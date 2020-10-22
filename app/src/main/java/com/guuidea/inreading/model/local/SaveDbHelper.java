package com.guuidea.inreading.model.local;

import com.guuidea.inreading.model.bean.AuthorBean;
import com.guuidea.inreading.model.bean.DownloadTaskBean;
import com.guuidea.inreading.model.bean.packages.BillboardPackage;
import com.guuidea.inreading.model.bean.ReviewBookBean;
import com.guuidea.inreading.model.bean.BookCommentBean;
import com.guuidea.inreading.model.bean.BookHelpfulBean;
import com.guuidea.inreading.model.bean.BookHelpsBean;
import com.guuidea.inreading.model.bean.BookReviewBean;
import com.guuidea.inreading.model.bean.packages.BookSortPackage;

import java.util.List;

/**
 * Created by guuidea on 17-4-28.
 */

public interface SaveDbHelper {
    void saveBookComments(List<BookCommentBean> beans);
    void saveBookHelps(List<BookHelpsBean> beans);
    void saveBookReviews(List<BookReviewBean> beans);
    void saveAuthors(List<AuthorBean> beans);
    void saveBooks(List<ReviewBookBean> beans);
    void saveBookHelpfuls(List<BookHelpfulBean> beans);

    void saveBookSortPackage(BookSortPackage bean);
    void saveBillboardPackage(BillboardPackage bean);
    /*************DownloadTask*********************/
    void saveDownloadTask(DownloadTaskBean bean);
}
