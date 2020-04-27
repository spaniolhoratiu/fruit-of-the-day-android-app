package com.example.fruitoftheday;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;
import java.util.Random;

public class ExampleDialog extends AppCompatDialogFragment {

    private ArrayList<String> fruits;
    String chosenFruit;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Your fruit of the day is")
                .setMessage(chosenFruit)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }

    public ExampleDialog(ArrayList<String> fr)
    {
        fruits = fr;
        Random random = new Random();
        int randomNumber = random.nextInt(fruits.size());
        chosenFruit = fruits.get(randomNumber);
    }
}
