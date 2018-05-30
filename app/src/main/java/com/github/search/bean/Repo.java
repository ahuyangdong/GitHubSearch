package com.github.search.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import com.github.search.greendao.DaoSession;
import com.github.search.greendao.OwnerDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;

import com.github.search.greendao.RepoDao;

/**
 * 仓库数据对象
 */
@Entity
public class Repo {
    @Id
    private long id; // 主键

    @Property
    private String full_name;

    @Transient
    private Owner owner; // 作者，关联对象，不入数据库

    private long ownerId;
    @ToOne(joinProperty = "ownerId")
    private Owner ownerDB; // 作者，关联对象，与数据库进行外键关联

    @Property
    private String description;
    @Property
    private int stargazers_count;
    @Property
    private int watchers_count;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 323649397)
    private transient RepoDao myDao;

    @Generated(hash = 146004342)
    public Repo(long id, String full_name, long ownerId, String description,
                int stargazers_count, int watchers_count) {
        this.id = id;
        this.full_name = full_name;
        this.ownerId = ownerId;
        this.description = description;
        this.stargazers_count = stargazers_count;
        this.watchers_count = watchers_count;
    }

    @Generated(hash = 1082775620)
    public Repo() {
    }

    @Generated(hash = 2109130897)
    private transient Long ownerDB__resolvedKey;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(int stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public int getWatchers_count() {
        return watchers_count;
    }

    public void setWatchers_count(int watchers_count) {
        this.watchers_count = watchers_count;
    }

    public long getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1752596705)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRepoDao() : null;
    }

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 1272138649)
    public Owner getOwnerDB() {
        long __key = this.ownerId;
        if (ownerDB__resolvedKey == null || !ownerDB__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            OwnerDao targetDao = daoSession.getOwnerDao();
            Owner ownerDBNew = targetDao.load(__key);
            synchronized (this) {
                ownerDB = ownerDBNew;
                ownerDB__resolvedKey = __key;
            }
        }
        return ownerDB;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 808427992)
    public void setOwnerDB(@NotNull Owner ownerDB) {
        if (ownerDB == null) {
            throw new DaoException(
                    "To-one property 'ownerId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.ownerDB = ownerDB;
            ownerId = ownerDB.getId();
            ownerDB__resolvedKey = ownerId;
        }
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
