package com.example.xmeng.simpletodo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;
import java.util.Date;
/**
 * Created by xmeng on 10/1/15.
 */
public class TodoAdapter extends ArrayAdapter<TodoItem>{
    public TodoAdapter(Context context, int resource, List<TodoItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item_layout, null);
        }

        TodoItem item = getItem(position);
        if (item != null) {
            TextView title = (TextView) v.findViewById(R.id.itemTitle);
            TextView description = (TextView) v.findViewById(R.id.itemDescription);
            TextView dueDate = (TextView) v.findViewById(R.id.dueDate);
            title.setText(item.getTitle());
            description.setText(item.getDescription());
            String due = item.getDueDate();
            String[] dueDateArr = due.split("/");
            dueDate.setText(dueDateArr[1] + "/" + dueDateArr[2]);
            Date now = new Date();
            Calendar dueDateObj = Calendar.getInstance();
            dueDateObj.set(Integer.parseInt(dueDateArr[0]), Integer.parseInt(dueDateArr[1])-1, Integer.parseInt(dueDateArr[2]));
            long diff = dueDateObj.getTime().getTime() - now.getTime();
            long diffDay = diff/(24*3600*1000);
            if (diffDay < 2){
                v.setBackgroundColor(Color.rgb(255, 204, 204));
            }else {
                v.setBackgroundColor(Color.rgb(255, 255, 255));

            }
        }
        return v;
    }
}
