package com.example.fruitoftheday;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.fruitoftheday.Helper.DatabaseHelper;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    DatabaseHelper mDatabaseHelper;
    private Button btnAdd;
    private Button btnViewData;
    private Button btnGetFruit;
    private TextView theFruitTextView;
    private EditText editText;

    private ArrayList<String> fruits;


    public void getYourFruit() {
        ExampleDialog exampleDialog = new ExampleDialog(fruits);
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        btnGetFruit = (Button) findViewById(R.id.btnGetFruit);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnViewData = (Button) findViewById(R.id.btnView);
        theFruitTextView = (TextView) findViewById(R.id.thefruit_view);
        mDatabaseHelper = new DatabaseHelper(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntryName = editText.getText().toString();
                int newEntryLikePercentage = 10; // TODO: To be replaced with getting input
                if(newEntryName.length() != 0)
                {
                    addData(newEntryName, newEntryLikePercentage);
                    editText.setText("");
                }else
                {
                    toastMessage("You must put a fruit name in the text field!");
                }
            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                startActivity(intent);
        }
        });

        btnGetFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = mDatabaseHelper.getData();
                ArrayList<String> allFruits = new ArrayList<>();
                //TODO: HashMap<String, Integer> listData = new HashMap<>();
                while(data.moveToNext())
                {
                    // get value from column 1(in database helper named column 2) and add to array
                    allFruits.add(data.getString(1)); //TODO: get likepercentage too
                }

                Random random = new Random();
                int randNumber = random.nextInt(allFruits.size());
                String selectedFruit = allFruits.get(randNumber);
                Log.d(TAG, selectedFruit, null);
                theFruitTextView.setText(selectedFruit);
                int fruitRelatedColor = computeColor(selectedFruit);
                theFruitTextView.setBackgroundColor(fruitRelatedColor);
            }
        });

    }

    private int computeColor(String fruit)
    {
        if(fruit.equals("Banana"))
            return Color.YELLOW;
        if(fruit.equals("Orange"))
            return Color.rgb(255, 165, 0);;
        if(fruit.equals("Pear"))
            return Color.rgb(0,127,127);
        if(fruit.equals("Apple"))
            return Color.rgb(0,255,0);
        return Color.WHITE;
    }

    private void toastMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void addData(String newEntryName, int newEntryLikePercentage)
    {
        boolean insertData = mDatabaseHelper.addData(newEntryName, newEntryLikePercentage);
        if(insertData)
            toastMessage("Data succesfully added.");
        else
            toastMessage("Something went wrong.");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
