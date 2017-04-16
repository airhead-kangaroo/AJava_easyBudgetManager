package com.outbackexmo.mybudgetmanager;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MyServer_U on 2017/02/18.
 */

public class ExpenseListAdapter extends BaseAdapter {

    private Context context = null;
    private ArrayList<ExpenseListItem> data = null;
    private int resource = 0;

    public ExpenseListAdapter(Context context, ArrayList<ExpenseListItem> data, int resource){
        this.context = context;
        this.data = data;
        this.resource = resource;

    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity)context;
        ExpenseListItem item = (ExpenseListItem)getItem(position);
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(resource, null);
        }
        ((TextView)convertView.findViewById(R.id.expenseAmount)).setText(String.valueOf(item.getAmount()));
        ((TextView)convertView.findViewById(R.id.expenseDay)).setText(item.getExpenseDate());
        ((TextView)convertView.findViewById(R.id.expenseItem)).setText(item.getBudgetItem());
        return convertView;
    }
}
