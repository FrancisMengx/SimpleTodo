package com.example.xmeng.simpletodo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xmeng on 9/23/15.
 */
public class TodoDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "simpleTodoList";
    public static final int DATABASE_VERSION = 5;
    public static final String TABLE_NAME = "todo_item";
    public static final String COLUMN_NAME_ITEM_ID = "item_id";
    public static final String COLUMN_NAME_ITEM_TITLE = "item_title";
    public static final String COLUMN_NAME_ITEM_DESCRIPTION = "item_description";
    public static final String COLUMN_NAME_ITEM_STATUS = "status";
    public static final String COLUMN_NAME_ITEM_DUE = "due_date";
    public static final String CREATE_TABLE_TODO_ITEM = "CREATE TABLE "+ TABLE_NAME
            +" (" + COLUMN_NAME_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME_ITEM_TITLE + " VARCHAR(256),"
            + COLUMN_NAME_ITEM_DESCRIPTION + " VARCHAR(512),"
            + COLUMN_NAME_ITEM_DUE + " VARCHAR(16))";

    public TodoDBHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TODO_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
