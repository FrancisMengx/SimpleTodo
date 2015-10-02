package com.example.xmeng.simpletodo;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xmeng on 9/21/15.
 */
public class EditItemDialog extends DialogFragment{


    public interface EditItemDialogListener {
        public void onFinishEditing(TodoItem newItem, int itemPos);
    }

    private EditText editItemTitle;
    private EditText editItemDescription;
    private Button editBtn;
    private DatePicker dueDatePicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.edit_item_dialog_layout, container);

        editItemTitle = (EditText) view.findViewById(R.id.editItemTitle);
        editItemDescription = (EditText) view.findViewById(R.id.editItemDescription);
        editBtn = (Button) view.findViewById(R.id.btnEdit);
        this.dueDatePicker = (DatePicker) view.findViewById(R.id.dueDatePicker);

        String itemTitle = getArguments().getString("itemTitle");
        String itemDescritpion = getArguments().getString("itemDescription");
        String itemDueDate = getArguments().getString("itemDueDate");
        if(itemTitle == null) {
            getDialog().setTitle("Add Item");
        } else {
            String[] dateArr = itemDueDate.split("/");
            getDialog().setTitle("Edit Item");
            editItemTitle.setText(itemTitle);
            editItemDescription.setText(itemDescritpion);
            dueDatePicker.updateDate(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1])-1, Integer.parseInt(dateArr[2]));
        }
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditItem();
            }
        });
        return view;
    }

    public void onEditItem() {
        Map paramMap = new HashMap();
        int year = this.dueDatePicker.getYear();
        int month = this.dueDatePicker.getMonth();
        month++;
        int day = this.dueDatePicker.getDayOfMonth();
        paramMap.put("title", this.editItemTitle.getText().toString());
        paramMap.put("description", this.editItemDescription.getText().toString());
        paramMap.put("dueDate", year + "/" + month + "/" + day);
        TodoItem newItem = new TodoItem(paramMap);
        EditItemDialogListener activity = (EditItemDialogListener) getActivity();
        activity.onFinishEditing(newItem, getArguments().getInt("itemPos"));
        this.dismiss();
    }
}
