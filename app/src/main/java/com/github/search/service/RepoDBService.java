package com.github.search.service;

import com.github.search.bean.Owner;
import com.github.search.bean.Repo;
import com.github.search.dao.DaoManager;
import com.github.search.greendao.DaoSession;
import com.github.search.greendao.OwnerDao;
import com.github.search.greendao.RepoDao;

import java.util.List;

/**
 * 仓库数据服务层
 */
public class RepoDBService {

    /**
     * 分页从数据库获取仓库列表
     *
     * @param page     页面
     * @param pageSize 页记录数
     * @return List<Repo> 数据列表
     */
    public static List<Repo> getReposFromDB(int page, int pageSize) {
        if (page < 1) {
            page = 1;
        }
        if (pageSize < 1) {
            pageSize = 30;
        }
        DaoSession daoSession = null;
        List<Repo> repos = null;
        try {
            daoSession = DaoManager.getInstance().getDaoSession();
            repos = daoSession.getRepoDao().queryBuilder().offset((page - 1) * pageSize).orderDesc(RepoDao.Properties.Id).limit(pageSize).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (daoSession != null) {
                daoSession.clear();
            }
        }
        return repos;
    }

    /**
     * 保存列表数据至数据库表中
     *
     * @param repos 列表
     */
    public static void saveRepos2DB(List<Repo> repos) {
        if (repos == null || repos.size() == 0) {
            return;
        }
        // 开启线程异步存储
        new RepoSaveThread(repos).start();
    }

    /**
     * 保存仓库实体信息入库
     * @param daoSession
     * @param repo
     */
    private static void saveRepo2DB(DaoSession daoSession, Repo repo) {
        if (repo.getOwner() != null) {
            // 设置关连键的值
            repo.setOwnerId(repo.getOwner().getId());
        }
        // 判重，存在则修改，不存在则插入
        Repo old = daoSession.getRepoDao().queryBuilder().where(RepoDao.Properties.Id.eq(repo.getId())).unique();
        if (old != null) {
            daoSession.getRepoDao().update(repo);
        } else {
            daoSession.getRepoDao().insert(repo);
        }
    }

    /**
     * 保存用户信息入库
     * @param daoSession
     * @param owner
     */
    private static void saveOwner2DB(DaoSession daoSession, Owner owner) {
        // 判重，存在则修改，不存在则插入
        Owner old = daoSession.getOwnerDao().queryBuilder().where(OwnerDao.Properties.Id.eq(owner.getId())).unique();
        if (old != null) {
            daoSession.getOwnerDao().update(owner);
        } else {
            daoSession.getOwnerDao().insert(owner);
        }
    }

    static class RepoSaveThread extends Thread {
        List<Repo> repos;

        public RepoSaveThread(List<Repo> repos) {
            this.repos = repos;
        }

        @Override
        public void run() {
            DaoSession daoSession = null;
            try {
                daoSession = DaoManager.getInstance().getDaoSession();
                for (Repo repo : repos) {
                    if (repo == null) {
                        continue;
                    }
                    // 保存仓库信息
                    saveRepo2DB(daoSession, repo);
                    if (repo.getOwner() == null) {
                        continue;
                    }
                    // 保存用户信息
                    saveOwner2DB(daoSession, repo.getOwner());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (daoSession != null) {
                    daoSession.clear();
                }
            }
        }
    }
}
