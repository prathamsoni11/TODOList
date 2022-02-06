package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PrathamAdapter extends ArrayAdapter<String> {
    private ArrayList<String> item;

    public PrathamAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> item) {
        super(context, resource,item);
        this.item = item;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return item.get(position);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_layout,parent,false);
        CheckBox cb = convertView.findViewById(R.id.checkBox);
        cb.setText(getItem(position));

        SharedPreferences sp = getContext().getSharedPreferences("MyPref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        Button delete = convertView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rev = item.remove(position);
                editor.remove(rev);

                Set<String> set = new HashSet<>(item);
                editor.putStringSet("list",set);
                editor.apply();
                notifyDataSetChanged();
            }
        });

        Boolean bet = sp.getBoolean(item.get(position),false);
        cb.setChecked(bet);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cb.isChecked()) {
                    editor.putBoolean(item.get(position), true);
                }else {
                    editor.putBoolean(item.get(position),false);
                }
                editor.apply();
            }
        });

        return convertView;
    }

}
