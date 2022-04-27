package com.example.notification_manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqlDataBaseHelper extends SQLiteOpenHelper {

    private static final String DataBaseName = "DataBaseIt";
    private static final int DataBaseVersion = 1;

    public SqlDataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version,String TableName) {
        super(context, DataBaseName, null, DataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SqlTable = "CREATE TABLE IF NOT EXISTS Users (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "packageName TEXT not null," +
                "title TEXT not null," +
                "text TEXT not null," +
                "timestamp INTEGER not null" +
                ")";
        sqLiteDatabase.execSQL(SqlTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String SQL = "DROP TABLE Users";
        sqLiteDatabase.execSQL(SQL);
    }
}
