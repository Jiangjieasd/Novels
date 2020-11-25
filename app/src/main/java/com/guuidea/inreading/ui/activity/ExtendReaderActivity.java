package com.guuidea.inreading.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.glong.reader.TurnStatus;
import com.glong.reader.widget.EffectOfCover;
import com.glong.reader.widget.EffectOfNon;
import com.glong.reader.widget.EffectOfRealBothWay;
import com.glong.reader.widget.EffectOfRealOneWay;
import com.glong.reader.widget.EffectOfSlide;
import com.glong.reader.widget.PageChangedCallback;
import com.glong.reader.widget.ReaderView;
import com.google.android.material.navigation.NavigationView;
import com.guuidea.inreading.R;
import com.guuidea.inreading.model.bean.BookChapterContent;
import com.guuidea.inreading.model.bean.Chapter;
import com.guuidea.inreading.model.remote.RemoteRepository;
import com.guuidea.inreading.ui.SimpleOnSeekBarChangeListener;
import com.guuidea.inreading.ui.adapter.CatalogueAdapter;
import com.guuidea.inreading.ui.adapter.MyReaderAdapter;
import com.guuidea.inreading.ui.base.BaseActivity;
import com.guuidea.inreading.utils.LogUtils;
import com.guuidea.inreading.utils.RxUtils;
import com.guuidea.inreading.utils.ScreenUtils;
import com.guuidea.inreading.widget.MenuView;
import com.guuidea.inreading.widget.SettingView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Lenovo
 */
