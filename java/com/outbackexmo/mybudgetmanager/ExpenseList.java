package com.outbackexmo.mybudgetmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class ExpenseList extends AppCompatActivity {
    private ArrayList<ExpenseListItem> data;
    private AdapterView.OnItemClickListener listener;
    private SQLiteDatabase db;
    private ExpenseList thisActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);

        createArrayList();
        setListener();

        ExpenseListAdapter adapter = new ExpenseListAdapter(this, data,R.layout.expenselist);
        ListView list = (ListView)findViewById(R.id.budgetListview);
        list.setAdapter(adapter);
        list.setOnItemClickListener(listener);

    }

    private void createArrayList(){
        data = new ArrayList<>();
        Cursor cs = getDBdata();
        cs.moveToFirst();
        try{
            do{
                ExpenseListItem item = new ExpenseListItem();
                item.setAmount(cs.getInt(1));
                item.setId((long)cs.getInt(0));
                item.setBudgetItem(cs.getString(3));
                item.setExpenseDate(cs.getString(2));
                data.add(item);
            } while(cs.moveToNext());
        }catch (Exception e){
            e.getMessage();
            Toast.makeText(this,"データが1件もありません",Toast.LENGTH_LONG).show();
        }

        cs.close();
        db.close();

    }

    private Cursor getDBdata(){
        db = Utils.writableDBConnect(this);
        Cursor cs = null;
        try{
            cs = db.rawQuery("select budget.id,budget.amount, budget.expenseDay, budgetItem.itemName from budget inner join budgetItem on budget.item = budgetItem.id", null);
        }catch (Exception e){
            e.getMessage();
        }
        return cs;
    }

    private void setListener(){
        listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(thisActivity,DetailExpense.class);
                intent.putExtra("expenseId",id);
                startActivity(intent);
            }
        };
    }
}
