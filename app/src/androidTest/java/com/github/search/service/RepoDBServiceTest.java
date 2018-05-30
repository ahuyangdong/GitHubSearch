package com.github.search.service;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * DB业务层测试
 */
public class RepoDBServiceTest {

    @Test
    public void getReposFromDB() throws Exception {
        Assert.assertEquals(30, RepoDBService.getReposFromDB(1, 30).size());
    }

}