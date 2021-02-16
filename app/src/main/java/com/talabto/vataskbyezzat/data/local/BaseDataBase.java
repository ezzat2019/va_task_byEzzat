package com.talabto.vataskbyezzat.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.talabto.vataskbyezzat.data.dao.MathDao;
import com.talabto.vataskbyezzat.data.model.MathModel;

@Database(entities = {MathModel.class}, version = 8, exportSchema = false)
abstract public class BaseDataBase extends RoomDatabase {
    private static volatile BaseDataBase INSTANCE;

    static public BaseDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BaseDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BaseDataBase.class, "math_database8")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    abstract public MathDao getMathDao();

}
