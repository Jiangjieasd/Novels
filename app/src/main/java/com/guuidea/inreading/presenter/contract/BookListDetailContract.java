package com.guuidea.inreading.presenter.contract;

import com.guuidea.inreading.model.bean.BookListDetailBean;
import com.guuidea.inreading.ui.base.BaseContract;

/**
 * Created by guuidea on 17-5-1.
 */

public interface BookListDetailContract {

    interface View extends BaseContract.BaseView{
        void finishRefresh(BookListDetailBean bean);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshBookListDetail(String detailId);
    }
}
