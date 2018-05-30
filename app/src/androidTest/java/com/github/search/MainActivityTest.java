package com.github.search;

import android.content.Intent;
import android.test.ActivityUnitTestCase;

import org.junit.Before;
import org.junit.Test;

import deadline.swiperecyclerview.SwipeRecyclerView;

import static org.junit.Assert.*;

/**
 * MainActivity单元测试
 */
public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {
    private Intent intent;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        intent = new Intent(getInstrumentation().getTargetContext(), MainActivity.class);
    }

    @Test
    public void testsearch() throws Exception {
        startActivity(intent, null, null);
        SwipeRecyclerView swipeRecyclerView = getActivity().findViewById(R.id.swipe_fresh);
        int dataCount = swipeRecyclerView.getRecyclerView().getAdapter().getItemCount();
        assertEquals("初始化数据库数据...", 30, dataCount);
    }

}