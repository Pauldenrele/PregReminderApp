package com.example.pregreminderapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.pregreminderapp.data.AlarmReminderContract;

//New
public class AlarmCursorAdapter extends CursorAdapter {


    private TextView mTitleText , mDateandTimetext , mRepeatinfotext;
    private ImageView mActiveimage , mThumbnailImage;
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    private TextDrawable mTextDrawable;
    public AlarmCursorAdapter(Context context, Cursor c) {
        super(context, c,0);
    }






    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.alarm_item , parent , false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        mTitleText =view.findViewById(R.id.recycle_title);
        mDateandTimetext = view.findViewById(R.id.recycle_date_time);
        mRepeatinfotext = view.findViewById(R.id.recycle_repeat_info);
        mActiveimage = view.findViewById(R.id.active_image);
        mThumbnailImage = view.findViewById(R.id.thumbnail_image);

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

        String dateTime = date + " " + time;

        setReminderTitle(title);
        setReminderDateTime(dateTime);
        setReminderRepeatinfo(repeat ,repeatNo , repeattype);
        setActiveImage(active);



    }



    public void setReminderTitle(String title){
        mTitleText.setText(title);

        String letter = "A";

        if(title != null && !title.isEmpty()){
            letter = title.substring(0,1);

        }

        int color = mColorGenerator.getRandomColor();
        mTextDrawable = TextDrawable.builder().buildRound(letter , color);
        mThumbnailImage.setImageDrawable(mTextDrawable);
    }

    public void setReminderDateTime (String datetime){
        mDateandTimetext.setText(datetime);
    }
    public void setReminderRepeatinfo(String repeat , String repeatNo , String repeattype){
        if (repeat.equals("true")){
            mRepeatinfotext.setText("Every" + repeatNo + " " + repeattype + "(s)");
        }else if(repeat.equals("false")){
            mRepeatinfotext.setText("Repeat off");
        }

    }
    public void setActiveImage(String active){
        if (active.equals("true")){
            mActiveimage.setImageResource(R.drawable.ic_notifications_black_24dp);
        }else if(active.equals("false")){
            mActiveimage.setImageResource(R.drawable.ic_notifications_off_black_24dp);
        }
    }
}
