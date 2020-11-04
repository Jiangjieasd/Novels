package com.guuidea.inreading.ui.activity;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.guuidea.inreading.R;
import com.guuidea.inreading.model.bean.packages.SearchBookPackage;
import com.guuidea.inreading.presenter.SearchPresenter;
import com.guuidea.inreading.presenter.contract.SearchContract;
import com.guuidea.inreading.ui.adapter.KeyWordAdapter;
import com.guuidea.inreading.ui.adapter.SearchBookAdapter;
import com.guuidea.inreading.ui.base.BaseMVPActivity;
import com.guuidea.inreading.widget.RefreshLayout;
import com.guuidea.inreading.widget.itemdecoration.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by guuidea on 17-4-24.
 */

public class SearchActivity extends BaseMVPActivity<SearchContract.Presenter>
        implements SearchContract.View {
    private static final String TAG = "SearchActivity";
    private static final int TAG_LIMIT = 8;
    /**
     * 返回按钮
     */
    @BindView(R.id.search_iv_back)
    ImageView mIvBack;
    /**
     * 搜索框
     */
    @BindView(R.id.search_et_input)
    EditText mEtInput;
    /**
     * 搜索框输入内容清楚按钮
     */
    @BindView(R.id.search_iv_delete)
    ImageView mIvDelete;
    /**
     * 搜索按钮
     */
    @BindView(R.id.search_iv_search)
    ImageView mIvSearch;
    /**
     * 热门标签换一批按钮
     */
    @BindView(R.id.search_book_tv_refresh_hot)
    TextView mTvRefreshHot;
    /**
     * 标签列表
     */
    @BindView(R.id.search_tg_hot)
    TagGroup mTgHot;
    /**
     * 结果列表父布局，显示加载动画
     */
    @BindView(R.id.refresh_layout)
    RefreshLayout mRlRefresh;
    /**
     * 搜索结果列表
     */
    @BindView(R.id.refresh_rv_content)
    RecyclerView mRvSearch;
    /**
     * 关键字列表适配器
     */
    private KeyWordAdapter mKeyWordAdapter;
    /**
     * 搜索结果列表适配器
     */
    private SearchBookAdapter mSearchAdapter;

    private boolean isTag;
    private List<String> mHotTagList;
    private int mTagStart = 0;

    @Override
    protected int getContentId() {
        return R.layout.activity_search;
    }

    @Override
    protected SearchContract.Presenter bindPresenter() {
        return new SearchPresenter();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setUpAdapter();
        mRlRefresh.setBackground(ContextCompat.getDrawable(this, R.color.white));
    }

    private void setUpAdapter() {
        mKeyWordAdapter = new KeyWordAdapter();
        mSearchAdapter = new SearchBookAdapter();

        mRvSearch.setLayoutManager(new LinearLayoutManager(this));
        mRvSearch.addItemDecoration(new DividerItemDecoration(this));
    }

    @Override
    protected void initClick() {
        super.initClick();

        //退出
        mIvBack.setOnClickListener(
                v -> onBackPressed()
        );

        //输入框
        mEtInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ("".equals(s.toString().trim())) {
                    //隐藏delete按钮和关键字显示内容
                    if (mIvDelete.getVisibility() == View.VISIBLE) {
                        mIvDelete.setVisibility(View.INVISIBLE);
                        mRlRefresh.setVisibility(View.INVISIBLE);
                        //删除全部视图
                        mKeyWordAdapter.clear();
                        mSearchAdapter.clear();
                        mRvSearch.removeAllViews();
                    }
                    return;
                }
                //显示delete按钮
                if (mIvDelete.getVisibility() == View.INVISIBLE) {
                    mIvDelete.setVisibility(View.VISIBLE);
                    mRlRefresh.setVisibility(View.VISIBLE);
                    //默认是显示完成状态
                    mRlRefresh.showFinish();
                }
                //搜索
                String query = s.toString().trim();
                if (isTag) {
                    mRlRefresh.showLoading();
                    mPresenter.searchBook(query);
                    isTag = false;
                } else {
                    //传递
                    mPresenter.searchKeyWord(query);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //键盘的搜索
        mEtInput.setOnKeyListener((v, keyCode, event) -> {
            //修改回车键功能
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                searchBook();
                return true;
            }
            return false;
        });

        //进行搜索
        mIvSearch.setOnClickListener(
                v -> searchBook()
        );

        //删除字
        mIvDelete.setOnClickListener(
                v -> {
                    mEtInput.setText("");
                    toggleKeyboard();
                }
        );

        //关键字查询点击查书（做隐藏处理）
        mKeyWordAdapter.setOnItemClickListener(
                (view, pos) -> {
                    //显示正在加载
                    mRlRefresh.showLoading();
                    String book = mKeyWordAdapter.getItem(pos);
                    mPresenter.searchBook(book);
                    toggleKeyboard();
                }
        );

        //Tag的点击事件（做隐藏处理）
        mTgHot.setOnTagClickListener(
                tag -> {
                    isTag = true;
                    mEtInput.setText(tag);
                }
        );

        //Tag的刷新事件（做隐藏处理）
        mTvRefreshHot.setOnClickListener(
                v -> refreshTag()
        );

        //书本的点击事件（搜索结果列表点击跳转书籍详情页面）
        mSearchAdapter.setOnItemClickListener(
                (view, pos) -> {
                    String bookId = mSearchAdapter.getItem(pos).get_id();
                    BookDetailActivity.startActivity(this, bookId);
                }
        );
    }

    private void searchBook() {
        String query = mEtInput.getText().toString().trim();
        if (!"".equals(query)) {
            mRlRefresh.setVisibility(View.VISIBLE);
            mRlRefresh.showLoading();
            mPresenter.searchBook(query);
            //显示正在加载
            mRlRefresh.showLoading();
            toggleKeyboard();
        }
    }

    private void toggleKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        //默认隐藏
        mRlRefresh.setVisibility(View.GONE);
        //获取热词（做隐藏处理）
        mPresenter.searchHotWord();
    }

    @Override
    public void showError() {
    }

    @Override
    public void complete() {

    }

    @Override
    public void finishHotWords(List<String> hotWords) {
        mHotTagList = hotWords;
        refreshTag();
    }

    private void refreshTag() {
        int last = mTagStart + TAG_LIMIT;
        if (mHotTagList.size() <= last) {
            mTagStart = 0;
            last = TAG_LIMIT;
        }
        List<String> tags = mHotTagList.subList(mTagStart, last);
        mTgHot.setTags(tags);
        mTagStart += TAG_LIMIT;
    }

    @Override
    public void finishKeyWords(List<String> keyWords) {
        if (keyWords.isEmpty()) {
            mRlRefresh.setVisibility(View.INVISIBLE);
        }
        mKeyWordAdapter.refreshItems(keyWords);
        if (!(mRvSearch.getAdapter() instanceof KeyWordAdapter)) {
            mRvSearch.setAdapter(mKeyWordAdapter);
        }
    }

    @Override
    public void finishBooks(List<SearchBookPackage.BooksBean> books) {
        mSearchAdapter.refreshItems(books);
        if (books.size() == 0) {
            mRlRefresh.showEmpty();
        } else {
            //显示完成
            mRlRefresh.showFinish();
        }
        //加载
        if (!(mRvSearch.getAdapter() instanceof SearchBookAdapter)) {
            mRvSearch.setAdapter(mSearchAdapter);
        }
    }

    @Override
    public void errorBooks() {
        mRlRefresh.showEmpty();
    }

    @Override
    public void onBackPressed() {
        if (mRlRefresh.getVisibility() == View.VISIBLE) {
            mEtInput.setText("");
        } else {
            super.onBackPressed();
        }
    }
}
