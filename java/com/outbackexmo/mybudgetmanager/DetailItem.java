package com.outbackexmo.mybudgetmanager;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DetailItem extends AppCompatActivity {
    private String Itemid;
    private DetailItem activity = this;
    private String itemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);
        Intent intent = getIntent();
        Itemid = intent.getStringExtra("id");
        itemName = intent.getStringExtra("itemName");
        viewCreate();
    }

    public void backBtn(View view){
        finish();
    }

    public void detailItemEditSave(View view){
        SQLiteDatabase db = Utils.writableDBConnect(this);
        EditText et = (EditText)findViewById(R.id.detailItemEdit);
        String itemNameLocal = et.getText().toString().trim();
        ContentValues cv = new ContentValues();
        cv.put("id",Itemid);
        cv.put("itemName",itemNameLocal);
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
    public void detailItemEditDelete(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("削除確認");
        builder.setMessage(getString(R.string.deleteConfirmMessage));
        builder.setPositiveButton(getString(R.string.ok),getListener());
        builder.setNegativeButton(getString(R.string.cancel),getNegativeListener());
        builder.create().show();
    }

    private DialogInterface.OnClickListener getListener(){
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = Utils.writableDBConnect(activity);
                String args[] = {Itemid};
                try{
                    db.delete("budgetItem","id = ?",args);
                    Toast.makeText(activity,getString(R.string.successSave),Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.getMessage();
                    Toast.makeText(activity,getString(R.string.failSave),Toast.LENGTH_LONG).show();
                }
                db.close();
                activity.finish();
            }
        };
        return listener;
    }

    private DialogInterface.OnClickListener getNegativeListener(){
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(activity,getString(R.string.cancelDelete),Toast.LENGTH_LONG);
            }
        };
        return listener;
    }

    private void viewCreate(){
        EditText et = (EditText)findViewById(R.id.detailItemEdit);
        et.setText(itemName);
    }
}
