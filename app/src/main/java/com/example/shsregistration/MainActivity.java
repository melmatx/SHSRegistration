package com.example.shsregistration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    public static final String myPREFERENCES = "UserPrefs";
    public static final String PREF_selected_color = "selectedColor";
    public static final String PREF_checked_item = "checkedItem";
    ActionBar actionBar;

    SharedPreferences sp;

    String selectedColor, lastColor;
    int checkedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();

        sp = getSharedPreferences(myPREFERENCES, MODE_PRIVATE);

        String selected_color = sp.getString(PREF_selected_color, "#018786");

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(selected_color));
        actionBar.setBackgroundDrawable(colorDrawable);

        lastColor = selected_color;
        selectedColor = lastColor;

        checkedItem = sp.getInt(PREF_checked_item, 0);
    }

    public void registerActivity(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void viewAllActivity(View view){
        Intent intent = new Intent(this, ViewAllActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_actionbar_color:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Pick action bar color");
                builder.setSingleChoiceItems(R.array.actionbar_colors, checkedItem, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            selectedColor = "#018786";
                            checkedItem = 0;
                            break;
                        case 1:
                            selectedColor = "#6200EE";
                            checkedItem = 1;
                            break;
                        case 2:
                            selectedColor = "#FF0000";
                            checkedItem = 2;
                            break;
                        case 3:
                            selectedColor = "#0000FF";
                            checkedItem = 3;
                            break;
                        case 4:
                            selectedColor = "#FFFF00";
                            checkedItem = 4;
                            break;
                    }
                });

                builder.setPositiveButton("OK", (dialog, which) -> {
                    if(!lastColor.equals(selectedColor)) {
                        sp = getSharedPreferences(myPREFERENCES, MODE_PRIVATE);

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString(PREF_selected_color, selectedColor);
                        editor.putInt(PREF_checked_item, checkedItem);
                        editor.commit();

                        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(selectedColor));
                        actionBar.setBackgroundDrawable(colorDrawable);

                        lastColor = selectedColor;
                    }
                });
                builder.setNegativeButton("Cancel", null);

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            case R.id.reset_data:
                try {
                    FileOutputStream fos = openFileOutput(RequirementsActivity.FILE_NAME, MODE_PRIVATE);
                    fos.close();

                    SharedPreferences.Editor editor = sp.edit();
                    editor.clear();
                    editor.commit();

                    finish();
                    startActivity(getIntent());

                    Toast.makeText(this, "Reset Data Successful!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.about:
                Toast.makeText(this, "This app was created by Mel Mathew Palana and Sted Reserva.", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
