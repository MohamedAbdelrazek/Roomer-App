package mohamedabdelrazek.com.roomer.ZokaPackage;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import mohamedabdelrazek.com.roomer.GuestsData.GuestsContract;
import mohamedabdelrazek.com.roomer.R;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private TextView dNAme;
    private TextView dAge;
    private TextView dID;
    private ImageView dImageView;
    private TextView dGender;
    private TextView dAddress;
    private TextView dMobile;
    private GuestsCursorAdapter dCursorAdapter;
    private TextView dEmail;
    Uri dCurrentUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        dCurrentUri = intent.getData();

        dCursorAdapter = new GuestsCursorAdapter(this, null);
        dNAme = (TextView) findViewById(R.id.dName);
        dAddress = (TextView) findViewById(R.id.dAddress);
        dAge = (TextView) findViewById(R.id.dAge);
        dID = (TextView) findViewById(R.id.dId);
        dGender = (TextView) findViewById(R.id.dGender);
        dEmail = (TextView) findViewById(R.id.dEmail);
        dMobile = (TextView) findViewById(R.id.dMobile_number);
        dImageView = (ImageView) findViewById(R.id.dImgView);
        getLoaderManager().initLoader(1, null, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent intent=new Intent(DetailsActivity.this, EditGuestActivity.class);
                intent.setData(dCurrentUri);
                startActivity(intent);
                return true;
            case R.id.action_remove:
                deleteGuest();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteGuest() {
if (dCurrentUri!=null) {
    getContentResolver().delete(dCurrentUri, null, null);
    finish();
}

    }


    private void setActivityValues(Cursor cursor) {
        cursor.moveToFirst();

        dNAme.setText(cursor.getString(cursor.getColumnIndex(GuestsContract.GuestsEntry.COLUMN_GUEST_NAME)));
        dAge.setText(""+cursor.getInt(cursor.getColumnIndex(GuestsContract.GuestsEntry.COLUMN_GUEST_AGE)));
        dID.setText(cursor.getString(cursor.getColumnIndex(GuestsContract.GuestsEntry.NATIONNAL_ID)));
        dAddress.setText(cursor.getString(cursor.getColumnIndex(GuestsContract.GuestsEntry.COLUMN_GUEST_ADDRESS)));
        dMobile.setText(cursor.getString(cursor.getColumnIndex(GuestsContract.GuestsEntry.COLUMN_GUEST_MOBILE_NUMBER)));
        dEmail.setText(cursor.getString(cursor.getColumnIndex(GuestsContract.GuestsEntry.COLUMN_GUEST_Email)));
        int gender = cursor.getInt(cursor.getColumnIndex(GuestsContract.GuestsEntry.COLUMN_GUEST_GENDER));



        if (gender == GuestsContract.GuestsEntry.GENDER_MALE) {
            dGender.setText("Male");
            dImageView.setImageResource(R.drawable.male_main_img);

        } else {
            dGender.setText("Female");
            dImageView.setImageResource(R.drawable.female_main_img);
        }

    }


    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {


        return new CursorLoader(this,   // Parent activity context
                dCurrentUri,   // Provider content URI to query
                null,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        setActivityValues(cursor);


    }

    @Override
    public void onLoaderReset(Loader loader) {

        resetValues();
    }

    private void resetValues() {
        dNAme.setText("");
        dAddress.setText("");
        dEmail.setText("");
        dAge.setText("");
        dID.setText("");
        dMobile.setText("");
        dGender.setText("MALE");
    }
}
