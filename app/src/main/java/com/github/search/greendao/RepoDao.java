package com.github.search.greendao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.github.search.bean.Owner;

import com.github.search.bean.Repo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "REPO".
*/
public class RepoDao extends AbstractDao<Repo, Long> {

    public static final String TABLENAME = "REPO";

    /**
     * Properties of entity Repo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property Full_name = new Property(1, String.class, "full_name", false, "FULL_NAME");
        public final static Property OwnerId = new Property(2, long.class, "ownerId", false, "OWNER_ID");
        public final static Property Description = new Property(3, String.class, "description", false, "DESCRIPTION");
        public final static Property Stargazers_count = new Property(4, int.class, "stargazers_count", false, "STARGAZERS_COUNT");
        public final static Property Watchers_count = new Property(5, int.class, "watchers_count", false, "WATCHERS_COUNT");
    }

    private DaoSession daoSession;


    public RepoDao(DaoConfig config) {
        super(config);
    }
    
    public RepoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"REPO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: id
                "\"FULL_NAME\" TEXT," + // 1: full_name
                "\"OWNER_ID\" INTEGER NOT NULL ," + // 2: ownerId
                "\"DESCRIPTION\" TEXT," + // 3: description
                "\"STARGAZERS_COUNT\" INTEGER NOT NULL ," + // 4: stargazers_count
                "\"WATCHERS_COUNT\" INTEGER NOT NULL );"); // 5: watchers_count
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"REPO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Repo entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String full_name = entity.getFull_name();
        if (full_name != null) {
            stmt.bindString(2, full_name);
        }
        stmt.bindLong(3, entity.getOwnerId());
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(4, description);
        }
        stmt.bindLong(5, entity.getStargazers_count());
        stmt.bindLong(6, entity.getWatchers_count());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Repo entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String full_name = entity.getFull_name();
        if (full_name != null) {
            stmt.bindString(2, full_name);
        }
        stmt.bindLong(3, entity.getOwnerId());
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(4, description);
        }
        stmt.bindLong(5, entity.getStargazers_count());
        stmt.bindLong(6, entity.getWatchers_count());
    }

    @Override
    protected final void attachEntity(Repo entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public Repo readEntity(Cursor cursor, int offset) {
        Repo entity = new Repo( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // full_name
            cursor.getLong(offset + 2), // ownerId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // description
            cursor.getInt(offset + 4), // stargazers_count
            cursor.getInt(offset + 5) // watchers_count
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Repo entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setFull_name(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setOwnerId(cursor.getLong(offset + 2));
        entity.setDescription(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setStargazers_count(cursor.getInt(offset + 4));
        entity.setWatchers_count(cursor.getInt(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Repo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Repo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Repo entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getOwnerDao().getAllColumns());
            builder.append(" FROM REPO T");
            builder.append(" LEFT JOIN OWNER T0 ON T.\"OWNER_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Repo loadCurrentDeep(Cursor cursor, boolean lock) {
        Repo entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Owner ownerDB = loadCurrentOther(daoSession.getOwnerDao(), cursor, offset);
         if(ownerDB != null) {
            entity.setOwnerDB(ownerDB);
        }

        return entity;    
    }

    public Repo loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Repo> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Repo> list = new ArrayList<Repo>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Repo> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Repo> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
