package com.guuidea.inreading.model.remote;

import com.guuidea.inreading.model.bean.AllVIPResource;
import com.guuidea.inreading.model.bean.BaseResponseBean;
import com.guuidea.inreading.model.bean.BaseResponseModel;
import com.guuidea.inreading.model.bean.BillBookBean;
import com.guuidea.inreading.model.bean.BookChapterBean;
import com.guuidea.inreading.model.bean.BookCommentBean;
import com.guuidea.inreading.model.bean.BookDetailBean;
import com.guuidea.inreading.model.bean.BookDto;
import com.guuidea.inreading.model.bean.BookHelpsBean;
import com.guuidea.inreading.model.bean.BookListBean;
import com.guuidea.inreading.model.bean.BookListDetailBean;
import com.guuidea.inreading.model.bean.BookNameResultTag;
import com.guuidea.inreading.model.bean.BookPurchaseList;
import com.guuidea.inreading.model.bean.BookPurchaseRecord;
import com.guuidea.inreading.model.bean.BookReviewBean;
import com.guuidea.inreading.model.bean.BookShelfBean;
import com.guuidea.inreading.model.bean.BookShelfBody;
import com.guuidea.inreading.model.bean.BookShelfList;
import com.guuidea.inreading.model.bean.BookTagBean;
import com.guuidea.inreading.model.bean.BookTagDto;
import com.guuidea.inreading.model.bean.BookTagResultDTO;
import com.guuidea.inreading.model.bean.Chapter;
import com.guuidea.inreading.model.bean.ChapterDto;
import com.guuidea.inreading.model.bean.ChapterInfoBean;
import com.guuidea.inreading.model.bean.CollBookBean;
import com.guuidea.inreading.model.bean.CommentBean;
import com.guuidea.inreading.model.bean.CommentDetailBean;
import com.guuidea.inreading.model.bean.DeleteUserBookListDto;
import com.guuidea.inreading.model.bean.Feedback;
import com.guuidea.inreading.model.bean.HelpsDetailBean;
import com.guuidea.inreading.model.bean.HotCommentBean;
import com.guuidea.inreading.model.bean.PurchaseList;
import com.guuidea.inreading.model.bean.PurchaseListDto;
import com.guuidea.inreading.model.bean.RecommendBook;
import com.guuidea.inreading.model.bean.ReviewDetailBean;
import com.guuidea.inreading.model.bean.SingleBookInfo;
import com.guuidea.inreading.model.bean.SortBookBean;
import com.guuidea.inreading.model.bean.UserBookDto;
import com.guuidea.inreading.model.bean.UserBuyVipDto;
import com.guuidea.inreading.model.bean.UserPreferenceDto;
import com.guuidea.inreading.model.bean.UserPurchaseDto;
import com.guuidea.inreading.model.bean.UserRecommendBooks;
import com.guuidea.inreading.model.bean.VIPInfo;
import com.guuidea.inreading.model.bean.VIPInfoBean;
import com.guuidea.inreading.model.bean.VIPPurchaseList;
import com.guuidea.inreading.model.bean.VIPPurchaseRecord;
import com.guuidea.inreading.model.bean.VIPResource;
import com.guuidea.inreading.model.bean.packages.BillboardPackage;
import com.guuidea.inreading.model.bean.packages.BookCommentPackage;
import com.guuidea.inreading.model.bean.packages.BookHelpsPackage;
import com.guuidea.inreading.model.bean.packages.BookListDetailPackage;
import com.guuidea.inreading.model.bean.packages.BookListPackage;
import com.guuidea.inreading.model.bean.packages.BookReviewPackage;
import com.guuidea.inreading.model.bean.packages.BookSortPackage;
import com.guuidea.inreading.model.bean.packages.BookSubSortPackage;
import com.guuidea.inreading.model.bean.packages.BookTagPackage;
import com.guuidea.inreading.model.bean.packages.ChapterInfoPackage;
import com.guuidea.inreading.model.bean.packages.CommentDetailPackage;
import com.guuidea.inreading.model.bean.packages.CommentsPackage;
import com.guuidea.inreading.model.bean.packages.HelpsDetailPackage;
import com.guuidea.inreading.model.bean.packages.HotCommentPackage;
import com.guuidea.inreading.model.bean.packages.HotWordPackage;
import com.guuidea.inreading.model.bean.packages.KeyWordPackage;
import com.guuidea.inreading.model.bean.packages.RecommendBookListPackage;
import com.guuidea.inreading.model.bean.packages.RecommendBookPackage;
import com.guuidea.inreading.model.bean.packages.ReviewDetailPackage;
import com.guuidea.inreading.model.bean.packages.SearchBookPackage;
import com.guuidea.inreading.model.bean.packages.SortBookPackage;
import com.guuidea.inreading.utils.RxUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Single;
import retrofit2.Retrofit;

