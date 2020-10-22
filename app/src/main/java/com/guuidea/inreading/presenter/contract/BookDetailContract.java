package com.guuidea.inreading.presenter.contract;

import com.guuidea.inreading.model.bean.BookDetailBean;
import com.guuidea.inreading.model.bean.BookListBean;
import com.guuidea.inreading.model.bean.CollBookBean;
import com.guuidea.inreading.model.bean.HotCommentBean;
import com.guuidea.inreading.ui.base.BaseContract;

import java.util.List;

/**
 * Created by guuidea on 17-5-4.
 */

public interface BookDetailContract {
    interface View extends BaseContract.BaseView{
        void finishRefresh(BookDetailBean bean);
        void finishHotComment(List<HotCommentBean> beans);
        void finishRecommendBookList(List<BookListBean> beans);

        void waitToBookShelf();
        void errorToBookShelf();
        void succeedToBookShelf();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshBookDetail(String bookId);
        //添加到书架上
        void addToBookShelf(CollBookBean collBook);
    }
}
