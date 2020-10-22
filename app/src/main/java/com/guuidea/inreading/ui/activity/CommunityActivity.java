package com.guuidea.inreading.ui.activity;

import androidx.appcompat.widget.Toolbar;


import com.guuidea.inreading.R;
import com.guuidea.inreading.ui.base.BaseActivity;

/**
 * Created by guuidea on 17-5-26.
 */

public class CommunityActivity extends BaseActivity{

    @Override
    protected int getContentId() {
        return R.layout.activity_community;
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle("社区");
    }
}
