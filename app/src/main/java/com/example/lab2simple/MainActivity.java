package com.example.lab2simple;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    String description = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView productsList = (ListView) findViewById(R.id.productsList);
        final TextView lePapucDescription = (TextView) findViewById(R.id.lePapucDescription);

        String[] bookTitles = new String[]{
                "Superstar 2020",
                "Ultraboost Original",
                "Ultraboost 19",
                "Ultraboost 20"
        };

        ArrayList<String> products = new ArrayList<>(Arrays.asList(bookTitles));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, products);

        productsList.setAdapter(arrayAdapter);
        productsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedTitle = (String) productsList.getItemAtPosition(position);
                String lePapucName = "";
                switch (selectedTitle) {
                    case "Superstar 2020":
                        lePapucName = "Superstar 2020";
                        break;
                    case "Ultraboost Original":
                        lePapucName = "Ultraboost Original";
                        break;
                    case "Ultraboost 19":
                        lePapucName = "Ultraboost 19";
                        break;
                    case "Ultraboost 20":
                        lePapucName = "Ultraboost 20";
                        break;
                    default:
                        break;
                }
                lePapucDescription.setText("Imi place " + lePapucName);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifecycle", "onStart invoked");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle", "onResume invoked");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle", "onPause invoked");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifecycle", "onStop invoked");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle", "onDestroy invoked");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("lifecycle", "onRestart invoked");
    }


    @Override
    protected void onSaveInstanceState(Bundle onSaveState) {

        TextView lePapucDescription = (TextView) findViewById(R.id.lePapucDescription);
        description = lePapucDescription.getText().toString();
        onSaveState.putString("lePapucDescription", description);
        super.onSaveInstanceState(onSaveState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle onRestoreState) {
        super.onRestoreInstanceState(onRestoreState);
        TextView lePapucDescription = (TextView) findViewById(R.id.lePapucDescription);
        description = onRestoreState.getString("lePapucDescription");
        lePapucDescription.setText(description);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        AlertDialog builder = new AlertDialog.Builder(this).create();

        if (menuItem.getItemId() == R.id.About) {
            builder.setTitle("About");
            builder.setMessage("This is about le papuc");
        } else if (menuItem.getItemId() == R.id.help) {
            builder.setTitle("Help");
            builder.setMessage("What do you need help for, bro?");
        }

        builder.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.show();

        return true;
    }
}