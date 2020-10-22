package com.guuidea.inreading.ui.adapter;

import android.content.Context;

import com.guuidea.inreading.model.bean.SortBookBean;
import com.guuidea.inreading.ui.adapter.view.BookSortListHolder;
import com.guuidea.inreading.ui.base.adapter.IViewHolder;
import com.guuidea.inreading.widget.adapter.WholeAdapter;

/**
 * Created by guuidea on 17-5-3.
 */

public class BookSortListAdapter extends WholeAdapter<SortBookBean>{
    public BookSortListAdapter(Context context, Options options) {
        super(context, options);
    }

    @Override
    protected IViewHolder<SortBookBean> createViewHolder(int viewType) {
        return new BookSortListHolder();
    }
}
