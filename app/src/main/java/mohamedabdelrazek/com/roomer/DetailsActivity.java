package mohamedabdelrazek.com.roomer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import mohamedabdelrazek.com.roomer.GuestsData.GuestModel;

public class DetailsActivity extends AppCompatActivity {
    private GuestModel guestModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        guestModel = (GuestModel) intent.getSerializableExtra("data");
        TextView textView = (TextView) findViewById(R.id.dName);
        textView.setText(guestModel.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
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

        }
        return super.onOptionsItemSelected(item);
    }
}
