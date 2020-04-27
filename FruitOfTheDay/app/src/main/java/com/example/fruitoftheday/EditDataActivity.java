package com.example.fruitoftheday;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fruitoftheday.Helper.DatabaseHelper;

public class EditDataActivity extends AppCompatActivity {

    private final static String TAG = "EditDataActivity";

    private Button btnSave, btnDelete;
    private EditText editable_item;

    DatabaseHelper mDatabaseHelper;

    private int selectedID;
    private String selectedName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_layout);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        editable_item = (EditText) findViewById(R.id.editable_item);
        mDatabaseHelper = new DatabaseHelper(this);

        //get the intent extra from the listdataactivity
        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id", -1); //NOTE: -1 is just default value in case the id provided does not exist
        selectedName = receivedIntent.getStringExtra("name");

        editable_item.setText(selectedName);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = editable_item.getText().toString();
                if(!item.equals(""))
                {
                    mDatabaseHelper.updateName(item, selectedID, selectedName);
                    toastMessage("Updated name to " + item + ".");
                    Intent intent = new Intent(EditDataActivity.this, ListDataActivity.class);
                    startActivity(intent);
                }
                else
                {
                    toastMessage("You must enter a name");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseHelper.deleteName(selectedID, selectedName);
                editable_item.setText("");
                toastMessage("Removed from database.");
                Intent intent = new Intent(EditDataActivity.this, ListDataActivity.class);
                startActivity(intent);
            }
        });

    }

    private void toastMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
