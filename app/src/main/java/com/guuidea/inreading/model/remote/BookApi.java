package com.guuidea.inreading.model.remote;

import com.guuidea.inreading.model.bean.AllVIPResource;
import com.guuidea.inreading.model.bean.BaseResponseBean;
import com.guuidea.inreading.model.bean.BookDetailBean;
import com.guuidea.inreading.model.bean.BookDto;
import com.guuidea.inreading.model.bean.BookPurchaseRecord;
import com.guuidea.inreading.model.bean.BookShelfBody;
import com.guuidea.inreading.model.bean.BookTagDto;
import com.guuidea.inreading.model.bean.ChapterBody;
import com.guuidea.inreading.model.bean.ChapterDto;
import com.guuidea.inreading.model.bean.DeleteUserBookListDto;
import com.guuidea.inreading.model.bean.Feedback;
import com.guuidea.inreading.model.bean.LoginDTO;
import com.guuidea.inreading.model.bean.PurchaseListDto;
import com.guuidea.inreading.model.bean.SingleBookInfo;
import com.guuidea.inreading.model.bean.UserBookDto;
import com.guuidea.inreading.model.bean.UserBuyVipDto;
import com.guuidea.inreading.model.bean.UserPreferenceDto;
import com.guuidea.inreading.model.bean.UserPurchaseDto;
import com.guuidea.inreading.model.bean.UserRecommendBooks;
import com.guuidea.inreading.model.bean.VIPInfo;
import com.guuidea.inreading.model.bean.VIPPurchaseList;
import com.guuidea.inreading.model.bean.VIPPurchaseRecord;
import com.guuidea.inreading.model.bean.packages.BillBookPackage;
import com.guuidea.inreading.model.bean.packages.BillboardPackage;
import com.guuidea.inreading.model.bean.packages.BookChapterPackage;
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
import com.guuidea.inreading.model.bean.packages.TagSearchPackage;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author guuidea
 */
public interface BookApi {

    /**
     * 推荐书籍
     */
    @GET("/book/recommend")
    Single<RecommendBookPackage> getRecommendBookPackage(@Query("gender") String gender);


    /**
     * 获取书籍的章节总列表
     */
    @GET("/mix-atoc/{bookId}")
    Single<BookChapterPackage> getBookChapterPackage(@Path("bookId") String bookId,
                                                     @Query("view") String view);

    /**
     * 章节的内容
     * 这里采用的是同步请求。
     */
    @GET("http://chapter2.zhuishushenqi.com/chapter/{url}")
    Single<ChapterInfoPackage> getChapterInfoPackage(@Path("url") String url);

    /**
     * 获取综合讨论区、原创区，女生区帖子列表
     * 全部、默认排序  http://api.zhuishushenqi.com/post/by-block?block=ramble&duration=all&sort=updated&type=all&start=0&limit=20&distillate=
     * 精品、默认排序  http://api.zhuishushenqi.com/post/by-block?block=ramble&duration=all&sort=updated&type=all&start=0&limit=20&distillate=true
     *
     * @param block      ramble:综合讨论区
     *                   original：原创区
     *                   girl:女生区
     * @param duration   all
     * @param sort       updated(默认排序)
     *                   created(最新发布)
     *                   comment-count(最多评论)
     * @param type       all
     * @param start      0
     * @param limit      20
     * @param distillate true(精品)
     */
    @GET("/post/by-block")
    Single<BookCommentPackage> getBookCommentList(@Query("block") String block, @Query("duration") String duration, @Query("sort") String sort, @Query("type") String type, @Query("start") String start, @Query("limit") String limit, @Query("distillate") String distillate);

    /**
     * 获取书荒区帖子列表
     * 全部、默认排序  http://api.zhuishushenqi.com/post/help?duration=all&sort=updated&start=0&limit=20&distillate=
     * 精品、默认排序  http://api.zhuishushenqi.com/post/help?duration=all&sort=updated&start=0&limit=20&distillate=true
     *
     * @param duration   all
     * @param sort       updated(默认排序)
     *                   created(最新发布)
     *                   comment-count(最多评论)
     * @param start      0
     * @param limit      20
     * @param distillate true(精品) 、空字符（全部）
     */
    @GET("/post/help")
    Single<BookHelpsPackage> getBookHelpList(@Query("duration") String duration,
                                             @Query("sort") String sort,
                                             @Query("start") String start,
                                             @Query("limit") String limit,
                                             @Query("distillate") String distillate);

    /**
     * 获取书评区帖子列表
     * 全部、全部类型、默认排序  http://api.zhuishushenqi.com/post/review?duration=all&sort=updated&type=all&start=0&limit=20&distillate=
     * 精品、玄幻奇幻、默认排序  http://api.zhuishushenqi.com/post/review?duration=all&sort=updated&type=xhqh&start=0&limit=20&distillate=true
     *
     * @param duration   all
     * @param sort       updated(默认排序)
     *                   created(最新发布)
     *                   helpful(最有用的)
     *                   comment-count(最多评论)
     * @param type       all(全部类型)、xhqh(玄幻奇幻)、dsyn(都市异能)...
     * @param start      0
     * @param limit      20
     * @param distillate true(精品) 、空字符（全部）
     */
    @GET("/post/review")
    Single<BookReviewPackage> getBookReviewList(@Query("duration") String duration,
                                                @Query("sort") String sort,
                                                @Query("type") String type,
                                                @Query("start") String start,
                                                @Query("limit") String limit,
                                                @Query("distillate") String distillate);

