package com.example.jackchu.lab10;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Message;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
{
    private EditText editText_input;
    private TextView textView_input;
    private TextView textView_state;

    private Button button_input;
    private Button button_next;

    SharedPreferences preference;
    SharedPreferences.Editor preferences_editor;
    String test_string = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_input = (EditText) findViewById(R.id.editText_input);

        textView_input = (TextView) findViewById(R.id.textView_input);

        textView_state = (TextView) findViewById(R.id.textView_state);

        button_input = (Button) findViewById(R.id.button_input);
        button_input.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    String result = editText_input.getEditableText().toString();
//                    Log.i("Kael", result);
                    textView_input.setText("Display shared preference message here...");
//                    SharedPreferences settings = getPreferences(0);
//                    String string = settings.getString("mystring", result);

                    preference = getPreferences(0);
                    test_string = preference.getString("test_key", "Default");
//                    Toast.makeText(MainActivity.this, test_string, Toast.LENGTH_SHORT).show();

                    preferences_editor = preference.edit();
                    preferences_editor.putString("test_key", result);
                    preferences_editor.commit();
//                    test_string = preference.getString("test_key", "Default");
                    textView_input.setText(test_string);
//                    Toast.makeText(MainActivity.this, test_string, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        button_next = (Button) findViewById(R.id.button_next);
        button_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);

            }
        });

        if (isExternalStorageWritable())
        {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            long bytesAvailable = (long) stat.getBlockSize() * (long) stat.getBlockCount();
            double megAvailable = bytesAvailable / 1024 / 1024 / 1024;

            textView_state.setText("external storages avilable: " + megAvailable + "GB");

        }
        else
        {
            textView_state.setText("external storages not avilable");
        }
    }

    public boolean isExternalStorageWritable()
    {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state))
        {
            return true;
        }
        return false;
    }
}
