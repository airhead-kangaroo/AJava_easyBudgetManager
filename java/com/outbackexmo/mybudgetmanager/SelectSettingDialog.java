package com.outbackexmo.mybudgetmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by MyServer_U on 2017/02/19.
 */

public class SelectSettingDialog extends DialogFragment {
    private static final String[] settings = {"項目追加","予算設定"};
    private static final Class[] links = {
            AddItem.class,
            BudgetSetting.class,
    };
    private DialogInterface.OnClickListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setListener();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle("設定を選択")
                .setItems(settings,listener)
                .create();
    }

    private void setListener(){
        listener = new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(),links[which]);
                startActivity(intent);
            }
        };
    }
}
