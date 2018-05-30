package com.github.search.network.result;

import com.github.search.bean.Repo;

import java.util.List;

/**
 * 仓库数据列表结果
 */
public class RepoListResult extends BaseResult {
    private int total_count; // 总数
    private boolean incomplete_results;

    private List<Repo> items; // 列表

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public List<Repo> getItems() {
        return items;
    }

    public void setItems(List<Repo> items) {
        this.items = items;
    }
}
