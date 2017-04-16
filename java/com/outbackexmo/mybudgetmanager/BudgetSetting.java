package com.outbackexmo.mybudgetmanager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class BudgetSetting extends AppCompatActivity {
    private SQLiteDatabase db;
    private EditText et;
    String dbargs[] = {"1"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_setting);
        initview();
    }

    public void backBtn(View view){
        finish();
    }

    public void saveBudget(View view){
        et = (EditText)findViewById(R.id.budgetSetting);
        db = Utils.writableDBConnect(this);
        ContentValues cv = new ContentValues();
        cv.put("budget",et.getText().toString().trim());
        try{
            db.update("monthlyBudget",cv,"id = ?",dbargs);
            Toast.makeText(this,getString(R.string.successSave),Toast.LENGTH_LONG);

        }catch (Exception e){
            e.getMessage();
            Toast.makeText(this,getString(R.string.failSave),Toast.LENGTH_LONG);
        }
        db.close();
    }

    private void initview(){
        et = (EditText)findViewById(R.id.budgetSetting);
        db = Utils.readableDBConnect(this);
        Cursor c = null;
        String cols[] = {"budget"};
        try{
            c = db.query("monthlyBudget",cols, "id = ?",dbargs,null,null,null);
            c.moveToFirst();
            et.setText(String.valueOf(c.getInt(0)));
            c.close();
        }catch (Exception e){
            e.getMessage();
        }
        db.close();
    }
}
