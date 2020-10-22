package com.guuidea.inreading.ui.base;

/**
 * Created by guuidea on 17-4-25.
 */

public abstract class BaseMVPFragment<T extends BaseContract.BasePresenter>
        extends BaseFragment
        implements BaseContract.BaseView{

    protected T mPresenter;

    /**
     * 对presenter进行赋值操作
     * @return
     */
    protected abstract T bindPresenter();

    @Override
    protected void processLogic(){
        mPresenter = bindPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
