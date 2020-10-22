package com.guuidea.inreading.ui.adapter;

import com.guuidea.inreading.model.bean.HotCommentBean;
import com.guuidea.inreading.ui.adapter.view.HotCommentHolder;
import com.guuidea.inreading.ui.base.adapter.BaseListAdapter;
import com.guuidea.inreading.ui.base.adapter.IViewHolder;

/**
 * Created by guuidea on 17-5-4.
 */

public class HotCommentAdapter extends BaseListAdapter<HotCommentBean>{
    @Override
    protected IViewHolder<HotCommentBean> createViewHolder(int viewType) {
        return new HotCommentHolder();
    }
}
