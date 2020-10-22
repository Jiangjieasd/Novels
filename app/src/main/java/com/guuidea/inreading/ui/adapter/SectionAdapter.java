package com.guuidea.inreading.ui.adapter;

import com.guuidea.inreading.model.bean.SectionBean;
import com.guuidea.inreading.ui.adapter.view.SectionHolder;
import com.guuidea.inreading.ui.base.adapter.BaseListAdapter;
import com.guuidea.inreading.ui.base.adapter.IViewHolder;

/**
 * Created by guuidea on 17-4-16.
 */

public class SectionAdapter extends BaseListAdapter<SectionBean> {
    @Override
    protected IViewHolder<SectionBean> createViewHolder(int viewType) {
        return new SectionHolder();
    }
}
