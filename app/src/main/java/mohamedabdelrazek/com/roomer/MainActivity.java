package mohamedabdelrazek.com.roomer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import mohamedabdelrazek.com.roomer.GuestsData.GuestModel;
import mohamedabdelrazek.com.roomer.GuestsData.ManageDataBase;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ManageDataBase manageDataBase;
    GustsAdapter adapter;
    ArrayList<String> sArrayList; // to save a pure copy of Gusts names will be used in searching..,......


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sArrayList = new ArrayList<>();
        manageDataBase = new ManageDataBase(this);
        listView = (ListView) findViewById(R.id.zListView);


        // listView.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddGuestActivity.class);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("data", getAllData().get(i));
                startActivity(intent);


            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "" + getAllData().get(i).getName(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter = new GustsAdapter(this, getAllData());
        listView.setAdapter(adapter);
        for (int i = 0; i < getAllData().size(); i++) {
            GuestModel guestModel = getAllData().get(i);
            sArrayList.add(guestModel.getName());

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/main_menul file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public ArrayList<GuestModel> getAllData()

    {
        return manageDataBase.getAllData();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                // Do nothing for now
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                for (int i = 0; i < sArrayList.size(); i++) {
                    Log.i("ZOKA", " " + i + "  " + sArrayList.get(i));
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
