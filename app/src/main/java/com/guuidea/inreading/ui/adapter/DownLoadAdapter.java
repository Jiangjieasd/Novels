package com.guuidea.inreading.ui.adapter;

import com.guuidea.inreading.model.bean.DownloadTaskBean;
import com.guuidea.inreading.ui.adapter.view.DownloadHolder;
import com.guuidea.inreading.ui.base.adapter.BaseListAdapter;
import com.guuidea.inreading.ui.base.adapter.IViewHolder;

/**
 * Created by guuidea on 17-5-12.
 */

public class DownLoadAdapter extends BaseListAdapter<DownloadTaskBean>{

    @Override
    protected IViewHolder<DownloadTaskBean> createViewHolder(int viewType) {
        return new DownloadHolder();
    }
}
