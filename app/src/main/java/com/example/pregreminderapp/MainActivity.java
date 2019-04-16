package com.example.pregreminderapp;

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pregreminderapp.data.AlarmReminderContract;
import com.example.pregreminderapp.data.AlarmReminderDbHelper;
import com.getbase.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private FloatingActionButton mAddReminderButton;
    private Toolbar mToolBar;
    AlarmCursorAdapter alarmCursorAdapter;
    AlarmReminderDbHelper alarmReminderDbHelper;
    ListView reminderListView;
    ProgressDialog prgDialog;

    private static  final int VEHICLE_LOADER = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolBar =(android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setTitle("Alarm Reminder");

        reminderListView = (ListView)findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        reminderListView.setEmptyView(emptyView);

        reminderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this , AddReminderActivity.class);
                Uri currentVehicleUri = ContentUris.withAppendedId(AlarmReminderContract.AlarmReminderEntry.CONTENT_URI , id);

                intent.setData(currentVehicleUri);
                startActivity(intent);
            }
        });

        mAddReminderButton = (FloatingActionButton)findViewById(R.id.fab);

        mAddReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext() , AddReminderActivity.class);
                startActivity(intent);
            }

        });
          getLoaderManager().initLoader(VEHICLE_LOADER , null , this);



    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
       String[] projection = {

               AlarmReminderContract.AlarmReminderEntry._ID,
               AlarmReminderContract.AlarmReminderEntry.KEY_TITLE,
               AlarmReminderContract.AlarmReminderEntry.KEY_DATE,
               AlarmReminderContract.AlarmReminderEntry.KEY_TIME,
               AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT,
               AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_NO,
               AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_TYPE,
               AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE
       };

       return  new CursorLoader(this,
               AlarmReminderContract.AlarmReminderEntry.CONTENT_URI,
               projection,
               null,
               null,
               null

       );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
     alarmCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        alarmCursorAdapter.swapCursor(null);


    }
}
