package mohamedabdelrazek.com.roomer.GuestsData;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Mohamed on 9/10/2016.
 */
public final class GuestsContract {

    public static final class GuestsEntry implements BaseColumns { // GUEST ENTRY IS THE ONLY TABLE IN  DB.

        public final static String TABLE_NAME = "guests";
        public final static String _ID = BaseColumns._ID;  // PRIMARY KEY ..
        public final static String NATIONNAL_ID ="national_id";  // CONSISTS OF 14 NUMBER ....
        public final static String COLUMN_GUEST_NAME = "name"; //MUST BE FULL NAMR
        public final static String COLUMN_GUEST_AGE = "age";    // //MUST BE LARGE THAN 14  . .
        public final static String COLUMN_GUEST_ADDRESS = "address"; // ANY SUBSENT CHARS..
        public final static String COLUMN_GUEST_MOBILE_NUMBER = "mobile_number";  //CONSISTS OF 11 NUMBER  AND START WITH 0...
        public final static String COLUMN_GUEST_Email = "e_mail";  // VALIDATION ON MAIL MUST BE IN RIGHT FORMAT = DOMAIN@YAHOO.COM
        public final static String COLUMN_GUEST_GENDER = "gender";  // DEFALUT VALUE IS MALE  ...
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;


        //===================================================


        public static final String CONTENT_AUTHORITY = "mohamedabdelrazek.com.roomer";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        public static final String PATH_ROMER = "roomer";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ROMER);
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ROMER;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ROMER;



    }

}
