package com.guuidea.inreading.ui.adapter;

import com.guuidea.inreading.model.bean.packages.SearchBookPackage;
import com.guuidea.inreading.ui.adapter.view.SearchBookHolder;
import com.guuidea.inreading.ui.base.adapter.BaseListAdapter;
import com.guuidea.inreading.ui.base.adapter.IViewHolder;

/**
 * Created by guuidea on 17-6-2.
 */

public class SearchBookAdapter extends BaseListAdapter<SearchBookPackage.BooksBean>{
    @Override
    protected IViewHolder<SearchBookPackage.BooksBean> createViewHolder(int viewType) {
        return new SearchBookHolder();
    }
}