    /**
     * 获取综合讨论区帖子详情 :/post/{detailId}
     *
     * @param detailId ->_id
     */
    @GET("/post/{detailId}")
    Single<CommentDetailPackage> getCommentDetailPackage(@Path("detailId") String detailId);

    /**
     * 获取书评区帖子详情
     *
     * @param detailId->_id
     */
    @GET("/post/review/{detailId}")
    Single<ReviewDetailPackage> getReviewDetailPacakge(@Path("detailId") String detailId);

    /**
     * 获取书荒区帖子详情
     *
     * @param detailId->_id
     **/
    @GET("/post/help/{detailId}")
    Single<HelpsDetailPackage> getHelpsDetailPackage(@Path("detailId") String detailId);

    /**
     * 获取神评论列表(综合讨论区、书评区、书荒区皆为同一接口)
     *
     * @param detailId -> _id
     **/
    @GET("/post/{detailId}/comment/best")
    Single<CommentsPackage> getBestCommentPackage(@Path("detailId") String detailId);

    /**
     * 获取综合讨论区帖子详情内的评论列表        :/post/{disscussionId}/comment
     * 获取书评区、书荒区帖子详情内的评论列表     :/post/review/{disscussionId}/comment
     *
     * @param detailId->_id
     * @param start         0
     * @param limit         30
     **/
    @GET("/post/{detailId}/comment")
    Single<CommentsPackage> getCommentPackage(@Path("detailId") String detailId,
                                              @Query("start") String start,
                                              @Query("limit") String limit);

    @GET("/post/review/{detailId}/comment")
    Single<CommentsPackage> getBookCommentPackage(@Path("detailId") String detailId,
                                                  @Query("start") String start,
                                                  @Query("limit") String limit);

    /**
     * 获取所有排行榜
     */
    @GET("/ranking/gender")
    Single<BillboardPackage> getBillboardPackage();

    /**
     * 获取单一排行榜
     * 周榜：rankingId-> _id
     * 月榜：rankingId-> monthRank
     * 总榜：rankingId-> totalRank
     */
    @GET("/ranking/{rankingId}")
    Single<BillBookPackage> getBillBookPackage(@Path("rankingId") String rankingId);

    /**
     * 获取分类
     */
    @GET("/cats/lv2/statistics")
    Single<BookSortPackage> getBookSortPackage();

    /**
     * 获取二级分类
     */
    @GET("/cats/lv2")
    Single<BookSubSortPackage> getBookSubSortPackage();

    /**
     * 按分类获取书籍列表
     *
     * @param gender male、female
     * @param type   hot(热门)、new(新书)、reputation(好评)、over(完结)
     * @param major  玄幻
     * @param minor  东方玄幻、异界大陆、异界争霸、远古神话
     * @param limit  50
     */
    @GET("/book/by-categories")
    Single<SortBookPackage> getSortBookPackage(@Query("gender") String gender,
                                               @Query("type") String type,
                                               @Query("major") String major,
                                               @Query("minor") String minor,
                                               @Query("start") int start,
                                               @Query("limit") int limit);

    /**
     * 获取主题书单列表
     * 本周最热：duration=last-seven-days&sort=collectorCount
     * 最新发布：duration=all&sort=created
     * 最多收藏：duration=all&sort=collectorCount
     * <p>
     * 如:http://api.zhuishushenqi.com/book-list?duration=last-seven-days&sort=collectorCount&start=0&limit=20&tag=%E9%83%BD%E5%B8%82&gender=male
     *
     * @param tag    都市、古代、架空、重生、玄幻、网游
     * @param gender male、female
     * @param limit  20
     */
    @GET("/book-list")
    Single<BookListPackage> getBookListPackage(@Query("duration") String duration,
                                               @Query("sort") String sort,
                                               @Query("start") String start,
                                               @Query("limit") String limit,
                                               @Query("tag") String tag,
                                               @Query("gender") String gender);

    /**
     * 获取主题书单标签列表
     */
    @GET("/book-list/tagType")
    Single<BookTagPackage> getBookTagPackage();

    /**
     * 获取书单详情
     */
    @GET("/book-list/{bookListId}")
    Single<BookListDetailPackage> getBookListDetailPackage(@Path("bookListId") String bookListId);

    /**
     * 书籍热门评论
     */
    @GET("/post/review/best-by-book")
    Single<HotCommentPackage> getHotCommnentPackage(@Query("book") String book);

    /**
     * 书籍推荐书单
     */
    @GET("/book-list/{bookId}/recommend")
    Single<RecommendBookListPackage> getRecommendBookListPackage(@Path("bookId") String bookId,
                                                                 @Query("limit") String limit);

