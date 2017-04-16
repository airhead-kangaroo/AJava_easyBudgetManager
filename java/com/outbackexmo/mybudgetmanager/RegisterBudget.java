package com.outbackexmo.mybudgetmanager;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by MyServer_U on 2017/02/18.
 */

public class RegisterBudget {

    private BudgetDBOpenhelper helper = null;
    private Activity activity;
    private SQLiteDatabase db = null;
    private int itemId;
    private Button dateBtn;
    private EditText amount;
    private SimpleDateFormat sdf;
    private Date datenow;
    private String inputDateStr;

    public RegisterBudget(Activity activity){
        this.activity = activity;
        helper = new BudgetDBOpenhelper(this.activity);
    }

    public int saveBudget(){
        getViewRef();
        if(amountCheck()){
            return 1;
        }
        getItemId();
        datasetting();
        db = helper.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("amount", amount.getText().toString().trim());
            cv.put("item", itemId);
            cv.put("expenseDay", inputDateStr);
            cv.put("created", sdf.format(datenow));
            db.insert("budget", null, cv);
            db.close();
            return 0;
        } catch (Exception e) {
            e.getMessage();
            db.close();
            return 2;
        }

    }

    private void getItemId(){
        Button itemBtn = (Button)activity.findViewById(R.id.itemBtn);
        db = helper.getReadableDatabase();
        Cursor cs = null;
        String cols[] = {"id"};
        String params[] = {itemBtn.getText().toString().trim()};
        try {
            cs = db.query("budgetItem",cols,"itemName = ?",params,null,null,null);
            cs.moveToFirst();
            itemId = cs.getInt(0);
            cs.close();
        }catch (Exception e){
            e.getMessage();
        }
    }

    public void datasetting(){
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        SimpleDateFormat sdfParse = new SimpleDateFormat("yyyy/MM/dd",Locale.US);
        String dateStr = dateBtn.getText().toString().trim();
        int strlength = dateStr.length();
        String dateStrAfterSubstr = dateStr.substring(0, strlength - 3);
        try{
            Date inputDate = sdfParse.parse(dateStrAfterSubstr);
            inputDateStr = sdfParse.format(inputDate);
        }catch (Exception e){
            e.getMessage();
        }
        datenow = new Date();
    }

    private boolean amountCheck(){
        if(amount.getText().toString().trim().length() == 0 || amount.getText() == null){
            return true;
        }else{
            return false;
        }
    }

    private void getViewRef(){
        dateBtn = (Button)activity.findViewById(R.id.dateBtn);
        amount = (EditText)activity.findViewById(R.id.editArea);
    }


}
