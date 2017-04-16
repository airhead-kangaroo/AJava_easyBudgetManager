package com.outbackexmo.mybudgetmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by MyServer_U on 2017/02/18.
 */

public class ConfirmAlertDialogFragment extends DialogFragment {
    private long expenseId;
    private DialogInterface.OnClickListener positiveListener;
    private DialogInterface.OnClickListener negativeListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String confirmMessage = getArguments().getString("message");
        expenseId = getArguments().getLong("expenseId");
        setPositiveListener();
        setNegativeListener();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("確認")
                .setMessage(confirmMessage)
                .setPositiveButton("OK",positiveListener)
                .setNegativeButton("キャンセル",negativeListener)
                .create();
    }

    public void setPositiveListener(){
        positiveListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BudgetDBOpenhelper helper = new BudgetDBOpenhelper(getActivity());
                SQLiteDatabase db = helper.getWritableDatabase();
                String[] params = {String.valueOf(expenseId)};
                db.delete("budget", "id = ?", params);
                db.close();
                getActivity().finish();
            }
        };
    }

    public void setNegativeListener(){
        negativeListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(),"削除を中止しました。", Toast.LENGTH_SHORT);
            }
        };
    }
}
