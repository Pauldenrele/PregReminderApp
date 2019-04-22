package com.example.pregreminderapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.pregreminderapp.data.AlarmReminderContract;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class AddReminderActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener , DatePickerDialog.OnDateSetListener
, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_VEHICLE_LOADER = 0;

    private RelativeLayout relativeLayout;
    private View view;
    private Toolbar mToolBar;
    private EditText mTitleText;
    private TextView mDateText, mTimeText, mRepeatText, mRepeatNoText, mRepeatTypeText;
    private FloatingActionButton mFab1;
    private FloatingActionButton mFab2;
    private Calendar mCalender;
    private int mYear, mMonth, mDay, mHour, mMinute;
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
    private Boolean mVehiclehasChanged = false;

    private static final String KEY_TITLE = "title_key";
    private static final String KEY_DATE = "date_key";
    private static final String KEY_TIME = "time_key";
    private static final String KEY_REPEAT = "repeat_key";
    private static final String KEY_REPEAT_NO = "repeat_no_key";
    private static final String KEY_REPEAT_TYPE = "repeat_type_key";
    private static final String KEY_ACTIVE = "active_key";

    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDays = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonths = 2592000000L;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mVehiclehasChanged = true;
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder2);

        Intent intent = getIntent();
        mCurrentReminderUri = intent.getData();

        if (mCurrentReminderUri == null) {
            setTitle("Add Reminder details");

            invalidateOptionsMenu();
        } else {
            setTitle("Edit Reminder ");
            getLoaderManager().initLoader(EXISTING_VEHICLE_LOADER, null, (android.app.LoaderManager.LoaderCallbacks<Object>) this);

        }
        mToolBar = findViewById(R.id.toolbar);
        mTitleText = findViewById(R.id.reminder_title);
        mDateText = findViewById(R.id.setDate);
        mTimeText = findViewById(R.id.set_time);
        mRepeatText = findViewById(R.id.set_repeat);
        mRepeatTypeText = findViewById(R.id.set_repeat_type);
        mRepeatNoText = findViewById(R.id.set_repeat_no);
        mRepeatSwitch = findViewById(R.id.repeat_switch);
        mFab1 = findViewById(R.id.starred1);
        mFab2 = findViewById(R.id.starred2);

        mActive = "true";
        mRepeat = "true";
        mRepeatNo = Integer.toString(1);
        mRepeatType = "Hour";

        mCalender = Calendar.getInstance();
        mHour = mCalender.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalender.get(Calendar.MINUTE);
        mYear = mCalender.get(Calendar.YEAR);
        mMonth = mCalender.get(Calendar.MONTH) + 1;
        mDay = mCalender.get(Calendar.DATE);

        mDate = mDay + "/" + mMonth + "/" + mYear;
        mTime = mHour + ":" + mMinute;

        mTitleText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTitle = s.toString().trim();
                mTitleText.setError(null);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mDateText.setText(mDate);
        mRepeatTypeText.setText(mRepeatType);
        mRepeatNoText.setText(mRepeatNo);
        mTimeText.setText(mTime);
        mRepeatText.setText("Every" + mRepeatNo + " " + mRepeatType +"(s)");

        if(savedInstanceState !=null){
            String savedTitle = savedInstanceState.getString(KEY_TITLE);
            mTitleText.setText(savedTitle);
            mTitle = savedTitle;

            String savedTime = savedInstanceState.getString(KEY_TIME);
            mTimeText.setText(savedTime);
            mTime = savedTime;

            String saveRepeat = savedInstanceState.getString(KEY_REPEAT);
            mRepeatText.setText(saveRepeat);
            mRepeat = saveRepeat;

            String saveRepeatNo = savedInstanceState.getString(KEY_REPEAT_NO);
            mRepeatNoText.setText(saveRepeatNo);
            mRepeatNo = saveRepeatNo;

            String savedRepeatType= savedInstanceState.getString(KEY_REPEAT_TYPE);
            mRepeatTypeText.setText(savedRepeatType);
            mRepeatType= savedRepeatType;

            String savedDate = savedInstanceState.getString(KEY_DATE);
            mDateText.setText(savedDate);
            mDate = savedDate;

            mActive = savedInstanceState.getString(KEY_ACTIVE);

        }

        if(mActive.equals("false")){
            mFab1.setVisibility(View.VISIBLE);
            mFab2.setVisibility(View.GONE);


        }else if(mActive.equals("true")) {
            mFab1.setVisibility(View.GONE);
            mFab2.setVisibility(View.VISIBLE);
        }
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Add Reminder");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);




    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putCharSequence(KEY_TITLE , mTitleText.getText());
        outState.putCharSequence(KEY_TIME , mTimeText.getText());
        outState.putCharSequence(KEY_DATE , mDateText.getText());
        outState.putCharSequence(KEY_REPEAT , mRepeatText.getText());
        outState.putCharSequence(KEY_REPEAT_NO , mRepeatNoText.getText());
        outState.putCharSequence(KEY_REPEAT_TYPE , mRepeatTypeText.getText());
        outState.putCharSequence(KEY_ACTIVE , mActive);

    }

    public void setTime(View v ){
        Calendar now =Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.time.TimePickerDialog tpd = (com.wdullaer.materialdatetimepicker.time.TimePickerDialog) com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(
                (com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener) this,
               now.get( Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false

        );
        tpd.setThemeDark(false);
        tpd.show(getFragmentManager() , "TimePickerDialog");


    }

    public void setDate(View v){
        Calendar now = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = (com.wdullaer.materialdatetimepicker.date.DatePickerDialog) com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                (com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener) this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager() , "DatePickerDialog");

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month++;
        mDay =dayOfMonth;
        mMonth = month;
        mYear = year;

        mDate = dayOfMonth + "/" + month + "/" + year;
        mDateText.setText(mDate);

    }
    public void selectFab1(View v){
        mFab1 = findViewById(R.id.starred1);
        mFab1.setVisibility(View.GONE);
        mFab2 = findViewById(R.id.starred2);
        mFab2.setVisibility(View.VISIBLE);
        mActive = "true";
    }
    public void selectFab2(View v){
        mFab2 = findViewById(R.id.starred2);
        mFab2.setVisibility(View.GONE);
        mFab1 = findViewById(R.id.starred1);
        mFab1.setVisibility(View.VISIBLE);
        mActive = "false";
    }

    public void onSwitchRepeat(View v){
        boolean on = ((Switch) view ).isChecked();
        if(on){
            mRepeat= "true";
            mRepeatText.setText("Every" + mRepeatNo +" " + mRepeatType + "(s)");

        }else{
            mRepeat = "false";
            mRepeatText.setText("off");

        }
    }

    public void selectRepeatType(View v){
        final String[]  items = new String[5];
        items[0] = "Minute";
        items[1] = "Hour";
        items[2] = "Day";
        items[3] = "Week";
        items[4] = "Month";

        AlertDialog.Builder builder = new AlertDialog.Builder(this );
        builder.setTitle("Select Type ");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mRepeatType = items[which];
                mRepeatTypeText.setText(mRepeatType);
                mRepeatText.setText("Every "+ mRepeatNo + " " +mRepeatType + "(s)");

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
    public void setRepeatNo(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Enter Number");

        final EditText input = new EditText(this );
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);
        alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(input.getText().toString().length() == 0){
                    mRepeatNo = Integer.toString(1);
                    mRepeatNoText.setText(mRepeatNo);
                    mRepeatText.setText("Every" + mRepeatNo + " " + mRepeatType + "(s)");
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

         getMenuInflater().inflate(R.menu.menu_add_reminder , menu);
        return  true;


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
         super.onPrepareOptionsMenu(menu);

        if (mCurrentReminderUri == null){
            MenuItem menuItem = menu.findItem(R.id.discard_reminder);
            menuItem.setVisible(false);


        }
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch(item.getItemId()){
            case R.id.save_reminder:
                if(mTitleText.getText().toString().length() ==0){
                    mTitleText.setError("Reminder Title cannot be blank");

                }else{
                    saveReminder();
                    finish();
                }
                return true;

            case R.id.discard_reminder:
                showdeleteConfirmationdialog();
                return true;

            case android.R.id.home:
                if(!mVehiclehasChanged){
                    NavUtils.navigateUpFromSameTask(AddReminderActivity.this);
                    return  true;
                }

                DialogInterface.OnClickListener discardButtonClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                          NavUtils.navigateUpFromSameTask(AddReminderActivity.this);
                    }
                };
                showUnavedChangesDialog(discardButtonClickListener);
                return true;

        }
        return super.onOptionsItemSelected(item);

    }

    private void showUnavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Discard your changes and quit editing ");
        builder.setPositiveButton("Discard" , discardButtonClickListener);
        builder.setNegativeButton("Keep editing", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog =builder.create();
        alertDialog.show();
    }

    private void showdeleteConfirmationdialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete this reminder"  );
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deletReminder();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
           if(dialog!=null){
               dialog.dismiss();
           }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void deletReminder() {
          if(mCurrentReminderUri != null){
              int rowDeleted = getContentResolver().delete(mCurrentReminderUri , null , null);
              if (rowDeleted == 0){
                  Toast.makeText(this, "Error deleting reminder", Toast.LENGTH_SHORT).show();
              }else{
                  Toast.makeText(this, "Reminder deleted ", Toast.LENGTH_SHORT).show();
              }
          }
          finish();
    }

    private void saveReminder() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AlarmReminderContract.AlarmReminderEntry.KEY_TITLE , mTitle);
        contentValues.put(AlarmReminderContract.AlarmReminderEntry.KEY_DATE , mDate);
        contentValues.put(AlarmReminderContract.AlarmReminderEntry.KEY_TIME , mTime);
        contentValues.put(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT , mRepeat);
        contentValues.put(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_NO , mRepeatNo);
        contentValues.put(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_TYPE , mRepeatType);
        contentValues.put(AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE , mActive);

        mCalender.set(Calendar.MONTH , --mMonth);
        mCalender.set(Calendar.YEAR , mYear);
        mCalender.set(Calendar.DAY_OF_MONTH , mDay);
        mCalender.set(Calendar.HOUR_OF_DAY , mHour);
        mCalender.set(Calendar.MINUTE , mMinute);
        mCalender.set(Calendar.SECOND , 0);

        long selectedTimeStamp = mCalender.getTimeInMillis();

        if(mRepeatType.equals("Minute")){
            mRepaeatTime = Integer.parseInt(mRepeatNo) * milMinute;

        }else if(mRepeatType.equals("Hour")) {
            mRepaeatTime = Integer.parseInt(mRepeatNo) * milHour;

        }else if (mRepeatType.equals("Day")){
            mRepaeatTime = Integer.parseInt(mRepeatNo) * milDays;

        }else if (mRepeatType.equals("Week")){
            mRepaeatTime = Integer.parseInt(mRepeatNo) * milWeek;

        }else if (mRepeatType.equals("Month")){
            mRepaeatTime = Integer.parseInt(mRepeatNo) * milMonths;

        }
        if(mCurrentReminderUri == null){
            Uri newUri = getContentResolver().insert(AlarmReminderContract.AlarmReminderEntry.CONTENT_URI , contentValues);

            if (newUri==null){
                Toast.makeText(this, "Error with savingh reminder", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Reminder Saved", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            int rowsAffected = getContentResolver().update(mCurrentReminderUri , contentValues , null , null);

            if (rowsAffected == 0){
                Toast.makeText(this, "error updating it", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();
            }
        }
        if(mActive.equals(true)){
            if (mRepeat.equals("true")){
                new AlarmScheduler().setRepeatAlarm(getApplicationContext() , selectedTimeStamp , mCurrentReminderUri  , mRepaeatTime);

            }else if (mRepeat.equals("false")){
                new AlarmScheduler().setAlarm(getApplicationContext() , selectedTimeStamp , mCurrentReminderUri);

            }
            Toast.makeText(this, "Alarm time is " + selectedTimeStamp, Toast.LENGTH_LONG).show();
        }

        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
       mHour = hourOfDay;
       mMinute = minute;

       if(minute<10){
           mTime = hourOfDay + ":" + "0" + minute;
       }else{
           mTime = hourOfDay + ":" + minute;

       }
       mTimeText.setText(mTime);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
                mCurrentReminderUri,
                projection,
                null,
                null,
                null

        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1){
            return;
        }
        if (cursor.moveToFirst()){
            int titleColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_TITLE);
            int datecolumnindex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_DATE);
            int timecolumnindex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_TIME);
            int repeatColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT);
            int repeatnoColumnindex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_NO);
            int repeattypeColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_TYPE);
            int activeColumnindex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE);

            String title = cursor.getString(titleColumnIndex);
            String date = cursor.getString(datecolumnindex);
            String time = cursor.getString(timecolumnindex);
            String repeat = cursor.getString(repeatColumnIndex);
            String repeatNo = cursor.getString(repeatnoColumnindex);
            String repeattype = cursor.getString(repeattypeColumnIndex);
            String active = cursor.getString(activeColumnindex);

            mTitleText.setText(title);
            mDateText.setText(date);
            mTimeText.setText(time);
            mRepeatNoText.setText(repeatNo);
            mRepeatTypeText.setText(repeattype);
            mRepeatText.setText("Every" + repeatNo + " " + repeattype + "(s)");

            if (repeat.equals("false")){
                mRepeatSwitch.setChecked(false);
                mRepeatText.setText("off");
            }else if (repeat.equals("true")){
                mRepeatSwitch.setChecked(true);
            }
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }



}
