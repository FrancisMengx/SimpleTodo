package com.example.xmeng.simpletodo;

import java.util.Map;

/**
 * Created by xmeng on 9/23/15.
 */
public class TodoItem {
    int id;
    String title;
    String description;
    String dueDate;

    public TodoItem () {
        this.title = "";
        this.description = "";
        this.dueDate = "";
    }

    public TodoItem (Map<String, String> params) {
        this.title = params.get("title");
        this.description = params.get("description");
        this.dueDate = params.get("dueDate");
    }

    public TodoItem (String title) {
        this.title = title;
    }

    public TodoItem (int id, String title) {
        this.id = id;
        this.title = title;
    }
    public void setId (int id) {
        this.id = id;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

}
