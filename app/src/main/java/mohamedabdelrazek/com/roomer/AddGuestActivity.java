package mohamedabdelrazek.com.roomer;

import android.database.sqlite.SQLiteConstraintException;
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

import mohamedabdelrazek.com.roomer.GuestsData.GuestModel;
import mohamedabdelrazek.com.roomer.GuestsData.ManageDataBase;

import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.GENDER_FEMALE;
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.GENDER_MALE;


public class AddGuestActivity extends AppCompatActivity {
    private ManageDataBase zManageDataBase;
    private Spinner zGenderSpinner;
    private int zGender = GENDER_MALE;
    private EditText zName;
    private EditText zAge;
    private EditText zId;
    private EditText zAddress;
    private EditText zMobileNumber;
    private EditText zEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest);
        zManageDataBase = new ManageDataBase(this);
        zName = (EditText) findViewById(R.id.zNAmeEdt);
        zAge = (EditText) findViewById(R.id.zAgeEdt);
        zAddress = (EditText) findViewById(R.id.zAddress);
        zId = (EditText) findViewById(R.id.zIdEdt);
        zMobileNumber = (EditText) findViewById(R.id.zPhoneNumber);
        zEmail = (EditText) findViewById(R.id.zEmail);
        zGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);
        setupSpinner();
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
                zGender = GENDER_MALE; // Male
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detalis_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                SaveGuestData();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void SaveGuestData() {

        GuestModel guestModel = new GuestModel();

        try {

            if (zName.getText().toString().length() > 1) {
                guestModel.setName(zName.getText().toString());
            }
            if (zAddress.getText().toString().length() > 1) {
                guestModel.setAddress(zAddress.getText().toString());
            }
            if (zEmail.getText().toString().length() > 1) {
                guestModel.setE_mail(zEmail.getText().toString());
            }
            if (zMobileNumber.getText().toString().length() > 1) {
                guestModel.setPhone(zMobileNumber.getText().toString());
            }
            guestModel.setGender(zGender); // default value set to be Male ;
            guestModel.setAge(Integer.parseInt(zAge.getText().toString()));// if the user doesnt enter any values it will throw

            if (zId.getText().toString().length() >= 1 && zId.getText().toString() != " ") {
                //to avoid  " " value  cause id is String type
                guestModel.setId(zId.getText().toString());
            }
            long i = zManageDataBase.insertGuestInformation(guestModel);
            if (i != -1) {

                Toast.makeText(AddGuestActivity.this, "Guest INFO saved", Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException r) { //if no value for age entered .
            Toast.makeText(AddGuestActivity.this, "numer", Toast.LENGTH_SHORT).show();
        } catch (SQLiteConstraintException e) {
            String message = e.getMessage();
            Toast.makeText(AddGuestActivity.this, message.substring(message.indexOf(".") + 1, message.indexOf("(")), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {


        }

    }

}
