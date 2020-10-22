package com.guuidea.inreading.ui.adapter;

import android.content.Context;

import com.guuidea.inreading.model.bean.BookListBean;
import com.guuidea.inreading.ui.adapter.view.BookListHolder;
import com.guuidea.inreading.ui.base.adapter.IViewHolder;
import com.guuidea.inreading.widget.adapter.WholeAdapter;

/**
 * Created by guuidea on 17-5-1.
 */

public class BookListAdapter extends WholeAdapter<BookListBean> {
    public BookListAdapter() {
    }

    public BookListAdapter(Context context, Options options) {
        super(context, options);
    }

    @Override
    protected IViewHolder<BookListBean> createViewHolder(int viewType) {
        return new BookListHolder();
    }
}
