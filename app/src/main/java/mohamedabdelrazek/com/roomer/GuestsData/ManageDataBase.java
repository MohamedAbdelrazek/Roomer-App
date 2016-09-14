package mohamedabdelrazek.com.roomer.GuestsData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.COLUMN_GUEST_ADDRESS;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.COLUMN_GUEST_AGE;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.COLUMN_GUEST_Email;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.COLUMN_GUEST_GENDER;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.COLUMN_GUEST_MOBILE_NUMBER;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.COLUMN_GUEST_NAME;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.TABLE_NAME;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry._ID;

/**
 * Created by Mohamed on 9/10/2016.
 */
public class ManageDataBase {
    Context zContext;
    GuestsHelper zGuestsHelper;
    SQLiteDatabase zSqLiteDatabase;

    public ManageDataBase(Context context) {
        this.zContext = context;
        zGuestsHelper = new GuestsHelper(context);

    }

    public long UpdateInformation(int byID, GuestModel uGuestModel) {
        zSqLiteDatabase = zGuestsHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_GUEST_NAME, uGuestModel.getName());
        contentValues.put(_ID, uGuestModel.getId());
        contentValues.put(COLUMN_GUEST_ADDRESS, uGuestModel.getAddress());
        contentValues.put(COLUMN_GUEST_MOBILE_NUMBER, uGuestModel.getPhone());
        contentValues.put(COLUMN_GUEST_Email, uGuestModel.getE_mail());
        contentValues.put(COLUMN_GUEST_GENDER, uGuestModel.getGender());
        contentValues.put(COLUMN_GUEST_AGE, uGuestModel.getAge());
        long i = zSqLiteDatabase.update(TABLE_NAME, contentValues, _ID + " = "+byID,null);

        return i;
    }

    public long insertGuestInformation(GuestModel zGuestModel) {

        zSqLiteDatabase = zGuestsHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_GUEST_NAME, zGuestModel.getName());
        contentValues.put(_ID, zGuestModel.getId());
        contentValues.put(COLUMN_GUEST_ADDRESS, zGuestModel.getAddress());
        contentValues.put(COLUMN_GUEST_MOBILE_NUMBER, zGuestModel.getPhone());
        contentValues.put(COLUMN_GUEST_Email, zGuestModel.getE_mail());
        contentValues.put(COLUMN_GUEST_GENDER, zGuestModel.getGender());
        contentValues.put(COLUMN_GUEST_AGE, zGuestModel.getAge());
        Long x = zSqLiteDatabase.insertOrThrow(TABLE_NAME, null, contentValues);
        return x;

    }
    public int removeGustInformation(int   id) {
        zSqLiteDatabase = zGuestsHelper.getWritableDatabase();
     int r=   zSqLiteDatabase.delete(TABLE_NAME, _ID + " =  " + id, null);
        return r;


    }

    ArrayList<GuestModel> arrayList;

    public ArrayList<GuestModel> getAllData() {
        zSqLiteDatabase = zGuestsHelper.getWritableDatabase();
        arrayList = new ArrayList();
        Cursor zCursor = zSqLiteDatabase.query(TABLE_NAME, null, null, null, null, null, null);

        while (zCursor.moveToNext()) {
            GuestModel guestModel = new GuestModel();
            guestModel.setId(zCursor.getInt(zCursor.getColumnIndex(_ID)));
            guestModel.setName(zCursor.getString(zCursor.getColumnIndex(COLUMN_GUEST_NAME)));
            guestModel.setAge(zCursor.getInt(zCursor.getColumnIndex(COLUMN_GUEST_AGE)));
            guestModel.setAddress(zCursor.getString(zCursor.getColumnIndex(COLUMN_GUEST_ADDRESS)));
            guestModel.setE_mail(zCursor.getString(zCursor.getColumnIndex(COLUMN_GUEST_Email)));
            guestModel.setGender(zCursor.getInt(zCursor.getColumnIndex(COLUMN_GUEST_GENDER)));
            guestModel.setPhone(zCursor.getString(zCursor.getColumnIndex(COLUMN_GUEST_MOBILE_NUMBER)));
            arrayList.add(guestModel);
        }
        return arrayList;
    }

    private class GuestsHelper extends SQLiteOpenHelper {

        private static final String DATA_BASE_NAME = "GUESTS.db";
        private static final int VERSION = 3;


        public GuestsHelper(Context context) {
            super(context, DATA_BASE_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String CREATE_GUESTS_TABLE = "create table " + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_GUEST_NAME + " TEXT NOT NULL , " + COLUMN_GUEST_AGE + " INTEGER,"
                    + COLUMN_GUEST_GENDER + " INTEGER NOT NULL," + COLUMN_GUEST_ADDRESS + " TEXT NOT NULL,"
                    + COLUMN_GUEST_MOBILE_NUMBER + " TEXT NOT NULL," + COLUMN_GUEST_Email + " TEXT );";
            sqLiteDatabase.execSQL(CREATE_GUESTS_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            final String Alter_TABLE = "DROP TABLE IF EXIST "+TABLE_NAME+" ;";
            sqLiteDatabase.execSQL(Alter_TABLE);
            onCreate(sqLiteDatabase);
        }
    }
}
