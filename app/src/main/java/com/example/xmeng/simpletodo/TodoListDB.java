package com.example.xmeng.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by xmeng on 9/22/15.
 */
public class TodoListDB {
    private Context context;
    private TodoDBHelper helper;
    private SQLiteDatabase db;
    public static final String TABLE_NAME = "todo_item";
    public static final String COLUMN_NAME_ITEM_ID = "item_id";
    public static final String COLUMN_NAME_ITEM_TITLE = "item_title";
    public static final String COLUMN_NAME_ITEM_DESCRIPTION = "item_description";
    public static final String COLUMN_NAME_ITEM_STATUS = "status";
    public static final String COLUMN_NAME_ITEM_DUE = "due_date";

    public TodoListDB (Context context) {
        this.context = context;
    }

    public TodoListDB open() {
        this.helper = new TodoDBHelper(this.context);
        this.db = this.helper.getWritableDatabase();
        return this;
    }

    public long addTodoItem (TodoItem item) {
        ContentValues itemMap = new ContentValues();
        itemMap.put(COLUMN_NAME_ITEM_TITLE, item.getTitle());
        itemMap.put(COLUMN_NAME_ITEM_DESCRIPTION, item.getDescription());
        itemMap.put(COLUMN_NAME_ITEM_DUE, item.getDueDate());
        return this.db.insert(TABLE_NAME, null, itemMap);
    }

    public boolean removeTodoItem (TodoItem item) {
        return this.db.delete(TABLE_NAME, COLUMN_NAME_ITEM_ID + "=" + item.getId(), null) > 0;
    }

    public boolean updateItem (TodoItem item) {
        ContentValues itemMap = new ContentValues();
        itemMap.put(COLUMN_NAME_ITEM_TITLE, item.getTitle());
        itemMap.put(COLUMN_NAME_ITEM_DESCRIPTION, item.getDescription());
        itemMap.put(COLUMN_NAME_ITEM_DUE, item.getDueDate());
        return this.db.update(TABLE_NAME, itemMap, COLUMN_NAME_ITEM_ID + "=" + item.getId(), null) > 0;
    }

    public ArrayList<TodoItem> getAllItem() {
        ArrayList<TodoItem> items = new ArrayList<TodoItem>();
        String[] columns = {COLUMN_NAME_ITEM_ID, COLUMN_NAME_ITEM_TITLE,
                COLUMN_NAME_ITEM_DESCRIPTION, COLUMN_NAME_ITEM_DUE};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        int iId = cursor.getColumnIndex(COLUMN_NAME_ITEM_ID);
        int iTitle = cursor.getColumnIndex(COLUMN_NAME_ITEM_TITLE);
        int iDescription = cursor.getColumnIndex(COLUMN_NAME_ITEM_DESCRIPTION);
        int iDueDate = cursor.getColumnIndex(COLUMN_NAME_ITEM_DUE);
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            TodoItem currentItem = new TodoItem(cursor.getString(iTitle));
            currentItem.setId(cursor.getInt(iId));
            currentItem.setDescription(cursor.getString(iDescription));
            currentItem.setDueDate(cursor.getString(iDueDate));
            items.add(currentItem);
        }


        return items;
    }
}
