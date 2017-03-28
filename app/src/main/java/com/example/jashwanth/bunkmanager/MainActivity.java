package com.example.jashwanth.bunkmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutionException;

class MyObject{
    private int number;
    private String name;


    MyObject(int num, String nam){
        number = num;
        name = nam;
    }

    public int getNumber(){
        return number;
    }

    public String getName(){
        return name;
    }
}

class myadapter extends BaseAdapter {
    private ArrayList<String> array1, array2, array3;
    int[] attendance,total;
    private LayoutInflater inflater;
    SharedPreferences sharedpreferences;

    static Stack<Integer> history = new Stack<>();
    static Stack<Integer> historypos = new Stack<>();

    public myadapter(Context context, ArrayList<String> a1, ArrayList<String> a2, ArrayList<String> a3, int[] attendance, int[] total, SharedPreferences sp) {
        array1 = a1;
        array2 = a2;
        array3 = a3;
        this.attendance = attendance;
        this.total = total;
        sharedpreferences = sp;
        inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return array1.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View currentView = view;

        if(currentView==null) {
            currentView = inflater.inflate(R.layout.listview, viewGroup, false);
        }

        TextView tView = (TextView)currentView.findViewById(R.id.textView1);
        tView.setText(array1.get(position));

        tView = (TextView)currentView.findViewById(R.id.textView2);
        tView.setText(array2.get(position));

        tView = (TextView)currentView.findViewById(R.id.textView3);
        tView.setText(array3.get(position));

        Button button = (Button) currentView.findViewById(R.id.attendButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attendance[position]++;
                total[position]++;
                array2.set(position,"Attended classes : "+Integer.toString(attendance[position]) + "/" + Integer.toString(total[position]));
                array3.set(position,"Percentage "+ Float.toString((attendance[position]* 100) / total[position])+" %");
                notifyDataSetChanged();
                history.push(1);
                historypos.push(position);
                try {
                    sharedpreferences.edit().putString("array2",ObjectSerializer.serialize(array2)).apply();
                    sharedpreferences.edit().putString("array3",ObjectSerializer.serialize(array3)).apply();
                    sharedpreferences.edit().putString("attendance",ObjectSerializer.serialize(attendance)).apply();
                    sharedpreferences.edit().putString("total",ObjectSerializer.serialize(total)).apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        button = (Button) currentView.findViewById(R.id.skipButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total[position]++;
                array2.set(position,"Attended classes : "+Integer.toString(attendance[position]) + "/" + Integer.toString(total[position]));
                array3.set(position,"Percentage "+ Float.toString((attendance[position]* 100) / total[position])+" %");
                notifyDataSetChanged();
                history.push(-1);
                historypos.push(position);
                try {
                    sharedpreferences.edit().putString("array2",ObjectSerializer.serialize(array2)).apply();
                    sharedpreferences.edit().putString("array3",ObjectSerializer.serialize(array3)).apply();
                    sharedpreferences.edit().putString("attendance",ObjectSerializer.serialize(attendance)).apply();
                    sharedpreferences.edit().putString("total",ObjectSerializer.serialize(total)).apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

            button = (Button) currentView.findViewById(R.id.undoButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (history.empty() != true && historypos.peek() == position) {
                        int temp = history.peek();
                        int temppos = historypos.peek();
                        history.pop();
                        historypos.pop();
                        if (temp == 1) {
                            attendance[temppos]--;
                            total[temppos]--;
                        } else {
                            total[temppos]--;
                        }
                        if(total[position] != 0) {
                            array2.set(position, "Attended classes : " + Integer.toString(attendance[position]) + "/" + Integer.toString(total[position]));
                            array3.set(position, "Percentage " + Float.toString((attendance[position] * 100) / total[position]) + " %");
                            notifyDataSetChanged();
                            try {
                                sharedpreferences.edit().putString("array2", ObjectSerializer.serialize(array2)).apply();
                                sharedpreferences.edit().putString("array3", ObjectSerializer.serialize(array3)).apply();
                                sharedpreferences.edit().putString("attendance", ObjectSerializer.serialize(attendance)).apply();
                                sharedpreferences.edit().putString("total", ObjectSerializer.serialize(total)).apply();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            attendance[position] = 0;
                            array2.set(position, "Attended classes : " + Integer.toString(attendance[position]) + "/" + Integer.toString(total[position]));
                            array3.set(position, "Percentage " + 0 + " %");
                            notifyDataSetChanged();
                            try {
                                sharedpreferences.edit().putString("array2", ObjectSerializer.serialize(array2)).apply();
                                sharedpreferences.edit().putString("array3", ObjectSerializer.serialize(array3)).apply();
                                sharedpreferences.edit().putString("attendance", ObjectSerializer.serialize(attendance)).apply();
                                sharedpreferences.edit().putString("total", ObjectSerializer.serialize(total)).apply();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            });
        return currentView;
    }
}


public class MainActivity extends AppCompatActivity {

    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> classes = new ArrayList<>();
    ArrayList<String> perc = new ArrayList<>();
    int[] attendance = new int[6];
    int[] total = new int[6];

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.timetable){
            Intent intent = new Intent(getApplicationContext(),timetable.class);
            startActivity(intent);

        }else if(item.getItemId() == R.id.datepicker){
            Intent intent = new Intent(getApplicationContext(),datepicker.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.jashwanth.attendancemanager",Context.MODE_PRIVATE);
        try {
            classes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("array2",ObjectSerializer.serialize(new ArrayList<>())));
            perc = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("array3",ObjectSerializer.serialize(new ArrayList<>())));
            attendance = (int[]) ObjectSerializer.deserialize(sharedPreferences.getString("attendance",ObjectSerializer.serialize(new int[6])));
            total = (int[]) ObjectSerializer.deserialize(sharedPreferences.getString("total",ObjectSerializer.serialize(new int[6])));
            Log.i("Array3",perc.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ListView listView = (ListView) findViewById(R.id.ListView);
        names.add("Networks");
        names.add("Microwave");
        names.add("OS");
        names.add("Vlsi");

        if(classes.size() == 0) {
            classes.add("Attended classes : " + Integer.toString(attendance[0]));
            classes.add("Attended classes : " + Integer.toString(attendance[1]));
            classes.add("Attended classes : " + Integer.toString(attendance[2]));
            classes.add("Attended classes : " + Integer.toString(attendance[3]));
        }

        if(perc.size() == 0) {
            perc.add("Percentage : 0 %");
            perc.add("Percentage : 0 %");
            perc.add("Percentage : 0 %");
            perc.add("Percentage : 0 %");
        }

        final myadapter adp = new myadapter(this, names, classes, perc,attendance,total,sharedPreferences);
        listView.setAdapter(adp);

    }
}


