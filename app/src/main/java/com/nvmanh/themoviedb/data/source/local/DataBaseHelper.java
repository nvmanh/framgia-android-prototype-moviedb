package com.nvmanh.themoviedb.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.nvmanh.themoviedb.data.Genre;
import com.nvmanh.themoviedb.data.Movie;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * DataBaseHelper
 * Created by ThongDang on 6/1/16.
 */
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "the_movie.db";
    private static final int DATABASE_VERSION = 2;
    private Map<String, Dao<?, ?>> daoMap = new HashMap<>();

    public static final Class<?>[] databaseClasses = new Class[] {
            Movie.class, Genre.class
    };

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            for (Class obj : databaseClasses) {
                TableUtils.createTable(connectionSource, obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
            int oldVersion, int newVersion) {
        if (oldVersion == newVersion) return;
        try {
            for (Class obj : databaseClasses) {
                TableUtils.dropTable(connectionSource, obj, false);
            }
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(getClass().getName(), e.getMessage());
        }
    }

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public <T, ID> Dao<T, ID> getCachedDao(Class<T> clazz) {
        Dao<?, ?> result = daoMap.get(clazz.getName());
        if (result == null) {
            try {
                result = getDao(clazz);
                daoMap.put(clazz.getName(), result);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return (Dao<T, ID>) result;
    }
}