/**
 * @author guuidea
 * 网络请求工具类
 */
public class RemoteRepository {
    private static final String TAG = "RemoteRepository";

    private static RemoteRepository sInstance;
    private final BookApi mBookApi;

    private RemoteRepository() {
        Retrofit retrofit = RemoteHelper.getInstance()
                .getRetrofit();

        mBookApi = retrofit.create(BookApi.class);
    }

    public static RemoteRepository getInstance() {
        return SingleInstanceHolder.INSTACE;
    }

    private static class SingleInstanceHolder {
        static final RemoteRepository INSTACE = new RemoteRepository();
    }

    public Single<List<CollBookBean>> getRecommendBooks(String gender) {
        return mBookApi.getRecommendBookPackage(gender)
                .map(RecommendBookPackage::getBooks)
                .compose(RxUtils::toSimpleSingle);
    }

    public Single<List<BookChapterBean>> getBookChapters(String bookId) {
        return mBookApi.getBookChapterPackage(bookId, "chapter")
                .map(bean -> {
                    if (bean.getMixToc() == null) {
                        return new ArrayList<>(1);
                    } else {
                        return bean.getMixToc().getChapters();
                    }
                });
    }

    /**
     * 注意这里用的是同步请求
     */
    public Single<ChapterInfoBean> getChapterInfo(String url) {
        return mBookApi.getChapterInfoPackage(url)
                .map(ChapterInfoPackage::getChapter);
    }

    public Single<List<BookCommentBean>> getBookComment(String block,
                                                        String sort,
                                                        int start,
                                                        int limit,
                                                        String distillate) {
        return mBookApi.getBookCommentList(block, "all", sort, "all",
                start + "", limit + "", distillate)
                .map(BookCommentPackage::getPosts);
    }

    public Single<List<BookHelpsBean>> getBookHelps(String sort,
                                                    int start,
                                                    int limit,
                                                    String distillate) {
        return mBookApi.getBookHelpList("all", sort, start + "",
                limit + "", distillate)
                .map(BookHelpsPackage::getHelps);
    }

    public Single<List<BookReviewBean>> getBookReviews(String sort,
                                                       String bookType,
                                                       int start,
                                                       int limited,
                                                       String distillate) {
        return mBookApi.getBookReviewList("all", sort, bookType, start + "",
                limited + "", distillate)
                .map(BookReviewPackage::getReviews);
    }

    public Single<CommentDetailBean> getCommentDetail(String detailId) {
        return mBookApi.getCommentDetailPackage(detailId)
                .map(CommentDetailPackage::getPost);
    }

    public Single<ReviewDetailBean> getReviewDetail(String detailId) {
        return mBookApi.getReviewDetailPacakge(detailId)
                .map(ReviewDetailPackage::getReview);
    }

    public Single<HelpsDetailBean> getHelpsDetail(String detailId) {
        return mBookApi.getHelpsDetailPackage(detailId)
                .map(HelpsDetailPackage::getHelp);
    }

    public Single<List<CommentBean>> getBestComments(String detailId) {
        return mBookApi.getBestCommentPackage(detailId)
                .map(CommentsPackage::getComments);
    }

    /**
     * 获取的是 综合讨论区的 评论
     */
    public Single<List<CommentBean>> getDetailComments(String detailId, int start, int limit) {
        return mBookApi.getCommentPackage(detailId, start + "", limit + "")
                .map(CommentsPackage::getComments);
    }

