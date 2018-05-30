package com.github.search;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.search.adapter.RepoListAdapter;
import com.github.search.bean.Repo;
import com.github.search.network.RetrofitRequest;
import com.github.search.network.result.RepoListResult;
import com.github.search.service.RepoDBService;
import com.github.search.util.CommonUtil;
import com.github.search.util.Constant;
import com.github.search.widget.EmptyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import deadline.swiperecyclerview.SwipeRecyclerView;

/**
 * 搜索主界面
 *
 * @author yangdong
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.edit_word)
    EditText etKeyword; // 关键词
    @BindView(R.id.swipe_fresh)
    SwipeRecyclerView swipeRecyclerView; // 刷新组件

    private RepoListAdapter adapter;
    private List<Repo> datas;
    private int pageIndex = 1;
    private boolean isDBData = true; // 是否为数据库数据
    private long exitTime; // 退出时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initView();

        initData();
    }

    private void initView() {
        adapter = new RepoListAdapter(this);
        adapter.addOnItemClickLister(itemClickListener);
        swipeRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        swipeRecyclerView.getRecyclerView().setLayoutManager(layoutManager);

        swipeRecyclerView.getSwipeRefreshLayout().setColorSchemeResources(R.color.colorPrimary);
        swipeRecyclerView.setOnLoadListener(new SwipeRecyclerView.OnLoadListener() {
            @Override
            public void onRefresh() {
                if (checkNull()) {
                    swipeRecyclerView.scrollTop();
                    pageIndex = 1;
                    if (isDBData) {
                        showDBData();
                    } else {
                        requestList();
                    }
                }
            }

            @Override
            public void onLoadMore() {
                if (checkNull()) {
                    pageIndex++;
                    if (isDBData) {
                        showDBData();
                    } else {
                        requestList();
                    }
                }
            }
        });
        swipeRecyclerView.setEmptyView(EmptyView.get(this));

        // 回车搜索
        registerEnterSearch();
    }

    /**
     * 注册回车搜索
     */
    private void registerEnterSearch() {
        // 软键盘搜索
        etKeyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyBoard();
                    String word = etKeyword.getText().toString();
                    if (word.length() == 0) {
                        return false;
                    }
                    // 重新开始查询
                    loadData();
                }
                return false;
            }
        });
    }

    /**
     * 搜索按钮事件
     */
    @OnClick(R.id.btn_search)
    void search() {
        loadData();
    }

    /**
     * 初始化基础数据
     */
    private void initData() {
        if (isDBData) {
            Toast.makeText(MainActivity.this, "初始化，加载数据库数据...", Toast.LENGTH_SHORT).show();
            showDBData();
        }
    }

    /**
     * 显示数据库中的数据
     */
    private void showDBData() {
        List<Repo> datas = RepoDBService.getReposFromDB(pageIndex, Constant.PAGE_SIZE);
        if (datas == null || datas.size() == 0) {
            if (pageIndex > 1) {
                pageIndex--;
            } else {
                adapter.setList(new ArrayList<Repo>());
            }
            swipeRecyclerView.complete();
            return;
        }
        if (pageIndex == 1) {
            this.datas = datas;
        } else {
            this.datas.addAll(datas);
        }
        adapter.setList(this.datas);
        swipeRecyclerView.complete();
    }

    /**
     * 加载新数据
     */
    private void loadData() {
        swipeRecyclerView.setRefreshing(true);
    }

    /**
     * 检查必填项
     *
     * @return boolean
     */
    private boolean checkNull() {
        // 标记为在线查询方式，不再读取数据库
        if (isDBData && !CommonUtil.isNull(etKeyword)) {
            isDBData = false;
            Toast.makeText(MainActivity.this, "转为加载网络数据...", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (isDBData) {
            return true;
        }
        if (CommonUtil.isNull(etKeyword)) {
            Toast.makeText(MainActivity.this, "请输入关键词", Toast.LENGTH_SHORT).show();
            swipeRecyclerView.complete();
            return false;
        }
        return true;
    }

    /**
     * 查询分页信息
     */
    private void requestList() {
        hideKeyBoard();
        StringBuilder sb = new StringBuilder("?");
        sb.append("q=" + etKeyword.getText().toString());
        sb.append("&page=").append(String.valueOf(pageIndex));
        RetrofitRequest.sendGetRequest(Constant.URL_SEARCH + sb.toString(), RepoListResult.class, new RetrofitRequest.ResultHandler<RepoListResult>(this) {
            @Override
            public void onBeforeResult() {
                swipeRecyclerView.complete();
            }

            @Override
            public void onResult(RepoListResult result) {
                handleResult(result);
            }

            @Override
            public void onAfterFailure() {
                swipeRecyclerView.complete();
            }
        });
    }

    /**
     * 处理请求结果
     *
     * @param result
     */
    private void handleResult(RepoListResult result) {
        if (result != null) {
            List<Repo> datas = result.getItems();
            if (datas == null || datas.size() == 0) {
                if (pageIndex > 1) {
                    pageIndex--;
                } else {
                    adapter.setList(new ArrayList<Repo>());
                }
                swipeRecyclerView.complete();
                return;
            }
            if (pageIndex == 1) {
                this.datas = datas;
            } else {
                this.datas.addAll(datas);
            }
            adapter.setList(this.datas);

            // 存入数据库
            RepoDBService.saveRepos2DB(datas);
        }
        swipeRecyclerView.complete();
    }

    /**
     * 隐藏输入键盘
     */
    private void hideKeyBoard() {
        // 隐藏软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etKeyword.getWindowToken(), 0); // 强制隐藏键盘
    }

    private RepoListAdapter.OnItemClickListener itemClickListener = new RepoListAdapter.OnItemClickListener() {
        @Override
        public void onClick(View view, int position) {
            // 显示提醒
            Repo data = datas.get(position);
            if (data == null) {
                return;
            }
            Toast.makeText(MainActivity.this, "项目：" + CommonUtil.getNotNullString(data.getFull_name()), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            // 重写键盘事件分发，onKeyDown方法某些情况下捕获不到，只能在这里写
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Snackbar snackbar = Snackbar.make(swipeRecyclerView, "再按一次退出程序", Snackbar.LENGTH_SHORT);
                snackbar.getView().setBackgroundResource(R.color.colorPrimary);
                snackbar.show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // 清单文件中已配置了configChanges属性，避开了旋转时activity重建的过程;
        // 如果觉得有必要可以不配置，通过onSaveInstanceState/onRestoreInstanceState方法存储和恢复数据
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 竖屏
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 横屏
        }
        // 刷新显示
        super.onConfigurationChanged(newConfig);
    }
}
