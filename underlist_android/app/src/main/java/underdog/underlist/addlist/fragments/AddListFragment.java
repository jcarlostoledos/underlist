package underdog.underlist.addlist.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import underdog.underlist.AppController;
import underdog.underlist.R;
import underdog.underlist.addlist.interfaces.AddFragmentActivityInterface;
import underdog.underlist.models.addListResponseModel;
import underdog.underlist.webservices.Endpoints;

/**
 * Created by Eric on 13/07/16.
 */
public class AddListFragment extends Fragment implements AddFragmentActivityInterface, View.OnClickListener{
    int TASK_ADDED = 100;

    Endpoints endpoints;
    AppController app;

    @Bind(R.id.title_et)
    EditText title_et;
    @Bind(R.id.description_et)
    EditText description_et;
    @Bind(R.id.done_bt)
    Button done_bt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_list_fragment_layout, container, false);
        ButterKnife.bind(this, view);

        done_bt.setOnClickListener(this);
        app = (AppController) getActivity().getApplication();
        Retrofit retrofit = app.getRetrofit();
        endpoints = retrofit.create(Endpoints.class);

        return view;
    }


    @Override
    public void addList(String title, String description, String createdDate, String userId) {
        Call<addListResponseModel> call;
        call = endpoints.addList(title, description, createdDate, userId);
        call.enqueue(new Callback<addListResponseModel>() {

            @Override
            public void onResponse(Call<addListResponseModel> call, Response<addListResponseModel> response) {
                if(!response.body().isError()){
                    Toast.makeText(getActivity().getApplicationContext(), "Lista añadida", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    getActivity().setResult(TASK_ADDED, intent);
                    getActivity().finish();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<addListResponseModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.done_bt:
                String title = title_et.getText().toString();
                String description = description_et.getText().toString();
                if(!title.contentEquals("") && !description.contentEquals("")){
                    //Todo getDate and userId
                    addList(title, description, "2000-01-01 00:00:00","1");
                }
                break;
        }
    }
}