    /**
     * 获取的是 书评区和书荒区的 评论
     */
    public Single<List<CommentBean>> getDetailBookComments(String detailId, int start, int limit) {
        return mBookApi.getBookCommentPackage(detailId, start + "", limit + "")
                .map(CommentsPackage::getComments);
    }

    /**
     * 获取书籍的分类
     */
    public Single<BookSortPackage> getBookSortPackage() {
        return mBookApi.getBookSortPackage();
    }

    /**
     * 获取书籍的子分类
     */
    public Single<BookSubSortPackage> getBookSubSortPackage() {
        return mBookApi.getBookSubSortPackage();
    }

    /**
     * 根据分类获取书籍列表
     */
    public Single<List<SortBookBean>> getSortBooks(String gender,
                                                   String type,
                                                   String major,
                                                   String minor,
                                                   int start,
                                                   int limit) {
        return mBookApi.getSortBookPackage(gender, type, major, minor, start, limit)
                .map(SortBookPackage::getBooks);
    }

    /**
     * 排行榜的类型
     */
    public Single<BillboardPackage> getBillboardPackage() {
        return mBookApi.getBillboardPackage();
    }

    /**
     * 排行榜的书籍
     */
    public Single<List<BillBookBean>> getBillBooks(String billId) {
        return mBookApi.getBillBookPackage(billId)
                .map(bean -> bean.getRanking().getBooks());
    }

    /**
     * 获取书单列表
     */
    public Single<List<BookListBean>> getBookLists(String duration,
                                                   String sort,
                                                   int start,
                                                   int limit,
                                                   String tag,
                                                   String gender) {
        return mBookApi.getBookListPackage(duration, sort, start + "",
                limit + "", tag, gender)
                .map(BookListPackage::getBookLists);
    }

    /**
     * 获取书单的标签|类型
     */
    public Single<List<BookTagBean>> getBookTags() {
        return mBookApi.getBookTagPackage()
                .map(BookTagPackage::getData);
    }

    /**
     * 获取书单的详情
     */
    public Single<BookListDetailBean> getBookListDetail(String detailId) {
        return mBookApi.getBookListDetailPackage(detailId)
                .map(BookListDetailPackage::getBookList);
    }

    public Single<BookDetailBean> getBookDetail(String bookId) {
        return mBookApi.getBookDetail(bookId);
    }

    public Single<List<HotCommentBean>> getHotComments(String bookId) {
        return mBookApi.getHotCommnentPackage(bookId)
                .map(HotCommentPackage::getReviews);
    }

    public Single<List<BookListBean>> getRecommendBookList(String bookId, int limit) {
        return mBookApi.getRecommendBookListPackage(bookId, limit + "")
                .map(RecommendBookListPackage::getBooklists);
    }

    /**
     * 搜索热词
     */
    public Single<List<String>> getHotWords() {
        return mBookApi.getHotWordPackage()
                .map(HotWordPackage::getHotWords);
    }

    /**
     * 搜索关键字
     */
    public Single<List<String>> getKeyWords(String query) {
        return mBookApi.getKeyWordPacakge(query)
                .map(KeyWordPackage::getKeywords);
    }

    /**
     * 查询书籍
     *
     * @param query:书名|作者名
     */
    public Single<List<SearchBookPackage.BooksBean>> getSearchBooks(String query) {
        return mBookApi.getSearchBookPackage(query)
                .map(SearchBookPackage::getBooks);
    }

    public Single<BaseResponseBean> feedback(String feedbackStr) {
        return mBookApi.feedback(new Feedback(feedbackStr));
    }

    public Single<BaseResponseBean> savePreference(List<Integer> preferenceIds) {
        return mBookApi.savePreference(new UserPreferenceDto(preferenceIds));
    }

    public Single<BaseResponseBean> purchaseBook(int bookEnId) {
        return mBookApi.purchaseBook(new UserBookDto(bookEnId));
    }

