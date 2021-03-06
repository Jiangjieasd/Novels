package com.guuidea.inreading.ui.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.guuidea.inreading.R;
import com.guuidea.inreading.RxBus;
import com.guuidea.inreading.event.DeleteResponseEvent;
import com.guuidea.inreading.event.DeleteTaskEvent;
import com.guuidea.inreading.event.DownloadMessage;
import com.guuidea.inreading.event.RecommendBookEvent;
import com.guuidea.inreading.model.bean.BookOperateDescBean;
import com.guuidea.inreading.model.bean.CollBookBean;
import com.guuidea.inreading.model.local.BookRepository;
import com.guuidea.inreading.presenter.BookShelfPresenter;
import com.guuidea.inreading.presenter.contract.BookShelfContract;
import com.guuidea.inreading.ui.activity.ReadActivity;
import com.guuidea.inreading.ui.adapter.CollBookAdapter;
import com.guuidea.inreading.ui.base.BaseMVPFragment;
import com.guuidea.inreading.ui.base.adapter.UniversalBaseAdapter;
import com.guuidea.inreading.ui.base.adapter.UniversalViewHolder;
import com.guuidea.inreading.utils.RxUtils;
import com.guuidea.inreading.utils.ToastUtils;
import com.guuidea.inreading.widget.adapter.WholeAdapter;
import com.guuidea.inreading.widget.itemdecoration.DividerItemDecoration;
import com.guuidea.inreading.widget.refresh.ScrollRefreshRecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


/**
 *
 * @author guuidea
 */
