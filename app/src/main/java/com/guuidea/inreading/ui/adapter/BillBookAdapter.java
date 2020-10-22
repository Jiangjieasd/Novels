package com.guuidea.inreading.ui.adapter;

import com.guuidea.inreading.model.bean.BillBookBean;
import com.guuidea.inreading.ui.adapter.view.BillBookHolder;
import com.guuidea.inreading.ui.base.adapter.BaseListAdapter;
import com.guuidea.inreading.ui.base.adapter.IViewHolder;

/**
 * Created by guuidea on 17-5-3.
 */

public class BillBookAdapter extends BaseListAdapter<BillBookBean> {
    @Override
    protected IViewHolder<BillBookBean> createViewHolder(int viewType) {
        return new BillBookHolder();
    }
}
