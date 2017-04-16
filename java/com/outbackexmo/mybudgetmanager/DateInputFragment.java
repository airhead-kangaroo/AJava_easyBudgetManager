package com.outbackexmo.mybudgetmanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by MyServer_U on 2017/02/18.
 */

public class DateInputFragment extends DialogFragment {
    private Calendar c;
    private DatePickerDialog.OnDateSetListener listener;
    private int btnId;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        btnId = getArguments().getInt("btnId");
        setListener();
        c = Calendar.getInstance();
        return new DatePickerDialog(getActivity(), listener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }

    public void setListener() {
        listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Button btn = (Button) getActivity().findViewById(btnId);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd(E)", Locale.JAPAN);
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                btn.setText(sdf.format(c.getTime()));
            }
        };
    }
}

