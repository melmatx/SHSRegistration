package com.example.shsregistration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;

public class ViewAllActivity extends AppCompatActivity {

    LinearLayout layout_container;
    ConstraintLayout empty_container;
    FloatingActionButton fab_register;
    CardView cv;
    ActionBar actionBar;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        layout_container = findViewById(R.id.layout_container);
        empty_container = findViewById(R.id.empty_container);
        fab_register = findViewById(R.id.fab_register);
        actionBar = getSupportActionBar();

        sp = getSharedPreferences(MainActivity.myPREFERENCES, MODE_PRIVATE);

        String selected_color = sp.getString(MainActivity.PREF_selected_color, "#018786");

        if(!selected_color.equals("")) {
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(selected_color));
            actionBar.setBackgroundDrawable(colorDrawable);
        }

        int registration_count = sp.getInt(RequirementsActivity.PREF_registration_count, 0);

        if(registration_count != 0) {
            empty_container.setVisibility(View.GONE);

            try {
                FileInputStream fis = openFileInput(RequirementsActivity.FILE_NAME);
                int ctr;
                StringBuffer buffer = new StringBuffer();

                while ((ctr = fis.read()) != -1) {
                    buffer = buffer.append((char)ctr);
                }

                String[] lines = buffer.toString().split("/\n");

                int num = 1;
                for (String line : lines) {
                    String[] info = line.split("-");

                    CardView.LayoutParams layoutParams = new CardView.LayoutParams(CardView.LayoutParams.WRAP_CONTENT, CardView.LayoutParams.WRAP_CONTENT);
                    cv = new CardView(this);

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(30, 30, 30, 30);

                    LinearLayout ll = new LinearLayout(this);
                    ll.setOrientation(LinearLayout.VERTICAL);
                    ll.setPadding(30, 30, 30,30);
                    cv.setLayoutParams(lp);

                    TextView tv_studentNum = new TextView(this);
                    TextView tv_fullName = new TextView(this);
                    TextView tv_gender = new TextView(this);
                    TextView tv_acadProg = new TextView(this);
                    TextView tv_requirements = new TextView(this);
                    tv_studentNum.setTextAppearance(this, R.style.TextAppearance_AppCompat_Title);
                    tv_studentNum.setText("Student " + num);
                    tv_fullName.setText("Name: " + info[0]);
                    tv_fullName.setPadding(0, 15,0,0);
                    tv_gender.setText("Gender: " + info[1]);
                    tv_acadProg.setText("Academic Program: " + info[2]);
                    tv_acadProg.setPadding(0, 0,0,25);
                    tv_requirements.setTextAppearance(this, R.style.TextAppearance_AppCompat_Body2);
                    tv_requirements.setText("Requirements: ");
                    tv_requirements.setPadding(0, 0,0,5);

                    CheckBox cb_req1 = new CheckBox(this);
                    CheckBox cb_req2 = new CheckBox(this);
                    CheckBox cb_req3 = new CheckBox(this);
                    CheckBox cb_req4 = new CheckBox(this);

                    cb_req1.setText(getResources().getString(R.string.form138));
                    cb_req2.setText(getResources().getString(R.string.nso));
                    cb_req3.setText(getResources().getString(R.string.gmo));
                    cb_req4.setText(getResources().getString(R.string.jhs));

                    cb_req1.setChecked(Boolean.parseBoolean(info[3]));
                    cb_req2.setChecked(Boolean.parseBoolean(info[4]));
                    cb_req3.setChecked(Boolean.parseBoolean(info[5]));
                    cb_req4.setChecked(Boolean.parseBoolean(info[6]));

                    cb_req1.setClickable(false);
                    cb_req2.setClickable(false);
                    cb_req3.setClickable(false);
                    cb_req4.setClickable(false);

                    ll.addView(tv_studentNum);
                    ll.addView(tv_fullName);
                    ll.addView(tv_gender);
                    ll.addView(tv_acadProg);
                    ll.addView(tv_requirements);
                    ll.addView(cb_req1);
                    ll.addView(cb_req2);
                    ll.addView(cb_req3);
                    ll.addView(cb_req4);
                    cv.addView(ll);
                    layout_container.addView(cv);

                    num++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        fab_register.setOnClickListener(v -> {
            Intent intent = new Intent(ViewAllActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}