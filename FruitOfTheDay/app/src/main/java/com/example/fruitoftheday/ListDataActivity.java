package com.example.fruitoftheday;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fruitoftheday.Helper.DatabaseHelper;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";

    DatabaseHelper mDatabaseHelper;

    private ListView mListView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_layout);

        mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);

        populateView();
    }

    private void populateView() {
        Log.d(TAG, "populateListView: Displaying data in ListView");

        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        //TODO: HashMap<String, Integer> listData = new HashMap<>();
        while(data.moveToNext())
        {
            // get value from column 1(in database helper named column 2) and add to array
            listData.add(data.getString(1)); //TODO: get likepercentage too
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                String name = adapterView.getItemAtPosition(i).toString();
                //TODO: likepercentage
                Log.d(TAG, "onItemClick: You clicked on: " + name);

                Cursor data = mDatabaseHelper.getItemID(name); // get id associated with name
                int itemID = -1;
                while(data.moveToNext())
                {
                    itemID = data.getInt(0);
                }

                if(itemID > -1) {
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent editScreenIntent = new Intent(ListDataActivity.this, EditDataActivity.class);
                    editScreenIntent.putExtra("id", itemID);
                    editScreenIntent.putExtra("name", name);
                    startActivity(editScreenIntent);
                }
                    else {
                    toastMessage("No ID associated with that name");
                }
            }
        });
    }


    @Override
    //Back button always goes to main screen(to avoid going to edit screen)
    public void onBackPressed()
    {
        Intent intent = new Intent(ListDataActivity.this, MainActivity.class);
        startActivity(intent);
    }


    private void toastMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
