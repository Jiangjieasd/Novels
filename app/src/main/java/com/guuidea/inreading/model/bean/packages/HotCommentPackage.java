package com.guuidea.inreading.model.bean.packages;

import com.guuidea.inreading.model.bean.BaseBean;
import com.guuidea.inreading.model.bean.HotCommentBean;

import java.util.List;

/**
 * Created by guuidea on 17-5-4.
 */

public class HotCommentPackage extends BaseBean {

    private List<HotCommentBean> reviews;

    public List<HotCommentBean> getReviews() {
        return reviews;
    }

    public void setReviews(List<HotCommentBean> reviews) {
        this.reviews = reviews;
    }
}
