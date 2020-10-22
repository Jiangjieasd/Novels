package com.guuidea.inreading.ui.adapter;

import android.content.Context;

import com.guuidea.inreading.model.bean.CommentBean;
import com.guuidea.inreading.ui.adapter.view.CommentHolder;
import com.guuidea.inreading.ui.base.adapter.IViewHolder;
import com.guuidea.inreading.widget.adapter.WholeAdapter;

/**
 * Created by guuidea on 17-4-29.
 */

public class CommentAdapter extends WholeAdapter<CommentBean> {

    public CommentAdapter(Context context, Options options) {
        super(context, options);
    }

    @Override
    protected IViewHolder<CommentBean> createViewHolder(int viewType) {
        return new CommentHolder(false);
    }
}
