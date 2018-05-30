package com.github.search.util;

import android.widget.EditText;

/**
 * 通用工具类
 */
public class CommonUtil {

    /**
     * 检查字符串是否为空
     *
     * @param str 输入字符串
     * @return boolean true/false
     */
    public static boolean isNull(String str) {
        return (str == null || str.length() == 0) ? true : false;
    }

    /**
     * 检查输入控件内容是否为空
     *
     * @param editText 输入控件
     * @return boolean true/false
     */
    public static boolean isNull(EditText editText) {
        if (editText == null) {
            return true;
        }
        String value = editText.getText().toString();
        if (value == null || value.length() == 0 || value.trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 获取非空形式的字符串
     *
     * @param str 字符串
     * @return String 非空串
     */
    public static String getNotNullString(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        return str;
    }
}
