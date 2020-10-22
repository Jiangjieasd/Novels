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

import io.reactivex.Single;

/**
 * Created by guuidea on 17-4-28.
 */

public interface GetDbHelper {
    Single<List<BookCommentBean>> getBookComments(String block, String sort, int start, int limited, String distillate);
    Single<List<BookHelpsBean>> getBookHelps(String sort, int start, int limited, String distillate);
    Single<List<BookReviewBean>> getBookReviews(String sort, String bookType, int start, int limited, String distillate);
    BookSortPackage getBookSortPackage();
    BillboardPackage getBillboardPackage();

    AuthorBean getAuthor(String id);
    ReviewBookBean getReviewBook(String id);
    BookHelpfulBean getBookHelpful(String id);

    /******************************/
    List<DownloadTaskBean> getDownloadTaskList();
}
