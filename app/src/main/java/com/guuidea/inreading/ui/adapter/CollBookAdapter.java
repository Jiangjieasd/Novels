package com.guuidea.inreading.ui.adapter;

import com.guuidea.inreading.model.bean.CollBookBean;
import com.guuidea.inreading.ui.adapter.view.CollBookHolder;
import com.guuidea.inreading.ui.base.adapter.IViewHolder;
import com.guuidea.inreading.widget.adapter.WholeAdapter;

/**
 * Created by guuidea on 17-5-8.
 */

public class CollBookAdapter extends WholeAdapter<CollBookBean> {

    @Override
    protected IViewHolder<CollBookBean> createViewHolder(int viewType) {
        return new CollBookHolder();
    }

}
