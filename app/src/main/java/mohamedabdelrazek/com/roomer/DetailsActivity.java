package mohamedabdelrazek.com.roomer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import mohamedabdelrazek.com.roomer.GuestsData.GuestModel;
import mohamedabdelrazek.com.roomer.GuestsData.ManageDataBase;

public class DetailsActivity extends AppCompatActivity {
    private GuestModel guestModel;
    private ManageDataBase zManageDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();

        zManageDataBase=new ManageDataBase(this);
        guestModel = (GuestModel) intent.getSerializableExtra("data");
        TextView textView = (TextView) findViewById(R.id.dName);
        textView.setText(guestModel.getName());
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
            case  R.id.action_remove:
                removeGuestInformation(guestModel.getId());
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void removeGuestInformation(int id) {
       int result=  zManageDataBase.removeGustInformation(id);
        if (result!=0)
        {
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

}
