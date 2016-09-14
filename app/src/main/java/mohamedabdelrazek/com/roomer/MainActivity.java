package mohamedabdelrazek.com.roomer;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mohamedabdelrazek.com.roomer.GuestsData.GuestModel;
import mohamedabdelrazek.com.roomer.GuestsData.ManageDataBase;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ManageDataBase manageDataBase;
    private GustsAdapter adapter;
    private SearchView searchView;
    private TextView mainText;
    private ArrayList<GuestModel> dArray;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manageDataBase = new ManageDataBase(this);
        listView = (ListView) findViewById(R.id.zListView);
        mainText = (TextView) findViewById(R.id.main_text);
        fab = (FloatingActionButton) findViewById(R.id.fab_add);
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


    }


    @Override
    protected void onStart() {
        super.onStart();
        dArray = getAllData();

        //  if the  list empty a Text view will appear with a message no Roomers ! // that's all .........

        if (dArray.size() >= 1) {

            mainText.setVisibility(View.GONE);

        }
        adapter = new GustsAdapter(this, dArray);
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/main_menul file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performNewSearch(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String key) {

                performNewSearch(key);


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
            case R.id.action_search:
                searchView.setIconified(false); // to hide keyboard when the app start and show it when the user hit the search icon ...
        }
        return super.onOptionsItemSelected(item);
    }

    private void performNewSearch(String query) {

        dArray.clear();
        for (int i = 0; i < getAllData().size(); i++) {
            if (getAllData().get(i).getName().toLowerCase().contains(query)) {
                dArray.add(getAllData().get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }

}
