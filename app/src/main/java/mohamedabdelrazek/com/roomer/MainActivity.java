package mohamedabdelrazek.com.roomer;

import android.app.SearchManager;
import android.content.Context;
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
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import mohamedabdelrazek.com.roomer.GuestsData.GuestModel;
import mohamedabdelrazek.com.roomer.GuestsData.ManageDataBase;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ManageDataBase manageDataBase;
    GustsAdapter adapter;
    ArrayList<GuestModel> dArray;
    ArrayList<String> sArrayList; // to save a pure copy of Gusts names will be used in searching..,......


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sArrayList = new ArrayList<>();
        manageDataBase = new ManageDataBase(this);
        listView = (ListView) findViewById(R.id.zListView);
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
                intent.putExtra("data", dArray.get(i));
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

        dArray=getAllData();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("ZOKA", "ON START");
        adapter = new GustsAdapter(this, dArray);
        listView.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/main_menul file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);
        final Context c = this;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String key) {
                searchIn(key);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String key) {
                searchIn(key);
                return false;
            }
        });

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

    private void searchIn(String key) {

        dArray.clear();
        for (int i = 0; i < getAllData().size(); i++) {
            if (getAllData().get(i).getName().toLowerCase().contains(key)) {
                dArray.add(getAllData().get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }

}
