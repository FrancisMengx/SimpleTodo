package com.example.xmeng.simpletodo;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity implements EditItemDialog.EditItemDialogListener {
    ArrayList<TodoItem> items;
    TodoAdapter itemsAdapter;
    TodoListDB db;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        this.db = new TodoListDB(this).open();
        items = this.db.getAllItem();
        itemsAdapter = new TodoAdapter(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        TodoItem itemToRemove = items.get(position);
                        System.out.println(itemToRemove.getId());
                        items.remove(position);
                        db.removeTodoItem(itemToRemove);
                        itemsAdapter.notifyDataSetChanged();
                        return true;
                    }
                }
        );
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle args = new Bundle();
                        args.putString("itemTitle", items.get(position).getTitle());
                        args.putString("itemDescription", items.get(position).getDescription());
                        args.putString("itemDueDate", items.get(position).getDueDate());
                        args.putInt("itemPos", position);
                        FragmentManager fm = getFragmentManager();
                        DialogFragment editDialog = new EditItemDialog();
                        editDialog.setArguments(args);
                        editDialog.show(fm, "edit_dialog");
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View v) {
        Bundle args = new Bundle();
        args.putString("itemTitle", null);
        args.putInt("itemPos", -1);
        FragmentManager fm = getFragmentManager();
        EditItemDialog editDialog = new EditItemDialog();
        editDialog.setArguments(args);
        editDialog.show(fm, "add_dialog");
    }

    @Override
    public void onFinishEditing(TodoItem newItem, int itemPos) {
        if(itemPos < 0) {
            int id = (int) this.db.addTodoItem(newItem);
            newItem.setId(id);
            itemsAdapter.add(newItem);
        } else {
            items.get(itemPos).setTitle(newItem.getTitle());
            items.get(itemPos).setDescription(newItem.getDescription());
            items.get(itemPos).setDueDate(newItem.getDueDate());
            itemsAdapter.notifyDataSetChanged();
            this.db.updateItem(items.get(itemPos));
        }
    }
}
