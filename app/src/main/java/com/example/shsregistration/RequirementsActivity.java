package com.example.shsregistration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;

public class RequirementsActivity extends AppCompatActivity {

    public static final String FILE_NAME = "Records.txt";
    public static final String PREF_registration_count = "registrationCount";
    public static int regCount;

    TextView tv_saved_name, tv_saved_gender, tv_saved_acadProg;
    Button btn_submit;
    ActionBar actionBar;
    CheckBox cb_form138, cb_nso, cb_gmo, cb_jhs;

    SharedPreferences sp;

    String fullName, saved_lastName, saved_firstName, saved_middleName, saved_gender, saved_acadProg;
    String[] requirements = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirements);

        tv_saved_name = findViewById(R.id.tv_saved_name);
        tv_saved_gender = findViewById(R.id.tv_saved_gender);
        tv_saved_acadProg = findViewById(R.id.tv_saved_acadProg);
        cb_form138 = findViewById(R.id.cb_form138);
        cb_nso = findViewById(R.id.cb_nso);
        cb_gmo = findViewById(R.id.cb_gmo);
        cb_jhs = findViewById(R.id.cb_jhs);
        btn_submit = findViewById(R.id.btn_submit);
        actionBar = getSupportActionBar();

        sp = getSharedPreferences(MainActivity.myPREFERENCES, MODE_PRIVATE);

        String selected_color = sp.getString(MainActivity.PREF_selected_color, "#018786");

        if(!selected_color.equals("")) {
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(selected_color));
            actionBar.setBackgroundDrawable(colorDrawable);
        }

        saved_lastName = sp.getString(RegisterActivity.PREF_last_name,"");
        saved_firstName = sp.getString(RegisterActivity.PREF_first_name, "");
        saved_middleName = sp.getString(RegisterActivity.PREF_middle_name, "");
        saved_gender = sp.getString(RegisterActivity.PREF_gender, "");
        saved_acadProg = sp.getString(RegisterActivity.PREF_acad_prog, "");

        fullName = saved_lastName + ", " + saved_firstName + " " + saved_middleName;

        tv_saved_name.setText(fullName);
        tv_saved_gender.setText(saved_gender);
        tv_saved_acadProg.setText(saved_acadProg);
    }

    public void submit (View view) {
        if(cb_form138.isChecked()) {
            requirements[0] = "true";
        }
        else {
            requirements[0] = "false";
        }

        if(cb_nso.isChecked()) {
            requirements[1] = "true";
        }
        else {
            requirements[1] = "false";
        }

        if(cb_gmo.isChecked()) {
            requirements[2] = "true";
        }
        else {
            requirements[2] = "false";
        }

        if(cb_jhs.isChecked()) {
            requirements[3] = "true";
        }
        else {
            requirements[3] = "false";
        }

        String data;
        data = fullName + "-" + saved_gender + "-" + saved_acadProg + "-" + requirements[0] + "-" + requirements[1] + "-" + requirements[2] + "-" + requirements[3] + "/\n";
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_APPEND);
            fos.write(data.getBytes());
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        regCount++;

        sp = getSharedPreferences(MainActivity.myPREFERENCES, MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(PREF_registration_count, regCount);
        editor.commit();

        Intent intent = new Intent(this, ViewAllActivity.class);
        startActivity(intent);

        Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
    }
}