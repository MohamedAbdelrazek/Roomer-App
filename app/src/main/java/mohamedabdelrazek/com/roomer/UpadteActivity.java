package mohamedabdelrazek.com.roomer;

import android.content.Intent;
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
import static mohamedabdelrazek.com.roomer.GuestsData.GuestsContract.GuestsEntry.*;
public class UpadteActivity extends AppCompatActivity {
    GuestModel guestModel;
    private ManageDataBase uManageDataBase;
    private Spinner uGenderSpinner;
    private int uGender = GENDER_MALE;
    private EditText uName;
    private EditText uAge;
    private EditText uId;
    private EditText uAddress;
    private EditText uMobileNumber;
    private EditText uEmail;
    private String OLDID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upadte);
        uManageDataBase = new ManageDataBase(this);
        uName = (EditText) findViewById(R.id.uNAmeEdt);
        uAge = (EditText) findViewById(R.id.uAgeEdt);
        uAddress = (EditText) findViewById(R.id.uAddress);
        uId = (EditText) findViewById(R.id.uIdEdt);
        uMobileNumber = (EditText) findViewById(R.id.uPhoneNumber);
        uEmail = (EditText) findViewById(R.id.uEmail);
        uGenderSpinner = (Spinner) findViewById(R.id.uSpinner_gender);
        setupSpinner();
        Intent intent = getIntent();
        guestModel = (GuestModel) intent.getSerializableExtra("UPDATE");
        OLDID = guestModel.getId();
        RestoreDefaultValues();


    }


    private void setupSpinner() {
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        uGenderSpinner.setAdapter(genderSpinnerAdapter);

        uGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {

                        uGender = GENDER_MALE; // Male
                    } else {
                        uGender = GENDER_FEMALE; // Female
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                uGender = GENDER_MALE; // Male
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_save:
                updateInformation();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void updateInformation() {

        GuestModel guestModel = new GuestModel();
        try {
            if (uName.getText().toString().length() >= 1) {
                guestModel.setName(uName.getText().toString());
            }
            if (uAddress.getText().toString().length() >= 1) {
                guestModel.setAddress(uAddress.getText().toString());
            }
            if (uEmail.getText().toString().length() >= 1) {
                guestModel.setE_mail(uEmail.getText().toString());
            }
            if (uMobileNumber.getText().toString().length() >= 1) {
                guestModel.setPhone(uMobileNumber.getText().toString());
            }
            guestModel.setGender(uGender); // default value set to be Male ;
            guestModel.setAge(Integer.parseInt(uAge.getText().toString()));// if the user doesnt enter any values it will throw

            if (uId.getText().toString().length() >= 1) {
                //to avoid  " " value  cause id is String type
                guestModel.setId(uId.getText().toString());
            }
            long result = uManageDataBase.UpdateInformation(OLDID, guestModel);
            if (result != -1) {
                Toast.makeText(UpadteActivity.this, "Information Updated Successfully !", Toast.LENGTH_LONG).show();
            }
        } catch (NumberFormatException r) { //if no value for age entered .
            Toast.makeText(UpadteActivity.this, "PLZ enter the age!", Toast.LENGTH_SHORT).show();
        } catch (SQLiteConstraintException e) {
            String message = e.getMessage();
            Toast.makeText(UpadteActivity.this, message.substring(message.indexOf(".") + 1, message.indexOf("(")), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(UpadteActivity.this, "Exception EE ", Toast.LENGTH_SHORT).show();

        }
    }

    private void RestoreDefaultValues() {
        uName.setText(guestModel.getName());
        uAddress.setText(guestModel.getAddress());
        uAge.setText("" + guestModel.getAge());
        uEmail.setText("" + guestModel.getE_mail());
        uId.setText(guestModel.getId());
        uMobileNumber.setText(guestModel.getPhone());
    }
}