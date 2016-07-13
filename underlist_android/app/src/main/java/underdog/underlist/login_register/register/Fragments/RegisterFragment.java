package underdog.underlist.login_register.register.Fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import underdog.underlist.login_register.register.Interfaces.RegisterInterface;
import underdog.underlist.models.registerResponseModel;
import underdog.underlist.webservices.Endpoints;


/**
 * Created by Eric on 12/07/16.
 *
 */
public class RegisterFragment extends Fragment implements RegisterInterface, View.OnClickListener {

    @Bind(R.id.name_et)EditText name_et;
    @Bind(R.id.username_et)EditText username_et;
    @Bind(R.id.register_fab)FloatingActionButton register_fab;

    Endpoints endpoints;
    AppController app;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);
        ButterKnife.bind(this, view);
        register_fab.setOnClickListener(this);

        app = (AppController) getActivity().getApplication();
        Retrofit retrofit = app.getRetrofit();
        endpoints = retrofit.create(Endpoints.class);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_fab:
                String username = username_et.getText().toString();
                String name = name_et.getText().toString();
                if(!username.contentEquals("") && !name.contentEquals("")){
                    registerUser(name, username);
                }
                break;
        }
    }

    @Override
    public void registerUser(String name, String username) {
        Call<registerResponseModel> call;
        call = endpoints.registerUser(name, username);
        call.enqueue(new Callback<registerResponseModel>() {

            @Override
            public void onResponse(Call<registerResponseModel> call, Response<registerResponseModel> response) {
                if(!response.body().isError()){
                    Toast.makeText(getActivity().getApplicationContext(), "Cuenta creada", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<registerResponseModel> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
