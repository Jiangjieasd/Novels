package com.guuidea.inreading.presenter.contract;

import com.guuidea.inreading.model.bean.BookChapterBean;
import com.guuidea.inreading.ui.base.BaseContract;
import com.guuidea.inreading.widget.page.TxtChapter;

import java.util.List;

/**
 * Created by guuidea on 17-5-16.
 */

public interface ReadContract extends BaseContract{
    interface View extends BaseContract.BaseView {
        void showCategory(List<BookChapterBean> bookChapterList);
        void finishChapter();
        void errorChapter();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void loadCategory(String bookId);
        void loadChapter(String bookId,List<TxtChapter> bookChapterList);
    }
}
