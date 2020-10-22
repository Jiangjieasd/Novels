package com.guuidea.inreading.model.bean.packages;

import com.guuidea.inreading.model.bean.BaseBean;

import java.util.List;

/**
 * Created by guuidea on 17-6-2.
 */

public class KeyWordPackage extends BaseBean {

    private List<String> keywords;

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
