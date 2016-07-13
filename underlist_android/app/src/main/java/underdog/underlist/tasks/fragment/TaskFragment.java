package underdog.underlist.tasks.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import underdog.underlist.AppController;
import underdog.underlist.R;
import underdog.underlist.models.getTasksResponseModel;
import underdog.underlist.tasks.adapters.RecyclerViewTasksAdapter;
import underdog.underlist.tasks.interfaces.TaskFragmentInterface;
import underdog.underlist.tasks.interfaces.TasksRowRecyclerViewOnClickListener;
import underdog.underlist.webservices.Endpoints;


/**
 * Created by Eric on 12/07/16.
 *
 */
public class TaskFragment extends Fragment implements TaskFragmentInterface,
        TasksRowRecyclerViewOnClickListener {

    Typeface helvetica_neue_bold, helvetica_neue_regular, helvetica_neue_italic;
    Endpoints endpoints;
    AppController app;
    RecyclerViewTasksAdapter adapter;
    String listId;

    @Bind(R.id.my_recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.progress_bar)
    ProgressBar progress_bar;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.root_view) View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_fragment, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        app = (AppController) getActivity().getApplication();
        Retrofit retrofit = app.getRetrofit();
        endpoints = retrofit.create(Endpoints.class);
        listId = app.getListId();

        helvetica_neue_bold = Typeface.createFromAsset(getActivity()
                        .getApplicationContext().getAssets(),
                "helvetica_neue_bold.ttf");

        helvetica_neue_regular = Typeface.createFromAsset(getActivity()
                        .getApplicationContext().getAssets(),
                "helvetica_neue_regular.ttf");

        helvetica_neue_italic = Typeface.createFromAsset(getActivity()
                        .getApplicationContext().getAssets(),
                "helvetica_neue_italic.otf");

        setupRecyclerView();
        showTasks();

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_tasks, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.addTaskAction:
                //Todo show new Task dialog
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setupRecyclerView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        adapter = new RecyclerViewTasksAdapter(getActivity().getApplicationContext(),
                this, helvetica_neue_bold, helvetica_neue_regular, helvetica_neue_italic);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO hacer de nuevo el fetch
            }
        });
    }




    @Override
    public void onRowClicked(int id, int rowPosition) {
        //TODO mostrar dialogo
        Toast.makeText(getContext(), "Editar task", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTasks() {
        Call<getTasksResponseModel> call;
        call = endpoints.getAllTasks(app.getUserId(), listId);
        call.enqueue(new Callback<getTasksResponseModel>() {

            @Override
            public void onResponse(Call<getTasksResponseModel> call, Response<getTasksResponseModel> response) {
                if(!response.body().isError()){
                    if(adapter!=null){
                        adapter.clearAdapter();
                        adapter.notifyDataSetChanged();
                    }
                    for(int i =0; i < response.body().getTasks().size(); i++){
                        adapter.addRow(response.body().getTasks().get(i));
                    }
                }else{
                    //Todo pass error
                    Toast.makeText(getActivity().getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
                progress_bar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<getTasksResponseModel> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                progress_bar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

}
