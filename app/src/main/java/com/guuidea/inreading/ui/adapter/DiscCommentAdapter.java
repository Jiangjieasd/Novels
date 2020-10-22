package com.guuidea.inreading.ui.adapter;

import android.content.Context;

import com.guuidea.inreading.model.bean.BookCommentBean;
import com.guuidea.inreading.ui.adapter.view.DiscCommentHolder;
import com.guuidea.inreading.ui.base.adapter.IViewHolder;
import com.guuidea.inreading.widget.adapter.WholeAdapter;

/**
 * Created by guuidea on 17-4-20.
 */

public class DiscCommentAdapter extends WholeAdapter<BookCommentBean> {

    public DiscCommentAdapter(Context context, Options options) {
        super(context, options);
    }

    @Override
    protected IViewHolder<BookCommentBean> createViewHolder(int viewType) {
        return new DiscCommentHolder();
    }
}
