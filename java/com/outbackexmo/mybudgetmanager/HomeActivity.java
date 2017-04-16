package com.outbackexmo.mybudgetmanager;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    private InitView initView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView = new InitView(this);
        initView.viewInit();

    }

    public void dateInput(View view){
        DialogFragment dialogFragment = new DateInputFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("btnId", R.id.dateBtn);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getFragmentManager(), "date_input_dialog");
    }

    public void itemInput(View view){
        DialogFragment dialogFragment = new ItemSelectFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("btnId", R.id.itemBtn);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getFragmentManager(), "item_input_dialog");
    }

    public void registerBtn(View view){
        RegisterBudget registerBudget = new RegisterBudget(this);
        switch (registerBudget.saveBudget()){
            case 0:
                Toast.makeText(this, getString(R.string.successSave), Toast.LENGTH_LONG).show();
                initView.viewInit();
                break;
            case 1:
                Toast.makeText(this,"支出額を入力してください",Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(this,getString(R.string.failSave),Toast.LENGTH_LONG).show();
                break;
        }
    }

    public void clearBtn(View view){
        initView.viewInit();
    }

    public void viewBtn(View view){
        DialogFragment dialog = new SelectViewDialog();
        dialog.show(getFragmentManager(),"select_view");

    }

    public void settingBtn(View view){
        DialogFragment dialog = new SelectSettingDialog();
        dialog.show(getFragmentManager(), "select_setting");
    }


}
