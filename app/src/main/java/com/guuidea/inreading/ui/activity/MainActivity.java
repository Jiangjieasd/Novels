package com.guuidea.inreading.ui.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.guuidea.inreading.R;
import com.guuidea.inreading.SingleItem;
import com.guuidea.inreading.ui.dialog.VipRenewDialog;
import com.guuidea.inreading.ui.base.BaseTabActivity;
import com.guuidea.inreading.ui.fragment.BookShelfFragment;
import com.guuidea.inreading.ui.fragment.CommunityFragment;
import com.guuidea.inreading.ui.fragment.FindFragment;
import com.guuidea.inreading.ui.record.RecordActivity;
import com.guuidea.inreading.utils.Constant;
import com.guuidea.inreading.utils.PermissionsChecker;
import com.guuidea.inreading.utils.SharedPreUtils;
import com.guuidea.inreading.ui.dialog.SexChooseDialog;
import com.guuidea.inreading.utils.ToastUtils;
import com.guuidea.inreading.widget.CustomActionbar;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseTabActivity {
    /*************Constant**********/
    private static final int WAIT_INTERVAL = 2000;
    private static final int PERMISSIONS_REQUEST_STORAGE = 1;

    static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    /***************Object*********************/
    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private PermissionsChecker mPermissionsChecker;
    /*****************Params*********************/
    private boolean isPrepareFinish = false;

    @Override
    protected int getContentId() {
        return R.layout.activity_base_tab;
    }

    /**************init method***********************/
    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        toolbar.setLogo(R.mipmap.logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("");
    }

    @Override
    protected List<Fragment> createTabFragments() {
        initFragment();
        return mFragmentList;
    }

    private void initFragment() {
        Fragment bookShelfFragment = new BookShelfFragment();
        Fragment communityFragment = new CommunityFragment();
        Fragment discoveryFragment = new FindFragment();
        mFragmentList.add(bookShelfFragment);
        mFragmentList.add(communityFragment);
        mFragmentList.add(discoveryFragment);
    }

    @Override
    protected List<String> createTabTitles() {
        String[] titles = getResources().getStringArray(R.array.nb_fragment_title);
        return Arrays.asList(titles);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //性别选择框
//        showSexChooseDialog();
//        findViewById(R.id.test).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Login.class)));
//        BookBanner<String> bookBanner = findViewById(R.id.book_banner);
//        ArrayList<String> datas = new ArrayList<>();
//        datas.add("233");
//        datas.add("23e3");
//        datas.add("23d3");
//        datas.add("23f3");
//        datas.add("23g3");
//        bookBanner.setAdapter(new PagerAdapter() {
//            @Override
//            public int getCount() {
//                return datas.size();
//            }
//
//            @Override
//            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//                return view == object;
//            }
//
//            @Override
//            public Object instantiateItem(ViewGroup container, int position) {
//                View view = LayoutInflater.from(container.getContext())
//                        .inflate(R.layout.layout_test, null);
//                container.addView(view);
//                TextView tvText = view.findViewById(R.id.tv_text);
//                int select = position < datas.size() ? position : (position % datas.size());
//                tvText.setText(datas.get(select));
//                return view;
//            }
//
//            @Override
//            public void destroyItem(ViewGroup container, int position, Object object) {
//                container.removeView((View) object);
//            }
//
//
//        });

//        showDialog();

        VipRenewDialog dialog = new VipRenewDialog(this,
                view -> {
                    ToastUtils.Companion.show("vip");
                },
                view -> {
                    ToastUtils.Companion.show("exit");
                });
        dialog.show();
        ((SingleItem) findViewById(R.id.one)).setClicker(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.Companion.show("4444");
            }
        });

        ((CustomActionbar) findViewById(R.id.action_bar)).setImgClicker(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RecordActivity.class));
            }
        });
        ((CustomActionbar) findViewById(R.id.action_bar)).setTitle("3333");

    }

    private void showDialog() {
        Dialog dialog = new Dialog(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_renew, null);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
    }

    private void showSexChooseDialog() {
        String sex = SharedPreUtils.getInstance()
                .getString(Constant.SHARED_SEX);
        if ("".equals(sex)) {
            mVp.postDelayed(() -> {
                Dialog dialog = new SexChooseDialog(this);
                dialog.show();
            }, 500);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 顶部工具条功能跳转
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Class<?> activityCls = null;
        switch (id) {
            case R.id.action_search:
                activityCls = SearchActivity.class;
                break;
            case R.id.action_login:
                break;
            case R.id.action_my_message:
                break;
            case R.id.action_download:
                activityCls = DownloadActivity.class;
                break;
            case R.id.action_sync_bookshelf:
                break;
            case R.id.action_scan_local_book:

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

                    if (mPermissionsChecker == null) {
                        mPermissionsChecker = new PermissionsChecker(this);
                    }

                    //获取读取和写入SD卡的权限
                    if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
                        //请求权限
                        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSIONS_REQUEST_STORAGE);
                        return super.onOptionsItemSelected(item);
                    }
                }

                activityCls = FileSystemActivity.class;
                break;
            case R.id.action_wifi_book:
                break;
            case R.id.action_feedback:
                break;
            case R.id.action_night_mode:
                break;
            case R.id.action_settings:
                break;
            default:
                break;
        }
        if (activityCls != null) {
            Intent intent = new Intent(this, activityCls);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        if (menu != null && menu instanceof MenuBuilder) {
            try {
                Method method = menu.getClass().
                        getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                method.setAccessible(true);
                method.invoke(menu, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onPreparePanel(featureId, view, menu);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_STORAGE: {
                // 如果取消权限，则返回的值为0
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //跳转到 FileSystemActivity
                    Intent intent = new Intent(this, FileSystemActivity.class);
                    startActivity(intent);

                } else {
                    ToastUtils.Companion.show("用户拒绝开启读写权限");
                }
                return;
            }
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!isPrepareFinish) {
            mVp.postDelayed(
                    () -> isPrepareFinish = false, WAIT_INTERVAL
            );
            isPrepareFinish = true;
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }
}
