package com.outbackexmo.mybudgetmanager;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.StringDef;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DetailExpense extends AppCompatActivity {
    private long expenseId;
    private SQLiteDatabase db;
    private BudgetDBOpenhelper helper;
    private Button dateBtn;
    private Button itemBtn;
    private EditText memo;
    private EditText detailAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_expense);

        Intent intent = this.getIntent();
        expenseId = intent.getLongExtra("expenseId",0);
        Cursor cs = getExpenseData();
        cs.moveToFirst();
        setView(cs);
        cs.close();
        db.close();
    }

    private Cursor getExpenseData(){
        connectDB();
        Cursor cs = null;
        String args[] = {String.valueOf(expenseId)};
        cs = db.rawQuery("select budget.id,budget.amount, budget.expenseDay, budgetItem.itemName, budget.memo from budget inner join budgetItem on budget.id = budgetItem.id where budget.id = ?", args);
        return cs;
    }

    private void setView(Cursor cs){
        dateBtn = (Button)findViewById(R.id.detailDate);
        detailAmount = (EditText)findViewById(R.id.detailAmount);
        itemBtn = (Button)findViewById(R.id.detailItem);
        memo = (EditText)findViewById(R.id.memo);
        dateBtn.setText(cs.getString(2));
        detailAmount.setText(String.valueOf(cs.getInt(1)));
        itemBtn.setText(cs.getString(3));
        memo.setText(cs.getString(4));
    }

    public void detailDate(View view){
        DialogFragment dialogFragment = new DateInputFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("btnId", R.id.detailDate);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getFragmentManager(), "date_input_dialog");
    }

    public void detailItem(View view){
        DialogFragment dialogFragment = new ItemSelectFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("btnId", R.id.detailItem);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getFragmentManager(), "item_input_dialog");
    }

    public void editBtn(View view){
        String amount = detailAmount.getText().toString().trim();
        String day = dateBtn.getText().toString().trim();
        String item = getItemId();
        String memoStr = memo.getText().toString().trim();
        ContentValues cv = new ContentValues();
        cv.put("amount", amount);
        cv.put("item", item);
        cv.put("expenseDay", day);
        cv.put("memo", memoStr);
        String[] id = {String.valueOf(expenseId)};
        try{
            db.update("budget",cv,"id = ?", id);
            Toast.makeText(this,"保存されました",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.getMessage();
            Toast.makeText(this,"保存に失敗しました",Toast.LENGTH_LONG).show();
        }
        db.close();
        finish();
    }

    public void deleteBtn(View view){
        DialogFragment dialog = new ConfirmAlertDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", "このデータを削除してもよろしいですか？");
        bundle.putLong("expenseId", expenseId);
        dialog.setArguments(bundle);
        dialog.show(getFragmentManager(), "confirmDialog");
    }

    private void connectDB(){
        helper = new BudgetDBOpenhelper(this);
        db = helper.getReadableDatabase();
    }

    private String getItemId(){
        connectDB();
        String item = itemBtn.getText().toString().trim();
        String[] cols = {"id"};
        String[] args = {item};
        Cursor cs = null;
        cs = db.query("budgetItem",cols,"itemName = ?",args,null,null,null);
        cs.moveToFirst();
        return String.valueOf(cs.getInt(0));
    }

    public void backBtn(View view){
        finish();
//        Toast.makeText(this,"test",Toast.LENGTH_SHORT);
    }
}
