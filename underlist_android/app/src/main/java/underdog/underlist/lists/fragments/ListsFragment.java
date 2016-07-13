package underdog.underlist.lists.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import underdog.underlist.lists.adapters.RecyclerViewListsAdapter;
import underdog.underlist.lists.interfaces.ListFragmentInterface;
import underdog.underlist.lists.interfaces.ListRowRecyclerViewOnClickListener;
import underdog.underlist.lists.interfaces.OnListEdited;
import underdog.underlist.models.getListResponseModel;
import underdog.underlist.tasks.activity.TaskActivity;
import underdog.underlist.webservices.Endpoints;

/**
 * Created by Eric on 12/07/16.
 *
 */
public class ListsFragment extends Fragment implements ListFragmentInterface,
        ListRowRecyclerViewOnClickListener, OnListEdited{

    Typeface helvetica_neue_bold, helvetica_neue_regular, helvetica_neue_italic;
    Endpoints endpoints;
    AppController app;
    RecyclerViewListsAdapter adapter;

    @Bind(R.id.my_recycler_view) RecyclerView recyclerView;
    @Bind(R.id.progress_bar) ProgressBar progress_bar;
    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.root_view) View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lists_fragment, container, false);
        ButterKnife.bind(this, view);

        app = (AppController) getActivity().getApplication();
        Retrofit retrofit = app.getRetrofit();
        endpoints = retrofit.create(Endpoints.class);

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
        getAllLists();


        return view;
    }

    @Override
    public void setupRecyclerView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        adapter = new RecyclerViewListsAdapter(getActivity().getApplicationContext(), this, this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllLists();
            }
        });
    }

    @Override
    public void getAllLists() {
        Call<getListResponseModel> call;
        call = endpoints.getAllLists(app.getUserId());
        call.enqueue(new Callback<getListResponseModel>() {

            @Override
            public void onResponse(Call<getListResponseModel> call, Response<getListResponseModel> response) {
                if(!response.body().isError()){
                    if(adapter!=null){
                        adapter.clearAdapter();
                        adapter.notifyDataSetChanged();
                    }
                    for(int i = 0; i < response.body().getLists().size(); i++){
                        adapter.addRow(response.body().getLists().get(i));
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
                progress_bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<getListResponseModel> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                progress_bar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    @Override
    public void onRowClicked(String id, int rowPosition) {
        app.setListId(id);
        Intent intent = new Intent(getActivity(), TaskActivity.class);
        startActivity(intent);
    }

    @Override
    public void OnListEdited(int id, int rowPosition, String listName) {
        //TODO Web Service para editar nombre de lista
    }

}
