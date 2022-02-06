package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> item = new ArrayList<>();

    FloatingActionButton floatingActionButton;
    ListView listView;
    LinearLayout linearLayout;
    Button add;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        linearLayout = findViewById(R.id.linearLayout);
        add = findViewById(R.id.add);
        editText = findViewById(R.id.editText);

        linearLayout.setVisibility(View.GONE);

        PrathamAdapter ad = new PrathamAdapter(this,R.layout.list_layout,item);
        listView.setAdapter(ad);

        SharedPreferences sp = getSharedPreferences("MyPref",MODE_PRIVATE);
        Set<String> set = sp.getStringSet("list",new HashSet<>());
        item.addAll(set);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.VISIBLE);
                floatingActionButton.setVisibility(View.GONE);

                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                if (str.trim().length() != 0) {
                    if (!item.contains(str)) {
                        item.add(str);
                        ad.notifyDataSetChanged();
                    }else {
                        Toast.makeText(MainActivity.this, "Task already exist", Toast.LENGTH_SHORT).show();
                    }
                }
                editText.setText("");

                SharedPreferences.Editor editor = sp.edit();
                Set<String> set = new HashSet<>(item);
                editor.putStringSet("list", set);
                editor.apply();

                linearLayout.setVisibility(View.GONE);
                floatingActionButton.setVisibility(View.VISIBLE);

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(add.getWindowToken(), 0);
            }
        });
    }
}