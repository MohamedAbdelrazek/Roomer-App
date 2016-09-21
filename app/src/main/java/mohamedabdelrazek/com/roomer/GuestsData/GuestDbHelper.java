package mohamedabdelrazek.com.roomer.GuestsData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.*;


/**
 * Created by Mohamed on 9/19/2016.
 */
public class GuestDbHelper extends SQLiteOpenHelper {

    private static final String DATA_BASE_NAME = "GUESTS_DEMO.db";
    private static final int VERSION = 3;


    public GuestDbHelper(Context context) {
        super(context, DATA_BASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_GUESTS_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NATIONNAL_ID + " TEXT NOT NULL UNIQUE ,"
                + COLUMN_GUEST_NAME + " TEXT NOT NULL , " + COLUMN_GUEST_AGE + " Text NOT NULL ,"
                + COLUMN_GUEST_GENDER + " INTEGER NOT NULL," + COLUMN_GUEST_ADDRESS + " TEXT NOT NULL,"
                + COLUMN_GUEST_MOBILE_NUMBER + " TEXT," + COLUMN_GUEST_Email + " TEXT NOT NULL );";
        sqLiteDatabase.execSQL(CREATE_GUESTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String ALTER_TABLE = "DROP TABLE IF EXIST " + TABLE_NAME + " ;";
        sqLiteDatabase.execSQL(ALTER_TABLE);
        onCreate(sqLiteDatabase);
    }
}