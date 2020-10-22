package com.guuidea.inreading.model.bean.packages;

import com.guuidea.inreading.model.bean.BaseBean;
import com.guuidea.inreading.model.bean.BookTagBean;

import java.util.List;

/**
 * Created by guuidea on 17-5-1.
 */

public class BookTagPackage extends BaseBean {

    private List<BookTagBean> data;

    public List<BookTagBean> getData() {
        return data;
    }

    public void setData(List<BookTagBean> data) {
        this.data = data;
    }


}