public class BookShelfFragment extends BaseMVPFragment<BookShelfContract.Presenter>
        implements BookShelfContract.View {
    private static final String TAG = "BookShelfFragment";
    private ScrollRefreshRecyclerView mRvContent;

    /************************************/
    private CollBookAdapter mCollBookAdapter;
    private FooterItemView mFooterItem;

    /**
     * 是否是第一次进入
     */
    private boolean isInit = true;

    @Override
    protected int getContentId() {
        return R.layout.fragment_bookshelf;
    }

    @Override
    protected BookShelfContract.Presenter bindPresenter() {
        return new BookShelfPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        mRvContent = getActivity().findViewById(R.id.book_shelf_rv_content);
        setUpAdapter();
    }

    private void setUpAdapter() {
        //添加Footer
        mCollBookAdapter = new CollBookAdapter();
        mRvContent.setLayoutManager(new GridLayoutManager(this.getContext(),
                3, RecyclerView.HORIZONTAL, false));
        mRvContent.setAdapter(mCollBookAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
        //推荐书籍
//        Disposable recommendDisp = RxBus.getInstance()
//                .toObservable(RecommendBookEvent.class)
//                .subscribe(
//                        event -> {
//                            mRvContent.startRefresh();
//                            mPresenter.loadRecommendBooks(event.sex);
//                        }
//                );
//        addDisposable(recommendDisp);

        Disposable donwloadDisp = RxBus.getInstance()
                .toObservable(DownloadMessage.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        event -> {
                            //使用Toast提示
                            ToastUtils.Companion.show(event.message);
                        }
                );
        addDisposable(donwloadDisp);

        //删除书籍(此处并非删除书籍的操作，而是对于删除书籍操作事件的界面显示)
        @SuppressLint("CheckResult")
        Disposable deleteDisp = RxBus.getInstance()
                .toObservable(DeleteResponseEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        event -> {
                            if (event.isDelete) {
                                ProgressDialog progressDialog = new ProgressDialog(getContext());
                                progressDialog.setMessage("正在删除中");
                                progressDialog.show();
                                //开始删除书籍的具体操作
                                BookRepository.getInstance().deleteCollBookInRx(event.collBook)
                                        .compose(RxUtils::toSimpleSingle)
                                        .subscribe(
                                                Void -> {
                                                    mCollBookAdapter.removeItem(event.collBook);
                                                    progressDialog.dismiss();
                                                }
                                        );
                            } else {
                                //弹出一个Dialog
                                AlertDialog tipDialog = new AlertDialog.Builder(getContext())
                                        .setTitle("您的任务正在加载")
                                        .setMessage("先请暂停任务再进行删除")
                                        .setPositiveButton("确定", (dialog, which) -> {
                                            dialog.dismiss();
                                        }).create();
                                tipDialog.show();
                            }
                        }
                );
        addDisposable(deleteDisp);

        mRvContent.setOnRefreshListener(
                () -> mPresenter.updateCollBooks(mCollBookAdapter.getItems())
        );

        mCollBookAdapter.setOnItemClickListener(
                (view, pos) -> {
                    //如果是本地文件，首先判断这个文件是否存在
                    CollBookBean collBook = mCollBookAdapter.getItem(pos);
                    if (collBook.isLocal()) {
                        //id表示本地文件的路径
                        String path = collBook.getCover();
                        File file = new File(path);
                        //判断这个本地文件是否存在
                        if (file.exists() && file.length() != 0) {
                            ReadActivity.startActivity(getContext(),
                                    mCollBookAdapter.getItem(pos), true);
                        } else {
                            String tip = getContext()
                                    .getString(R.string.nb_bookshelf_book_not_exist);
                            //提示(从目录中移除这个文件)
                            new AlertDialog.Builder(getContext())
                                    .setTitle(getResources().getString(R.string.nb_common_tip))
                                    .setMessage(tip)
                                    .setPositiveButton(getResources()
                                            .getString(R.string.nb_common_sure),
                                            (dialog, which) -> deleteBook(collBook))
                                    .setNegativeButton(getResources()
                                            .getString(R.string.nb_common_cancel), null)
                                    .show();
                        }
                    } else {
                        ReadActivity.startActivity(getContext(),
                                mCollBookAdapter.getItem(pos), true);
                    }
                }
        );

        mCollBookAdapter.setOnItemLongClickListener(
                (v, pos) -> {
                    //开启Dialog,最方便的Dialog,就是AlterDialog
                    openItemDialog(mCollBookAdapter.getItem(pos));
                    return true;
                }
        );
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        mRvContent.startRefresh();
    }

    private void openItemDialog(CollBookBean collBook) {
        showBookOperateDialog(collBook);
    }

    /**
     * 显示底部弹窗操作
     */
    private void showBookOperateDialog(CollBookBean collBook) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.setContentView(R.layout.dialog_book_operate);
        RecyclerView rvOperates = bottomSheetDialog.getDelegate().findViewById(R.id.rv_operates);
        assert rvOperates != null;
        rvOperates.setAdapter(new UniversalBaseAdapter<BookOperateDescBean>(getContext(),
                productData()) {

            @Override
            public void bindData(@NotNull UniversalViewHolder holder,
                                 BookOperateDescBean item,
                                 int position) {
                holder.setText(R.id.tv_operate_desc, item.getOperateDesc());
                holder.setImageRes(R.id.img_operate, item.getOperateImgId());
                holder.itemView.setOnClickListener(v -> {
                    switch (item.getType()) {
                        case BookOperateDescBean.VIEW_DETAIL:
                            break;
                        case BookOperateDescBean.SHARE_NOVEL:
                            break;
                        case BookOperateDescBean.REMOVE_NOVEL:
                            deleteBook(collBook);
                            break;
                        default:
                            break;
                    }
                });
            }

            @Override
            public int getItemLayoutId() {
                return R.layout.item_book_operate;
            }
        });
        bottomSheetDialog.show();
    }

    /**
     * 创建底部弹窗显示数据集
     */
    private ArrayList<BookOperateDescBean> productData() {
        ArrayList<BookOperateDescBean> datas = new ArrayList<>();
        datas.add(new BookOperateDescBean(
                "View novel details",
                R.drawable.more,
                BookOperateDescBean.VIEW_DETAIL));
        datas.add(new BookOperateDescBean(
                "share the novel",
                R.drawable.more,
                BookOperateDescBean.SHARE_NOVEL));
        datas.add(new BookOperateDescBean(
                "remove from books",
                R.drawable.more,
                BookOperateDescBean.REMOVE_NOVEL));
        return datas;
    }

    private void downloadBook(CollBookBean collBook) {
        //创建任务
        mPresenter.createDownloadTask(collBook);
    }

    /**
     * 默认删除本地文件
     *
     * @param collBook
     */
    private void deleteBook(CollBookBean collBook) {
        if (collBook.isLocal()) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.dialog_delete, null);
            CheckBox cb = view.findViewById(R.id.delete_cb_select);
            new AlertDialog.Builder(getContext())
                    .setTitle("删除文件")
                    .setView(view)
                    .setPositiveButton(getResources().getString(R.string.nb_common_sure),
                            new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean isSelected = cb.isSelected();
                            if (isSelected) {
                                ProgressDialog progressDialog = new ProgressDialog(getContext());
                                progressDialog.setMessage("正在删除中");
                                progressDialog.show();
                                //删除
                                File file = new File(collBook.getCover());
                                if (file.exists()) {
                                    file.delete();
                                }
                                BookRepository.getInstance().deleteCollBook(collBook);
                                BookRepository.getInstance().deleteBookRecord(collBook.get_id());

                                //从Adapter中删除
                                mCollBookAdapter.removeItem(collBook);
                                progressDialog.dismiss();
                            } else {
                                BookRepository.getInstance().deleteCollBook(collBook);
                                BookRepository.getInstance().deleteBookRecord(collBook.get_id());
                                //从Adapter中删除
                                mCollBookAdapter.removeItem(collBook);
                            }
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.nb_common_cancel), null)
                    .show();
        } else {
            RxBus.getInstance().post(new DeleteTaskEvent(collBook));
        }
    }

    /*******************************************************************8*/

    @Override
    public void showError() {

    }

    @Override
    public void complete() {
        if (mCollBookAdapter.getItemCount() > 0 && mFooterItem == null) {
            mFooterItem = new FooterItemView();
            mCollBookAdapter.addFooterView(mFooterItem);
        }

        if (mRvContent.isRefreshing()) {
            mRvContent.finishRefresh();
        }
    }

    @Override
    public void finishRefresh(List<CollBookBean> collBookBeans) {
        mCollBookAdapter.refreshItems(collBookBeans);
        //如果是初次进入，则更新书籍信息
        if (isInit) {
            isInit = false;
            mRvContent.post(
                    () -> mPresenter.updateCollBooks(mCollBookAdapter.getItems())
            );
        }
    }

    @Override
    public void finishUpdate() {
        //重新从数据库中获取数据
        mCollBookAdapter.refreshItems(BookRepository
                .getInstance().getCollBooks());
    }

    @Override
    public void showErrorTip(String error) {
        mRvContent.setTip(error);
        mRvContent.showTip();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.refreshCollBooks();
    }

    /*****************************************************************/
    class FooterItemView implements WholeAdapter.ItemView {
        @Override
        public View onCreateView(ViewGroup parent) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.footer_book_shelf, parent, false);
            view.setOnClickListener(
                    (v) -> {
                        //设置RxBus回调
                    }
            );
            return view;
        }

        @Override
        public void onBindView(View view) {
        }
    }

}
