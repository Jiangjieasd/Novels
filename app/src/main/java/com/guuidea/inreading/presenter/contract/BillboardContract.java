package com.guuidea.inreading.presenter.contract;

import com.guuidea.inreading.model.bean.packages.BillboardPackage;
import com.guuidea.inreading.ui.base.BaseContract;

/**
 * Created by guuidea on 17-4-23.
 */

public interface BillboardContract {

    interface View extends BaseContract.BaseView{
        void finishRefresh(BillboardPackage beans);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void loadBillboardList();
    }
}
