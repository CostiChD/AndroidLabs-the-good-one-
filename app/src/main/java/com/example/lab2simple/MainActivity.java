package com.example.lab2simple;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.lab2simple.MESSAGE";

    String description = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView productsList = (ListView) findViewById(R.id.productsList);
        final TextView shoesDescription = (TextView) findViewById(R.id.shoesDescription);

        String[] shoesNames = new String[]{
                "Superstar 2020",
                "Ultraboost Original",
                "Ultraboost 19",
                "Ultraboost 20"
        };

        ArrayList<String> products = new ArrayList<>(Arrays.asList(shoesNames));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, products);

        productsList.setAdapter(arrayAdapter);
        productsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedTitle = (String) productsList.getItemAtPosition(position);
                String shoesName = "";
                switch (selectedTitle) {
                    case "Superstar 2020":
                        shoesName = "Superstar 2020";
                        break;
                    case "Ultraboost Original":
                        shoesName = "Ultraboost Original";
                        break;
                    case "Ultraboost 19":
                        shoesName = "Ultraboost 19";
                        break;
                    case "Ultraboost 20":
                        shoesName = "Ultraboost 20";
                        break;
                    default:
                        break;
                }
                shoesDescription.setText("Imi place " + shoesName);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        loadLocalSettings();
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
        saveLocalSettings();
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

        TextView shoesDescription = (TextView) findViewById(R.id.shoesDescription);
        description = shoesDescription.getText().toString();
        onSaveState.putString("shoesDescription", description);
        super.onSaveInstanceState(onSaveState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle onRestoreState) {
        super.onRestoreInstanceState(onRestoreState);
        TextView shoesDescription = (TextView) findViewById(R.id.shoesDescription);
        description = onRestoreState.getString("shoesDescription");
        shoesDescription.setText(description);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    /*
    Lab4, primul ex si ultimul ex
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        AlertDialog builder = new AlertDialog.Builder(this).create();

        if (menuItem.getItemId() == R.id.about) {
            builder.setTitle("About");
            builder.setMessage("This is about le papuc");
        } else if (menuItem.getItemId() == R.id.help) {
            builder.setTitle("Help");
            builder.setMessage("What do you need help for, bro?");
        } else if (menuItem.getItemId() == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (menuItem.getItemId() == R.id.sensors) {
            Intent intent = new Intent(this, SensorsDisplayActivity.class);
            startActivity(intent);
            return true;
        } else if (menuItem.getItemId() == R.id.location) {
            Intent intent = new Intent(this, GPSCoordinatesActivity.class);
            startActivity(intent);
            return true;
        } else if (menuItem.getItemId() == R.id.camera) {
            Intent intent = new Intent(this, CameraActivity.class);
            startActivity(intent);
            return true;
        } else {
            super.onOptionsItemSelected(menuItem);
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

    public void sendMessage(View view) {
        final TextView shoesDescription = (TextView) findViewById(R.id.shoesDescription);

        Intent intent = new Intent(this, DisplayMessageActivity.class);

        intent.putExtra(EXTRA_MESSAGE, shoesDescription.getText().toString());
        startActivity(intent);
    }

    private void loadLocalSettings() {
        String textToLoad = getLocalSettings();
        TextView textView = (TextView) findViewById(R.id.shoesDescription);
        textView.setText(String.format("%s(from local storage)", textToLoad));
    }

    private void saveLocalSettings() {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput(
                    "localSettings.txt", MODE_PRIVATE));
            String text = ((TextView) findViewById(R.id.shoesDescription)).getText().toString();
            outputStreamWriter.write(text);
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getLocalSettings() {
        FileInputStream fis = null;
        try {
            File localSettingsFile = new File("localSettings.txt");
            if (!localSettingsFile.exists()) {
                saveLocalSettings();
            }

            fis = openFileInput("localSettings.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();

            return "";
        }
    }
}