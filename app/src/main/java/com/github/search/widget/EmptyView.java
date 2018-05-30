package com.github.search.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.github.search.R;

/**
 * 空列表视图
 */
public class EmptyView {

    /**
     * 获取空白提示
     * @param context
     * @return View
     */
    public static View get(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.empty_view, null);
        return view;
    }
}
