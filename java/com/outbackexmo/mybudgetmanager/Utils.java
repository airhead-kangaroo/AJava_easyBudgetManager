package com.outbackexmo.mybudgetmanager;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;

/**
 * Created by MyServer_U on 2017/02/19.
 */

public class Utils {

    public static SQLiteDatabase writableDBConnect(Activity activity){
        BudgetDBOpenhelper helper = new BudgetDBOpenhelper(activity);
        SQLiteDatabase db = helper.getWritableDatabase();
        return db;
    }

    public static SQLiteDatabase readableDBConnect(Activity activity){
        BudgetDBOpenhelper helper = new BudgetDBOpenhelper(activity);
        SQLiteDatabase db = helper.getReadableDatabase();
        return db;
    }

    public static Calendar getFirstDayOfMonth(int year, int month){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,1);
        return c;
    }

    public static Calendar getLastDayOfMonth(int year, int month){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,c.getActualMaximum(Calendar.DATE));
        return c;
    }

}
