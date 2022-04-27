package com.example.notification_manager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.notification_manager.databinding.ActivityScrollingBinding;

import java.util.Set;

public class ScrollingActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private static TextView notificationView;
    private static String string;
    private ActivityScrollingBinding binding;

    private static final String DataBaseName = "DataBaseIt";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Users";
    private static SQLiteDatabase db;
    private SqlDataBaseHelper sqlDataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationView = (TextView) findViewById(R.id.notificationTextView);

        binding = ActivityScrollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NotificationDisplayActivity.class);
                startActivity(intent);
            }
        });

        if(!isPurview(this)){ // 檢查權限是否開啟，未開啟則開啟對話框
            new AlertDialog.Builder(ScrollingActivity.this)
                    .setTitle(R.string.app_name)
                    .setMessage("請啟用通知欄擷取權限")
                    .setIcon(R.mipmap.ic_launcher_round)
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) { // 對話框取消事件
                            finish();
                        }})
                    .setPositiveButton("前往", new DialogInterface.OnClickListener() { // 對話框按鈕事件
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 跳轉自開啟權限畫面，權限開啟後通知欄擷取服務將自動啟動。
                            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                        }}
                    ).show();
        }

        sqlDataBaseHelper = new SqlDataBaseHelper(this,DataBaseName,null,DataBaseVersion,DataBaseTable);
        db = sqlDataBaseHelper.getWritableDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    static void show(String packageName, String text){

        string = text;


        new Thread(new Runnable(){
            public void run(){
                Message msg = Message.obtain();
                handler.sendMessage(msg);}
        }).start();
    }

    private static Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                //將資料顯示，更新至畫面
                notificationView.setText(string);
            }catch (Exception e){}
        }
    };
    private boolean isPurview(Context context) { // 檢查權限是否開啟 true = 開啟 ，false = 未開啟
        Set<String> packageNames = NotificationManagerCompat.getEnabledListenerPackages(context);
        if (packageNames.contains(context.getPackageName())) {
            return true;
        }
        return false;
    }

    public static void insertNotification(String packageName, String title, String text, long timestamp){
        long id;
        ContentValues contentValues = new ContentValues();
        contentValues.put("packageName",packageName);
        contentValues.put("title",title);
        contentValues.put("text",text);
        contentValues.put("timestamp",timestamp);
        id = db.insert(DataBaseTable,null,contentValues);

    }
}























