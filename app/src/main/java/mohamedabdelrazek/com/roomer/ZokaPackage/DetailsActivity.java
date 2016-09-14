package mohamedabdelrazek.com.roomer.ZokaPackage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import mohamedabdelrazek.com.roomer.GuestsData.GuestModel;
import mohamedabdelrazek.com.roomer.GuestsData.GuestsContract;
import mohamedabdelrazek.com.roomer.GuestsData.ManageDataBase;
import mohamedabdelrazek.com.roomer.R;

public class DetailsActivity extends AppCompatActivity {
    private GuestModel guestModel;
    private ManageDataBase zManageDataBase;
    private TextView dNAme;
    private TextView dAge;
    private TextView dID;
    private ImageView dImageView;
    private TextView dGender;
    private TextView dAddress;
    private TextView dMobile;
    private TextView dEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();

        zManageDataBase = new ManageDataBase(this);
        guestModel = (GuestModel) intent.getSerializableExtra("data");
        dNAme = (TextView) findViewById(R.id.dName);
        dAddress = (TextView) findViewById(R.id.dAddress);
        dAge = (TextView) findViewById(R.id.dAge);
        dID = (TextView) findViewById(R.id.dId);
        dGender = (TextView) findViewById(R.id.dGender);
        dEmail = (TextView) findViewById(R.id.dEmail);
        dMobile = (TextView) findViewById(R.id.dMobile_number);
        dImageView= (ImageView) findViewById(R.id.dImgView);
        setActivityValues();
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
                Intent intent = new Intent(this, UpadteActivity.class);
                intent.putExtra("UPDATE", guestModel);
                startActivity(intent);
                return true;
            case R.id.action_remove:
                removeGuestInformation(guestModel.getId());
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void removeGuestInformation(int id) {
        int result = zManageDataBase.removeGustInformation(id);
        if (result != 0) {
            final ProgressDialog progress = ProgressDialog.show(DetailsActivity.this, "removing guest Info ...",
                    "please wait", true);


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            progress.dismiss();
                            Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(DetailsActivity.this, "Guest information removed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }).start();
        }

    }

    private void setActivityValues() {

        dNAme.setText(guestModel.getName());
        dAge.setText(""+guestModel.getAge());
        dID.setText(""+guestModel.getId());
        dAddress.setText(guestModel.getAddress());
        dMobile.setText(guestModel.getPhone());
        dEmail.setText(guestModel.getE_mail());
        int gender= guestModel.getGender();
        if (gender== GuestsContract.GuestsEntry.GENDER_MALE)
        {
            dGender.setText("Male");
            dImageView.setImageResource(R.drawable.male_main_img);

        }
        else
        {
            dGender.setText("Female");
            dImageView.setImageResource(R.drawable.female_main_img);
        }

    }


}