    /**
     * 书籍详情
     */
    @GET("/book/{bookId}?t=0&useNewCat=true")
    Single<BookDetailBean> getBookDetail(@Path("bookId") String bookId);

    /**
     * 根据书籍的 Tag 进行检索
     */
    @GET("/book/by-tags")
    Single<TagSearchPackage> getTagSearchPackage(@Query("tags") String tags,
                                                 @Query("start") String start,
                                                 @Query("limit") String limit);

    /************************************搜索书籍******************************************************/
    @GET("/book/hot-word")
    Single<HotWordPackage> getHotWordPackage();

    /**
     * 关键字自动补全
     */
    @GET("/book/auto-complete")
    Single<KeyWordPackage> getKeyWordPacakge(@Query("query") String query);

    /**
     * 书籍查询
     */
    @GET("/book/fuzzy-search")
    Single<SearchBookPackage> getSearchBookPackage(@Query("query") String query);


    /**
     * 登录
     */
    @POST("/admin/auth/login")
    Single<BaseResponseBean> login(@Body LoginDTO loginDTO);

    @POST("/admin/auth/logout")
    Single<BaseResponseBean> logout();

    /**
     * 反馈
     */
    @POST("/user/feedback")
    Single<BaseResponseBean> feedback(@Body Feedback feedback);

    /**
     * 用户感兴趣标签
     */
    @POST("/user/save/preference")
    Single<BaseResponseBean> savePreference(@Body UserPreferenceDto userPreferenceDto);

    /**
     * 用户购买书籍
     */
    @POST("/user/purchase/book")
    Single<BaseResponseBean> purchaseBook(@Body UserBookDto userBookDto);

    /**
     * 购买记录
     */
    @POST("/user/purchase/list")
    Single<BookPurchaseRecord> purchaseList(@Body UserPurchaseDto userPurchaseDto);

    /**
     * 获取书籍购买价格
     */
    @POST("/content/price/book")
    Single<BaseResponseBean> getBookPrice(@Body UserBookDto bookIdDto);

    /**
     * 获取VIP列表
     */
    @POST("/vip/list")
    Single<BaseResponseBean> getVipList();

    /**
     * 获取书籍购买价格
     */
    @POST("/content/book/info")
    Single<SingleBookInfo> fetchBookInfo(@Body UserBookDto bookDto);

    /**
     * 书籍章节
     */
    @POST("/content/chapter/list")
    Single<ChapterBody> fetchChapterList(@Body ChapterDto chapterDto);

    /**
     * 用户推荐书籍
     */
    @POST("/content/recommend")
    Single<UserRecommendBooks> getRecommendBook();

    /**
     * 根据书名搜索书籍
     */
    @POST("/content/search/book/name")
    Single<BaseResponseBean> searchBookByName(@Body BookDto bookDto);

    /**
     * 根据标签搜索书籍
     */
    @POST("/content/search/book/tag")
    Single<BaseResponseBean> searchBookByTag(@Body BookTagDto bookTagDto);

    /**
     * 购买vip
     */
    @POST("/user/vip/buy")
    Single<BaseResponseBean> buyVip(@Body UserBuyVipDto userBuyVipDto);

    /**
     * 获取当前用户vip信息
     */
    @POST("/user/vip/info")
    Single<VIPInfo> getVipInfo();

    /**
     * 获取用户折扣VIP
     */
    @POST("/user/vip/onsale")
    Single<BaseResponseBean> getVipOnsell();

    /**
     * 获取用户购买vip记录
     */
    @POST("/user/vip/purchase/list")
    Single<VIPPurchaseRecord> getVipPurchaseList(@Body UserPurchaseDto userPurchaseDto);

    /**
     * 用户添加书籍
     */
    @POST("/user/reading/add/book")
    Single<BaseResponseBean> addReadingBook(@Body UserBookDto saveUserBookListDto);

    /**
     * 用户删除书籍
     */
    @POST("/user/reading/delete/book")
    Single<BaseResponseBean> deleteReadingBook(@Body DeleteUserBookListDto deleteUserBookListDto);

    /**
     * 用户获取书本阅读进度
     */
    @POST("/user/reading/get/book/process")
    Single<BaseResponseBean> getReadingBookProcess();

    /**
     * 用户获取书籍章节
     */
    @POST("/user/reading/get/chapter")
    Single<BaseResponseBean> getReadingChapter();

    /**
     * 用户获取书架书籍列表
     */
    @POST("/user/reading/get/list")
    Single<BookShelfBody> fetchReadingList(@Body UserPurchaseDto userBookListDto);

    /**
     * 用户阅读书籍增加views阅读量
     */
    @POST("/user/reading/read/book")
    Single<BaseResponseBean> addReadingReadBook(@Body UserBookDto saveUserBookListDto);

    /**
     * 用户更新书籍已读章节
     */
    @POST("/user/reading/update/book")
    Single<BaseResponseBean> getReadingUpdateBook();

    @POST("/vip/all-vip-resources")
    Single<AllVIPResource> getAllVIPResources();

}