    public Single<BookPurchaseList> purchaseList(int pageNum, int pageSize) {
        return mBookApi.purchaseList(new UserPurchaseDto(pageNum, pageSize))
                .map(BookPurchaseRecord::getData);
    }

    public Single<VIPPurchaseList> purchaseVIPList(int pageNum, int pageSize) {
        return mBookApi.getVipPurchaseList(new UserPurchaseDto(pageNum, pageSize))
                .map(VIPPurchaseRecord::getData);
    }

    /**
     * 获取当前所有VIP信息和折扣信息
     *
     * @return
     */
    public Single<List<VIPResource>> getAllVIPResource() {
        return mBookApi.getAllVIPResources()
                .map(AllVIPResource::getData);
    }

    /**
     * 购买VIP
     *
     * @param onSaleId
     * @param vipId
     * @return
     */
    public Single<BaseResponseBean> buyVIP(String onSaleId, int vipId) {
        return mBookApi.buyVip(new UserBuyVipDto(onSaleId, vipId));
    }

    /**
     * 获取VIP信息
     *
     * @return
     */
    public Single<VIPInfoBean> getVIPInfo() {
        return mBookApi.getVipInfo()
                .map(VIPInfo::getData)
                .compose(RxUtils::toSimpleSingle);
    }

    /**
     * 获取用户推荐书籍
     *
     * @return
     */
    public Single<UserRecommendBooks> fetchRecommendBookInReading() {
        return mBookApi.getRecommendBook();
    }

    /**
     * 获取单本书籍信息
     *
     * @param bookEnId
     * @return
     */
    public Single<RecommendBook> fetchSingleBookInfo(int bookEnId) {
        return mBookApi.fetchBookInfo(new UserBookDto(bookEnId))
                .map(SingleBookInfo::getData)
                .compose(RxUtils::toSimpleSingle);
    }

    /**
     * 获取章节列表
     *
     * @param bookEnId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Single<List<Chapter>> fetchChapterList(int bookEnId,
                                                  int pageNumber,
                                                  int pageSize) {
        return mBookApi.fetchChapterList(new ChapterDto(bookEnId, pageNumber, pageSize))
                .map(bean -> bean.getData().getData());

    }

    /**
     * 添加书籍至书架
     *
     * @param bookEnId
     * @return
     */
    public Single<BaseResponseBean> addReadingBook(int bookEnId) {
        return mBookApi.addReadingBook(new UserBookDto(bookEnId))
                .compose(RxUtils::toSimpleSingle);
    }

    /**
     * 删除书籍
     *
     * @param listId
     * @return
     */
    public Single<BaseResponseBean> deleteReadingBook(int listId) {
        return mBookApi.deleteReadingBook(new DeleteUserBookListDto(listId))
                .compose(RxUtils::toSimpleSingle);
    }

    /**
     * 获取用户书架列表
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Single<BookShelfList> fetchReadingList(int pageNumber, int pageSize) {
        return mBookApi.fetchReadingList(new UserPurchaseDto(pageNumber, pageSize))
                .map(BookShelfBody::getData);
    }

    /**
     * 增加用户阅读数
     *
     * @param bookEnId
     * @return
     */
    public Single<BaseResponseBean> addReadingReadBook(int bookEnId) {
        return mBookApi.addReadingReadBook(new UserBookDto(bookEnId));
    }

    /**
     * 根据标签搜索
     *
     * @param order “views desc”
     */
    public Single<BookTagResultDTO> searchBookByTag(String order,
                                                    int pageNum,
                                                    int pageSize,
                                                    int tagId) {
        return mBookApi.searchBookByTag(new BookTagDto(order, pageNum, pageSize, tagId))
                .map(BaseResponseModel::getData)
                .compose(RxUtils::toSimpleSingle);
    }

    /**
     * 根据书名进行搜索
     */
    public Single<BookNameResultTag> searchBookByName(String bookName,
                                                      String order,
                                                      int pageNum,
                                                      int pageSize) {
        return mBookApi.searchBookByName(new BookDto(bookName, order, pageNum, pageSize))
                .map(BaseResponseModel::getData)
                .compose(RxUtils::toSimpleSingle);
    }
}
