package mohamedabdelrazek.com.roomer.ZokaPackage;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import mohamedabdelrazek.com.roomer.GuestsData.GuestsContract;
import mohamedabdelrazek.com.roomer.R;

/**
 * Created by Mohamed on 9/20/2016.
 */
public class GuestsCursorAdapter extends CursorAdapter {
    public GuestsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name = (TextView) view.findViewById(R.id.mName);
        TextView Mobile_number = (TextView) view.findViewById(R.id.mPhone);
        ImageView genderImage = (ImageView) view.findViewById(R.id.mGenderImage);

        name.setText(cursor.getString(cursor.getColumnIndex(GuestsContract.GuestsEntry.COLUMN_GUEST_NAME)));
        Mobile_number.setText(cursor.getString(cursor.getColumnIndex(GuestsContract.GuestsEntry.COLUMN_GUEST_MOBILE_NUMBER)));
        int gender = cursor.getInt(cursor.getColumnIndex(GuestsContract.GuestsEntry.COLUMN_GUEST_GENDER));
        if (gender == GuestsContract.GuestsEntry.GENDER_MALE) {
            genderImage.setImageResource(R.drawable.male_pic);
        } else {

            genderImage.setImageResource(R.drawable.female_pic);
        }


    }
}
