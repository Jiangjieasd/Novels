package com.guuidea.inreading.ui.adapter.view;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.guuidea.inreading.R;
import com.guuidea.inreading.model.bean.CollBookBean;
import com.guuidea.inreading.ui.base.adapter.ViewHolderImpl;
import com.guuidea.inreading.utils.Constant;
import com.guuidea.inreading.utils.StringUtils;

;

/**
 * Created by guuidea on 17-5-8.
 * CollectionBookView
 */

public class CollBookHolder extends ViewHolderImpl<CollBookBean> {

    private static final String TAG = "CollBookView";
    private ImageView mIvCover;
    private TextView mTvName;
    private TextView mTvChapter;
    private TextView mTvTime;
    private CheckBox mCbSelected;
    private ImageView mIvRedDot;
    private ImageView mIvTop;
    private TextView tvReadRate;


    @Override
    public void initView() {
        mIvCover = findById(R.id.coll_book_iv_cover);
        mTvName = findById(R.id.coll_book_tv_name);
        mTvChapter = findById(R.id.coll_book_tv_chapter);
        mTvTime = findById(R.id.coll_book_tv_lately_update);
        mCbSelected = findById(R.id.coll_book_cb_selected);
        mIvRedDot = findById(R.id.coll_book_iv_red_rot);
        mIvTop = findById(R.id.coll_book_iv_top);
        tvReadRate = findById(R.id.tv_read_rate);
    }

    @Override
    public void onBind(CollBookBean value, int pos) {
        if (value.isLocal()) {
            //本地文件的图片
            Glide.with(getContext())
                    .load(R.drawable.ic_local_file)
                    .fitCenter()
                    .into(mIvCover);
        } else {
            //书的图片
            Glide.with(getContext())
                    .load(Constant.IMG_BASE_URL + value.getCover())
                    .placeholder(R.drawable.ic_book_loading)
                    .error(R.drawable.ic_load_error)
                    .fitCenter()
                    .into(mIvCover);
        }
        //书名
        mTvName.setText(value.getTitle());
        if (!value.isLocal()) {
            //时间
            mTvTime.setText(StringUtils.
                    dateConvert(value.getUpdated(), Constant.FORMAT_BOOK_DATE) + ":");
            mTvTime.setVisibility(View.VISIBLE);
        } else {
            mTvTime.setText("阅读进度:");
        }
        //章节
        mTvChapter.setText(value.getLastChapter());
        //我的想法是，在Collection中加一个字段，当追更的时候设置为true。当点击的时候设置为false。
        //当更新的时候，最新数据跟旧数据进行比较，如果更新的话，设置为true。
        if (value.isUpdate()) {
            mIvRedDot.setVisibility(View.VISIBLE);
        } else {
            mIvRedDot.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_coll_book;
    }

}
