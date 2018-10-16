package com.weilaiweather.android;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        button = (Button) findViewById(R.id.btn_test);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setTitle("test");
//        alert.setMessage("哈哈哈哈哈都是");
//        alert.setCancelable(false);
//        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        alert.setNegativeButton("colse", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        alert.show();
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("test");
        dialog.setMessage("132123123");
        dialog.setCancelable(true);
        dialog.show();

    }
}
