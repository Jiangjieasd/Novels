package com.guuidea.inreading.model.local;

import com.guuidea.inreading.model.bean.AuthorBean;
import com.guuidea.inreading.model.bean.ReviewBookBean;
import com.guuidea.inreading.model.bean.BookCommentBean;
import com.guuidea.inreading.model.bean.BookHelpfulBean;
import com.guuidea.inreading.model.bean.BookHelpsBean;
import com.guuidea.inreading.model.bean.BookReviewBean;

import java.util.List;

/**
 * Created by guuidea on 17-4-28.
 */

public interface DeleteDbHelper {
    void deleteBookComments(List<BookCommentBean> beans);
    void deleteBookReviews(List<BookReviewBean> beans);
    void deleteBookHelps(List<BookHelpsBean> beans);
    void deleteAuthors(List<AuthorBean> beans);
    void deleteBooks(List<ReviewBookBean> beans);
    void deleteBookHelpful(List<BookHelpfulBean> beans);
    void deleteAll();
}