public class ExtendReaderActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = ExtendReaderActivity.class.getSimpleName();

    private ReaderView mReaderView;
    private ReaderView.ReaderManager mReaderManager;
    private ReaderView.Adapter<Chapter, BookChapterContent> mAdapter;
    /**
     * 菜单View
     */
    private MenuView mMenuView;
    /**
     * 设置View
     */
    private SettingView mSettingView;
    /**
     * 调节章节的SeekBar
     */
    private SeekBar mChapterSeekBar;
    /**
     * 操作抽屉
     */
    private DrawerLayout mDrawerLayout;
    /**
     * 左侧用于展示目录的抽屉
     */
    private NavigationView mNavigationView;
    /**
     * 展示目录
     */
    private RecyclerView mRecyclerView;
    private CatalogueAdapter mCatalogueAdapter;
    private String mBookId = "15";

    public static void startRead(String bookId) {

    }

    @Override
    protected int getContentId() {
        return R.layout.activity_extend_reader;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initReader();
        initView();
        initToolbar();
        loadChapter();
    }

    private void initView() {
        mMenuView = findViewById(R.id.menu_view);
        mSettingView = findViewById(R.id.setting_view);
        mChapterSeekBar = findViewById(R.id.chapter_seek_bar);
        // 调节亮度的SeekBar
        SeekBar lightSeekBar = findViewById(R.id.light_seek_bar);
        //调节字号的SeekBar
        SeekBar textSizeSeekBar = findViewById(R.id.text_size_seek_bar);
        // 调节文字间距的SeekBar
        SeekBar textSpaceSeekBar = findViewById(R.id.text_space_seek_bar);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mNavigationView = findViewById(R.id.navigation);
        mRecyclerView = findViewById(R.id.recyclerView);
        initRecyclerViewAndDrawerLayout();
        //设置
        findViewById(R.id.setting).setOnClickListener(this);
        //上一章
        findViewById(R.id.text_prev_chapter).setOnClickListener(this);
        //下一章
        findViewById(R.id.text_next_chapter).setOnClickListener(this);
        //目录
        findViewById(R.id.reader_catalogue).setOnClickListener(this);
        // 切换背景
        findViewById(R.id.reader_bg_0).setOnClickListener(this);
        findViewById(R.id.reader_bg_1).setOnClickListener(this);
        findViewById(R.id.reader_bg_2).setOnClickListener(this);
        findViewById(R.id.reader_bg_3).setOnClickListener(this);
        // 切换翻页效果
        findViewById(R.id.effect_real_one_way).setOnClickListener(this);
        findViewById(R.id.effect_real_both_way).setOnClickListener(this);
        findViewById(R.id.effect_cover).setOnClickListener(this);
        findViewById(R.id.effect_slide).setOnClickListener(this);
        findViewById(R.id.effect_non).setOnClickListener(this);

        mChapterSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                TurnStatus turnStatus = mReaderManager.toSpecifiedChapter(seekBar.getProgress(), 0);
                if (turnStatus == TurnStatus.LOAD_SUCCESS) {
                    mReaderView.invalidateBothPage();
                }
            }
        });

        lightSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ScreenUtils.changeAppBrightness(ExtendReaderActivity.this, progress);
            }
        });

        textSizeSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //文字大小限制最小20
                mReaderView.setTextSize(progress + 20);
            }
        });

        textSpaceSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mReaderView.setLineSpace(progress);
            }
        });

        lightSeekBar.setMax(255);
        textSizeSeekBar.setMax(100);
        textSpaceSeekBar.setMax(200);

        // 初始化SeekBar位置 如果需要历史纪录的话，可以在这里实现
        mChapterSeekBar.setProgress(0);
        lightSeekBar.setProgress(ScreenUtils.getSystemBrightness(this));
        textSizeSeekBar.setProgress(mReaderView.getTextSize() - 20);
        textSpaceSeekBar.setProgress(mReaderView.getLineSpace());
    }

    private void initRecyclerViewAndDrawerLayout() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCatalogueAdapter = new CatalogueAdapter(position -> {
            TurnStatus turnStatus = mReaderManager.toSpecifiedChapter(position, 0);
            if (turnStatus == TurnStatus.LOAD_SUCCESS) {
                mReaderView.invalidateBothPage();
            }
            mDrawerLayout.closeDrawer(mNavigationView);
        });
        mRecyclerView.setAdapter(mCatalogueAdapter);
        // 不响应滑动打开
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        // 这个订阅目的:当抽屉打开时可以滑动关闭,未打开时不能滑动打开
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }

            @Override
            public void onDrawerStateChanged(int i) {
            }
        });
    }

    private void initToolbar() {
        final Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_cache:
                Toast.makeText(this,
                        "删除缓存" + (mReaderManager.getCache().removeAll() ? "成功" : "失败"),
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initReader() {
        mReaderView = findViewById(R.id.extend_reader);
        mReaderManager = new ReaderView.ReaderManager();
        mAdapter = new MyReaderAdapter(mBookId);

        mReaderView.setReaderManager(mReaderManager);
        mReaderView.setAdapter(mAdapter);

        mReaderView.setPageChangedCallback(new PageChangedCallback() {
            @Override
            public TurnStatus toPrevPage() {
                TurnStatus turnStatus = mReaderManager.toPrevPage();
                if (turnStatus == TurnStatus.NO_PREV_CHAPTER) {
                    Toast.makeText(ExtendReaderActivity.this,
                            "没有上一页啦", Toast.LENGTH_SHORT).show();
                }
                return turnStatus;
            }

            @Override
            public TurnStatus toNextPage() {
                TurnStatus turnStatus = mReaderManager.toNextPage();
                if (turnStatus == TurnStatus.NO_NEXT_CHAPTER) {
                    Toast.makeText(ExtendReaderActivity.this,
                            "没有下一页啦", Toast.LENGTH_SHORT).show();
                }
                return turnStatus;
            }
        });
    }

    private Disposable mDisposable;

    /**
     * 下载章节列表
     */
    private void loadChapter() {
        mDisposable = RemoteRepository.getInstance().fetchChapterList(
                mBookId,
                1,
                5000)
                .compose(RxUtils::toSimpleSingle)
                .subscribe(new Consumer<List<Chapter>>() {

                               @Override
                               public void accept(List<Chapter> chapters) throws Exception {
                                   if (chapters != null) {
                                       mAdapter.setChapterList(chapters);
                                       mAdapter.notifyDataSetChanged();
                                       mChapterSeekBar.setMax(chapters.size() - 1);
                                       mCatalogueAdapter.setList(chapters);
                                   }
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                LogUtils.e(throwable);
                            }
                        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    private long mDownTime;
    private float mDownX;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int longClickTime = ViewConfiguration.get(this).getLongPressTimeout();
        int touchSlop = ViewConfiguration.get(this).getScaledPagingTouchSlop();
        int screenWidth = ScreenUtils.getScreenWidth(this);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (ev.getX() > screenWidth / 3
                        && ev.getX() < screenWidth * 2 / 3) {
                    mDownTime = System.currentTimeMillis();
                } else {
                    mDownTime = 0;
                }
                mDownX = ev.getX();
                break;
            case MotionEvent.ACTION_UP:
                if (System.currentTimeMillis() - mDownTime < longClickTime
                        && (Math.abs(ev.getX() - mDownX) < touchSlop)) {
                    if (!mMenuView.isShowing() && !mSettingView.isShowing()
                            && !mDrawerLayout.isDrawerOpen(mNavigationView)) {
                        mMenuView.show();
                        return true;
                    }
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting:
                mMenuView.dismiss();
                mSettingView.show();
                break;
            case R.id.text_prev_chapter:
                //上一章
                TurnStatus turnStatus = mReaderManager.toPrevChapter();
                if (turnStatus == TurnStatus.LOAD_SUCCESS) {
                    mReaderView.invalidateBothPage();
                    mChapterSeekBar.setProgress(mChapterSeekBar.getProgress() - 1);
                } else if (turnStatus == TurnStatus.DOWNLOADING) {
                    mChapterSeekBar.setProgress(mChapterSeekBar.getProgress() - 1);
                } else if (turnStatus == TurnStatus.NO_PREV_CHAPTER) {
                    Toast.makeText(this, "没有上一章啦", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.text_next_chapter:
                //下一章
                TurnStatus turnStatus2 = mReaderManager.toNextChapter();
                if (turnStatus2 == TurnStatus.LOAD_SUCCESS) {
                    mReaderView.invalidateBothPage();
                    mChapterSeekBar.setProgress(mChapterSeekBar.getProgress() + 1);
                } else if (turnStatus2 == TurnStatus.DOWNLOADING) {
                    mChapterSeekBar.setProgress(mChapterSeekBar.getProgress() + 1);
                } else if (turnStatus2 == TurnStatus.NO_NEXT_CHAPTER) {
                    Toast.makeText(this, "没有下一章啦", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.reader_catalogue:
                //目录
                mDrawerLayout.openDrawer(mNavigationView);
                mMenuView.dismiss();
                break;
            // 切换背景
            case R.id.reader_bg_0:
                mReaderView.setBackgroundColor(getResources().getColor(R.color.reader_bg_0));
                break;
            case R.id.reader_bg_1:
                mReaderView.setBackgroundColor(getResources().getColor(R.color.reader_bg_1));
                break;
            case R.id.reader_bg_2:
                mReaderView.setBackgroundColor(getResources().getColor(R.color.reader_bg_2));
                break;
            case R.id.reader_bg_3:
                mReaderView.setBackgroundColor(getResources().getColor(R.color.reader_bg_3));
                break;
            //切换翻页效果
            case R.id.effect_real_one_way:
                mReaderView.setEffect(new EffectOfRealOneWay(this));
                break;
            case R.id.effect_real_both_way:
                mReaderView.setEffect(new EffectOfRealBothWay(this));
                break;
            case R.id.effect_cover:
                mReaderView.setEffect(new EffectOfCover(this));
                break;
            case R.id.effect_slide:
                mReaderView.setEffect(new EffectOfSlide(this));
                break;
            case R.id.effect_non:
                mReaderView.setEffect(new EffectOfNon(this));
                break;
            case R.id.switch_night:
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        /*
         * 返回时,依次关闭目录,设置,菜单
         */
        if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
            mDrawerLayout.closeDrawer(mNavigationView);
        } else if (mSettingView.isShowing()) {
            mSettingView.dismiss();
        } else if (mMenuView.isShowing()) {
            mMenuView.dismiss();
        } else {
            super.onBackPressed();
        }
    }
}
