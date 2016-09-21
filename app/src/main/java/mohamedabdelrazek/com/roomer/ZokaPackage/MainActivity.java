package mohamedabdelrazek.com.roomer.ZokaPackage;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import mohamedabdelrazek.com.roomer.GuestsData.GuestModel;
import mohamedabdelrazek.com.roomer.GuestsData.GuestsContract;
import mohamedabdelrazek.com.roomer.R;

import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.COLUMN_GUEST_ADDRESS;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.COLUMN_GUEST_AGE;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.COLUMN_GUEST_Email;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.COLUMN_GUEST_GENDER;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.COLUMN_GUEST_MOBILE_NUMBER;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.COLUMN_GUEST_NAME;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.CONTENT_URI;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.NATIONNAL_ID;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry._ID;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ListView zGuestslistView;
    private GuestsCursorAdapter zCursorAdapter;
    private View emptyView;
    private FloatingActionButton fab;
    private final int ROOMER_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zGuestslistView = (ListView) findViewById(R.id.zListView);
        emptyView = findViewById(R.id.empty_view);
        zGuestslistView.setEmptyView(emptyView);
        fab = (FloatingActionButton) findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditGuestActivity.class);
                startActivity(intent);
            }
        });
        zGuestslistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Uri uri = ContentUris.withAppendedId(CONTENT_URI, id);
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.setData(uri);
                startActivity(intent);

            }
        });
        zCursorAdapter = new GuestsCursorAdapter(this, null);

        zGuestslistView.setAdapter(zCursorAdapter);
        getLoaderManager().initLoader(ROOMER_LOADER, null, this);

    }


    @Override
    protected void onStart() {
        super.onStart();


    }


    public ArrayList<GuestModel> DisplayDbInfo() {
        ArrayList<GuestModel> arrayList = new ArrayList<>();
        String[] projection = {NATIONNAL_ID, COLUMN_GUEST_ADDRESS, COLUMN_GUEST_AGE, COLUMN_GUEST_NAME,
                COLUMN_GUEST_Email, COLUMN_GUEST_GENDER, COLUMN_GUEST_MOBILE_NUMBER};

        Cursor zCursor = getContentResolver().query(GuestsContract.GuestsEntry.CONTENT_URI, null, null, null, null);
        while (zCursor.moveToNext()) {
            GuestModel guestModel = new GuestModel();
            guestModel.setNational_id(zCursor.getString(zCursor.getColumnIndex(NATIONNAL_ID)));
            guestModel.setId(zCursor.getString(zCursor.getColumnIndex(_ID)));
            guestModel.setName(zCursor.getString(zCursor.getColumnIndex(COLUMN_GUEST_NAME)));
            guestModel.setAge(zCursor.getString(zCursor.getColumnIndex(COLUMN_GUEST_AGE)));
            guestModel.setAddress(zCursor.getString(zCursor.getColumnIndex(COLUMN_GUEST_ADDRESS)));
            guestModel.setE_mail(zCursor.getString(zCursor.getColumnIndex(COLUMN_GUEST_Email)));
            guestModel.setGender(zCursor.getInt(zCursor.getColumnIndex(COLUMN_GUEST_GENDER)));
            guestModel.setPhone(zCursor.getString(zCursor.getColumnIndex(COLUMN_GUEST_MOBILE_NUMBER)));
            arrayList.add(guestModel);
        }
        return arrayList;

    }


    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {


        String[] projection = {_ID, COLUMN_GUEST_NAME, COLUMN_GUEST_MOBILE_NUMBER, COLUMN_GUEST_GENDER};
        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                GuestsContract.GuestsEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order


    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        zCursorAdapter.swapCursor(cursor);
    }


    @Override
    public void onLoaderReset(Loader loader) {
        zCursorAdapter.swapCursor(null);

    }
}
