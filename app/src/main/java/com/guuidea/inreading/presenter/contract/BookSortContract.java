package com.guuidea.inreading.presenter.contract;

import com.guuidea.inreading.model.bean.packages.BookSortPackage;
import com.guuidea.inreading.model.bean.packages.BookSubSortPackage;
import com.guuidea.inreading.ui.base.BaseContract;

/**
 * Created by guuidea on 17-4-23.
 */

public interface BookSortContract {

    interface View extends BaseContract.BaseView{
        void finishRefresh(BookSortPackage sortPackage, BookSubSortPackage subSortPackage);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshSortBean();
    }
}
