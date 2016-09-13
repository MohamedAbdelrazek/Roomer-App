package mohamedabdelrazek.com.roomer.GuestsData;

import android.provider.BaseColumns;

/**
 * Created by Mohamed on 9/10/2016.
 */
public final class GuestsContract {

    public static final class GuestsEntry implements BaseColumns {

        public final static String TABLE_NAME = "guests";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_GUEST_NAME = "name";
        public final static String COLUMN_GUEST_AGE = "age";
        public final static String COLUMN_GUEST_ADDRESS = "address";
        public final static String COLUMN_GUEST_MOBILE_NUMBER = "mobile_number";
        public final static String COLUMN_GUEST_Email = "e_mail";
        public final static String COLUMN_GUEST_GENDER = "gender";
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;


    }

}
