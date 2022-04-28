package com.example.notification_manager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notification_manager.databinding.ActivityNotificationDisplayBinding;

import java.util.LinkedList;

public class NotificationDisplayActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityNotificationDisplayBinding binding;
    private final LinkedList<String> packageNameList = new LinkedList<>();
    private final LinkedList<String> titleList = new LinkedList<>();
    private final LinkedList<String> textList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;

    private static final String DataBaseName = "DataBaseIt";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Users";
    private static SQLiteDatabase db;
    private SqlDataBaseHelper sqlDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNotificationDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_notification_display);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        sqlDataBaseHelper = new SqlDataBaseHelper(this,DataBaseName,null,DataBaseVersion,DataBaseTable);
        db = sqlDataBaseHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseTable,null);
        c.moveToFirst();

        for(int i=0;i<c.getCount();i++){
            packageNameList.addLast(c.getString(1));
            titleList.addLast(c.getString(2));
            textList.addLast(c.getString(3));
            c.moveToNext();
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new WordListAdapter(this, packageNameList, titleList, textList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_notification_display);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}