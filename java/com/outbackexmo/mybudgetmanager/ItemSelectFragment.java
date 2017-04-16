package com.outbackexmo.mybudgetmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by MyServer_U on 2017/02/18.
 */

public class ItemSelectFragment extends DialogFragment {

    private ArrayList<String> list;
    private DialogInterface.OnClickListener listener;
    private int selected = 0;
    private int btnId;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        btnId = getArguments().getInt("btnId");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        fragmentSetting();
        setListener();
        return builder
                .setTitle(getString(R.string.selectItemMessage))
                .setSingleChoiceItems(list.toArray(new CharSequence[list.size()]), selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selected = which;
                    }
                })
                .setPositiveButton("OK",listener)
                .show();
    }

    private void fragmentSetting(){
        list = new ArrayList<>();
        SQLiteDatabase db = Utils.writableDBConnect(getActivity());
        Cursor c = null;
        c = db.rawQuery("select itemName from budgetItem",null);
        c.moveToFirst();
        do{
            list.add(c.getString(0));
        }while(c.moveToNext());
        c.close();
    }

    private void setListener(){
        listener = new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int which){
                Button btn = (Button)getActivity().findViewById(btnId);
                btn.setText(list.get(selected));
            }
        };
    }


}
