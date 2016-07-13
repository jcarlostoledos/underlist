package underdog.underlist.lists.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import retrofit2.Retrofit;
import underdog.underlist.AppController;
import underdog.underlist.R;
import underdog.underlist.addlist.activity.AddListActivity;
import underdog.underlist.lists.adapters.RecyclerViewListsAdapter;
import underdog.underlist.lists.fragments.ListsFragment;
import underdog.underlist.lists.interfaces.ListInterface;
import underdog.underlist.webservices.Endpoints;

public class ListsActivity extends AppCompatActivity implements ListInterface{

    int TASK_ADDED = 100;
    FragmentTransaction ft;
    Endpoints endpoints;
    AppController app;
    RecyclerViewListsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
//        if(getSupportActionBar()!=null){
//            getSupportActionBar().setHomeButtonEnabled(true);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }

        app = (AppController) getApplication();
        Retrofit retrofit = app.getRetrofit();
        endpoints = retrofit.create(Endpoints.class);


        showListFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;

            case R.id.addListAction:
                startActivityForResult(new Intent(this, AddListActivity.class), TASK_ADDED);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==TASK_ADDED){
            showListFragment();
        }
    }

    @Override
    public void showListFragment() {
        // Begin the transaction
        ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.fragment_placeholder, new ListsFragment());
        // Complete the changes added above
        ft.commitAllowingStateLoss();
    }


}
