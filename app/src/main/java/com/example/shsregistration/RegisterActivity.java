package com.example.shsregistration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String PREF_last_name = "lastName";
    public static final String PREF_first_name = "firstName";
    public static final String PREF_middle_name = "middleName";
    public static final String PREF_gender = "gender";
    public static final String PREF_acad_prog = "acadProg";

    EditText et_lastName, et_firstName, et_middleName;
    RadioButton rb_male, rb_female;
    Spinner spinner;
    ActionBar actionBar;

    SharedPreferences sp;

    String gender = "";
    String acad_prog = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_lastName = findViewById(R.id.et_lastName);
        et_firstName = findViewById(R.id.et_firstName);
        et_middleName = findViewById(R.id.et_middleName);
        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);
        spinner = findViewById(R.id.spn_acadProg);
        actionBar = getSupportActionBar();

        sp = getSharedPreferences(MainActivity.myPREFERENCES, MODE_PRIVATE);

        String selected_color = sp.getString(MainActivity.PREF_selected_color, "#018786");

        if(!selected_color.equals("")) {
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(selected_color));
            actionBar.setBackgroundDrawable(colorDrawable);
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.academic_programs, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String[] acad_prog_array = getResources().getStringArray(R.array.academic_programs);

        switch (position) {
            case 0:
                acad_prog = acad_prog_array[0];
                break;
            case 1:
                acad_prog = acad_prog_array[1];
                break;
            case 2:
                acad_prog = acad_prog_array[2];
                break;
            case 3:
                acad_prog = acad_prog_array[3];
                break;
            case 4:
                acad_prog = acad_prog_array[4];
                break;
            case 5:
                acad_prog = acad_prog_array[5];
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        int id = view.getId();

        if (id == R.id.rb_male) {
            if (checked)
                gender = "Male";
        }
        else if (id == R.id.rb_female) {
            if (checked)
                gender = "Female";
        }
    }

    public void requirementsActivity(View view) {
        String last_name = et_lastName.getText().toString();
        String first_name = et_firstName.getText().toString();
        String middle_name = et_middleName.getText().toString();

        if (last_name.equals("")) {
            Toast.makeText(this, "Enter a last name!", Toast.LENGTH_SHORT).show();
            et_lastName.requestFocus();
        }
        else if (first_name.equals("")) {
            Toast.makeText(this, "Enter a first name!", Toast.LENGTH_SHORT).show();
            et_firstName.requestFocus();
        }
        else if (gender.equals("")) {
            Toast.makeText(this, "Please select gender!", Toast.LENGTH_SHORT).show();
        }
        else if (acad_prog.equals("")) {
            Toast.makeText(this, "Please select academic program!", Toast.LENGTH_SHORT).show();
        }
        else {
            sp = getSharedPreferences(MainActivity.myPREFERENCES, MODE_PRIVATE);

            SharedPreferences.Editor editor = sp.edit();

            editor.putString(PREF_last_name, last_name);
            editor.putString(PREF_first_name, first_name);
            editor.putString(PREF_middle_name, middle_name);
            editor.putString(PREF_gender, gender);
            editor.putString(PREF_acad_prog, acad_prog);

            editor.commit();

            Intent intent = new Intent(this, RequirementsActivity.class);
            startActivity(intent);
        }
    }
}
