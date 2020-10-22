package com.guuidea.inreading.presenter.contract;

import com.guuidea.inreading.model.bean.packages.SearchBookPackage;
import com.guuidea.inreading.ui.base.BaseContract;

import java.util.List;

/**
 * Created by guuidea on 17-6-2.
 */

public interface SearchContract extends BaseContract {

    interface View extends BaseView{
        void finishHotWords(List<String> hotWords);
        void finishKeyWords(List<String> keyWords);
        void finishBooks(List<SearchBookPackage.BooksBean> books);

        void errorBooks();
    }

    interface Presenter extends BasePresenter<View>{
        void searchHotWord();
        //搜索提示
        void searchKeyWord(String query);
        //搜索书籍
        void searchBook(String query);
    }
}
