package mohamedabdelrazek.com.roomer.ZokaPackage;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import mohamedabdelrazek.com.roomer.GuestsData.GuestsContract;
import mohamedabdelrazek.com.roomer.R;

import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.COLUMN_GUEST_ADDRESS;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.COLUMN_GUEST_AGE;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.COLUMN_GUEST_Email;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.COLUMN_GUEST_GENDER;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.COLUMN_GUEST_MOBILE_NUMBER;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.COLUMN_GUEST_NAME;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.GENDER_FEMALE;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.GENDER_MALE;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.NATIONNAL_ID;

public class EditGuestActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private Spinner zGenderSpinner;
    private int zGender = GENDER_MALE;
    private EditText zName;
    private EditText zAge;
    private EditText zId;
    private EditText zAddress;
    private EditText zMobile;
    private EditText zEmail;
    private Uri dCurrentUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest);
        zName = (EditText) findViewById(R.id.zNAmeEdt);
        zAge = (EditText) findViewById(R.id.zAgeEdt);
        zAddress = (EditText) findViewById(R.id.zAddress);
        zId = (EditText) findViewById(R.id.zIdEdt);
        zMobile = (EditText) findViewById(R.id.zPhoneNumber);
        zEmail = (EditText) findViewById(R.id.zEmail);
        zGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);
        setupSpinner();
        Intent intent = getIntent();
        dCurrentUri = intent.getData();
        if (dCurrentUri == null) {
            setTitle("Add a new Guest");
        } else {

            setTitle("Edit a Guest");
            getLoaderManager().initLoader(101, null, this);
        }

    }


    private void setupSpinner() {
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        zGenderSpinner.setAdapter(genderSpinnerAdapter);
        zGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {

                        zGender = GENDER_MALE; // Male
                    } else {
                        zGender = GENDER_FEMALE; // Female
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                zGender = GENDER_MALE; //  Male IF NO SELECTION OCCUR ...
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_guest_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                insertGuestInfo();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void insertGuestInfo() {
        try {


            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_GUEST_NAME, zName.getText().toString());
            contentValues.put(COLUMN_GUEST_AGE, zAge.getText().toString());
            contentValues.put(COLUMN_GUEST_MOBILE_NUMBER, zMobile.getText().toString());
            contentValues.put(COLUMN_GUEST_ADDRESS, zAddress.getText().toString());
            contentValues.put(COLUMN_GUEST_Email, zEmail.getText().toString());
            contentValues.put(COLUMN_GUEST_GENDER, zGender);
            contentValues.put(NATIONNAL_ID, zId.getText().toString());

            if (dCurrentUri == null) {
                Uri zUri = getContentResolver().insert(GuestsContract.GuestsEntry.CONTENT_URI, contentValues);
                if (zUri == null) {
                    // If the new content URI is null, then there was an error with insertion.
                    Toast.makeText(this, getString(R.string.editor_insert_guest_failed),
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Otherwise, the insertion was successful and we can display a toast.
                    Toast.makeText(this, getString(R.string.editor_insert_guest_successful),
                            Toast.LENGTH_SHORT).show();
                    finish();

                }
            } else {
                // Otherwise this is an EXISTING pet, so update the pet with content URI: mCurrentPetUri
                // and pass in the new ContentValues. Pass in null for the selection and selection args
                // because mCurrentPetUri will already identify the correct row in the database that
                // we want to modify.
                int rowsAffected = getContentResolver().update(dCurrentUri, contentValues, null, null);

                // Show a toast message depending on whether or not the update was successful.
                if (rowsAffected == 0) {
                    // If no rows were affected, then there was an error with the update.
                    Toast.makeText(this, getString(R.string.editor_update_guest_failed),
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Otherwise, the update was successful and we can display a toast.
                    Toast.makeText(this, getString(R.string.editor_update_guest_successful),
                            Toast.LENGTH_SHORT).show();
                    finish();

                }

            }
        } catch (NumberFormatException e) {
            Toast.makeText(EditGuestActivity.this, "plz specify the age !", Toast.LENGTH_SHORT).show();


        } catch (IllegalArgumentException e) {

            Toast.makeText(EditGuestActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (SQLiteConstraintException e) {
            Toast.makeText(EditGuestActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

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
        setActivityValues(cursor);

    }


    @Override
    public void onLoaderReset(Loader loader) {
        resetValues();

    }

    private void setActivityValues(Cursor cursor) {
        cursor.moveToFirst();
        zName.setText(cursor.getString(cursor.getColumnIndex(GuestsContract.GuestsEntry.COLUMN_GUEST_NAME)));
        zAge.setText(cursor.getString(cursor.getColumnIndex(GuestsContract.GuestsEntry.COLUMN_GUEST_AGE)));
        zId.setText(cursor.getString(cursor.getColumnIndex(GuestsContract.GuestsEntry.NATIONNAL_ID)));
        zAddress.setText(cursor.getString(cursor.getColumnIndex(GuestsContract.GuestsEntry.COLUMN_GUEST_ADDRESS)));
        zMobile.setText(cursor.getString(cursor.getColumnIndex(GuestsContract.GuestsEntry.COLUMN_GUEST_MOBILE_NUMBER)));
        zEmail.setText(cursor.getString(cursor.getColumnIndex(GuestsContract.GuestsEntry.COLUMN_GUEST_Email)));
        int gender = cursor.getInt(cursor.getColumnIndex(GuestsContract.GuestsEntry.COLUMN_GUEST_GENDER));
        ;

        switch (gender) {
            case GENDER_MALE:
                zGenderSpinner.setSelection(0);
                break;
            case GENDER_FEMALE:
                zGenderSpinner.setSelection(1);
                break;
            default:
                zGenderSpinner.setSelection(0);
                break;
        }

    }

    private void resetValues() {
        zName.setText("");
        zAddress.setText("");
        zEmail.setText("");
        zAge.setText("");
        zId.setText("");
        zMobile.setText("");
        zGenderSpinner.setSelection(1);
    }
}
