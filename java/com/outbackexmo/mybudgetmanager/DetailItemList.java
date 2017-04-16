package com.outbackexmo.mybudgetmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailItemList extends AppCompatActivity {

    private SQLiteDatabase db;
    private ArrayList<HashMap<String,String>> data;
    private final String[] from = {"id", "itemName"};
    private DetailItemList activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item_list);

        Cursor c = createCursol();
        createHashMap(c);
        int[] to = {R.id.detailItemId, R.id.detailItemName};
        SimpleAdapter adapter = new SimpleAdapter(this,data,R.layout.itemlist,from,to);
        ListView list = (ListView)findViewById(R.id.itemList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(createListener());
        c.close();
        db.close();
    }

    private Cursor createCursol(){
        db = Utils.readableDBConnect(this);
        String[] cols = {"id","itemName"};
        return db.query("budgetItem",cols,null,null,null,null,"id");
    }

    private void createHashMap(Cursor c){
        data = new ArrayList<>();
        c.moveToFirst();
        do{
            HashMap<String,String> item = new HashMap<>();
            item.put(from[0],String.valueOf(c.getInt(0)));
            item.put(from[1],c.getString(1));
            data.add(item);
        }while(c.moveToNext());
    }

    private AdapterView.OnItemClickListener createListener(){
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView)view.findViewById(R.id.detailItemId);
                TextView tv2 = (TextView)view.findViewById(R.id.detailItemName);
                Intent intent = new Intent(activity,DetailItem.class);
                intent.putExtra("id",tv.getText().toString().trim());
                intent.putExtra("itemName",tv2.getText().toString().trim());
                startActivity(intent);
            }
        };
        return listener;
    }
}
