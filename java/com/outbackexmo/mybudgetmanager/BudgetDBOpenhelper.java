package com.outbackexmo.mybudgetmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MyServer_U on 2017/02/18.
 */

public class BudgetDBOpenhelper extends SQLiteOpenHelper {

    static final private String DBNAME = "MyBudgetManagerDB.sqlite";
    static final private int VERSION = 5;

    public BudgetDBOpenhelper(Context context){
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table budget(id integer primary key autoincrement, amount integer not null, item integer not null, expenseDay text not null, created text not null, memo default null)");
        db.execSQL("create table budgetItem(id integer primary key autoincrement, itemName text not null unique)");
        db.execSQL("create table monthlyBudget(id integer primary key not null,budget int not null)");
        db.execSQL("insert into budgetItem(itemName) values('食費')");
        db.execSQL("insert into budgetItem(itemName) values('電車代')");
        db.execSQL("insert into budgetItem(itemName) values('本代')");
        db.execSQL("insert into monthlyBudget values(1, 10000)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists budget");
        db.execSQL("drop table if exists budgetItem");
        onCreate(db);
    }
}
