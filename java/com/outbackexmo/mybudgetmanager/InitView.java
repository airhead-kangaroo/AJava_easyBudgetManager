package com.outbackexmo.mybudgetmanager;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by MyServer_U on 2017/02/18.
 */

public class InitView {

    private Activity activity;
    private SQLiteDatabase db;
    private Cursor cursor;
    private int calcAmount;

    public InitView(Activity activity){
        this.activity = activity;
    }

    private String setDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd(E)", Locale.JAPAN);
        return sdf.format(c.getTime());
    }

    public void viewInit(){
        Button dateBtn = (Button)activity.findViewById(R.id.dateBtn);
        Button itemBtn = (Button)activity.findViewById(R.id.itemBtn);
        EditText et = (EditText)activity.findViewById(R.id.editArea);
        TextView totalAmount = (TextView)activity.findViewById(R.id.totalAmount);
        TextView remainAmount = (TextView)activity.findViewById(R.id.remainAmount);
        calcSumAmount();
        et.setText("");
        dateBtn.setText(setDate());
        itemBtn.setText(getinitialItem());
        totalAmount.setText(String.valueOf(calcAmount));
        remainAmount.setText(String.valueOf(10000 - calcAmount));
        db.close();
    }

    private String getinitialItem(){
        db = Utils.readableDBConnect(activity);
        Cursor c = null;
        String[] cols = {"itemName"};
        String item = null;
        try{
            c = db.query("budgetItem",cols,null,null,null,null,"id ","1");
            c.moveToFirst();
            item = c.getString(0);
        }catch (Exception e){
            e.getMessage();
        }
        c.close();
        db.close();
        return item;
    }

    private void calcSumAmount(){
        Calendar c = Calendar.getInstance();
        Calendar firstDay = Utils.getFirstDayOfMonth(c.get(Calendar.YEAR),c.get(Calendar.MONTH));
        Calendar lastDay = Utils.getLastDayOfMonth(c.get(Calendar.YEAR),c.get(Calendar.MONTH));
        db = Utils.writableDBConnect(activity);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        String[] args = { sdf.format(firstDay.getTime()), sdf.format(lastDay.getTime())};
        try{
            cursor = db.rawQuery("select sum(amount) from budget where expenseDay between ? and ?",args);
            cursor.moveToFirst();
        }catch (Exception e){
            e.getMessage();
        }
        if(cursor != null){
            calcAmount = cursor.getInt(0);
        }else{
            calcAmount = 0;
        }
    }

}
