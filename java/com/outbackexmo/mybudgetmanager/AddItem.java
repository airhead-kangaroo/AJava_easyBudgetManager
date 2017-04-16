package com.outbackexmo.mybudgetmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }

    public void viewBtn(View view){
        Intent intent = new Intent(this,DetailItemList.class);
        startActivity(intent);

    }

    public void backBtn(View view){
        finish();
    }

    public void saveAddItem(View view){
        SQLiteDatabase db = Utils.writableDBConnect(this);
        ContentValues cv = cvcreate();
        try{
            db.insert("budgetItem",null,cv);
            Toast.makeText(this,getString(R.string.successSave),Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.getMessage();
            Toast.makeText(this,getString(R.string.failSave),Toast.LENGTH_LONG).show();
        }
        cv.clear();
        db.close();
    }

    private ContentValues cvcreate(){
        EditText et = (EditText)findViewById(R.id.addItem) ;
        String item = et.getText().toString().trim();
        ContentValues cv = new ContentValues();
        cv.put("itemName", item);
        return cv;
    }
}
