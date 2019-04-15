package com.example.pregreminderapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Switch;

public class AlarmReminderProvider extends ContentProvider {
    private static  final String LOG_TAG = AlarmReminderProvider.class.getSimpleName();

    private static  final int REMINDER = 100;

    private static  final int REMINDER_ID =101;

    private static final UriMatcher sUriMacther = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMacther.addURI(AlarmReminderContract.CONTENT_AUTHORITY , AlarmReminderContract.PATH_VEHICLE , REMINDER);
        sUriMacther.addURI(AlarmReminderContract.CONTENT_AUTHORITY , AlarmReminderContract.PATH_VEHICLE + "/#" , REMINDER_ID );
    }

    private  AlarmReminderDbHelper alarmReminderDbHelper;

    @Override
    public boolean onCreate() {
        alarmReminderDbHelper = new AlarmReminderDbHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri,  String[] projection, String selection,  String[] selectionArgs,  String sortOrder) {
        SQLiteDatabase sqLiteDatabase = alarmReminderDbHelper.getReadableDatabase();
        Cursor cursor = null;

        int match = sUriMacther.match(uri);

        switch (match){
            case REMINDER :
                cursor = sqLiteDatabase.query(AlarmReminderContract.AlarmReminderEntry.TABLE_NAME , projection , selection , selectionArgs
                ,null ,null , sortOrder);
                break;

            case REMINDER_ID:
                selection = AlarmReminderContract.AlarmReminderEntry._ID +"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = sqLiteDatabase.query(AlarmReminderContract.AlarmReminderEntry.TABLE_NAME , projection , selection , selectionArgs,
                        null , null , sortOrder);
                break;
                default:
                    throw new IllegalArgumentException("Cannot query unknown Uri" + uri);




        }
        cursor.setNotificationUri(getContext().getContentResolver() , uri);

        return cursor;
    }


    @Override
    public String getType( Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete( Uri uri,  String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update( Uri uri, ContentValues values,  String selection,  String[] selectionArgs) {
        return 0;
    }
}
