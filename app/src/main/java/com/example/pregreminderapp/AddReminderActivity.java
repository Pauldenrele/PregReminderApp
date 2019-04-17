package com.example.pregreminderapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toolbar;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class AddReminderActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener , DatePickerDialog.OnDateSetListener
, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_VEHICLE_LOADER = 0;

    private Toolbar mToolBar ;
    private EditText mTitleText;
    private TextView mDateText , mTimeText , mRepeatText , mRepeatNoText , mRepeatTypeText;
    private FloatingActionButton mFab1;
    private FloatingActionButton mFab2;
    private Calendar mCalender;
    private int mYear , mMonth , mDay ,mHour , mMinute;
    private long mRepaeatTime;
    private Switch mRepeatSwitch;
    private String mTitle;
    private String mTime;
    private String mRepeat;
    private String mRepeatNo;
    private String mDate;
    private String mRepeatType;
    private String mActive;

    private Uri mCurrentReminderUri;
    private Boolean mVehiclehasChanged =false;

    private static final String KEY_TITLE ="title_key";
    private static final String KEY_DATE = "date_key";
    private static final String KEY_TIME ="time_key";
    private static final String KEY_REPEAT_NO= "repeat_no_key";
    private static final String KEY_REPEAT_TYPE ="repeat_type_key";
    private static final String KEY_ACTIVE = "active_key";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder2);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
