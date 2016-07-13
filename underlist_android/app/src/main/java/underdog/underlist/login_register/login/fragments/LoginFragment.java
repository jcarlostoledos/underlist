package underdog.underlist.login_register.login.fragments;

import android.content.Intent;
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
import underdog.underlist.lists.activities.ListsActivity;
import underdog.underlist.login_register.login.interfaces.LoginInterface;
import underdog.underlist.models.loginResponseModel;
import underdog.underlist.webservices.Endpoints;


/**
 * Created by Eric on 12/07/16.
 *
 */
public class LoginFragment extends Fragment implements LoginInterface, View.OnClickListener {

    @Bind(R.id.username_et)EditText username_et;
    @Bind(R.id.login_fab)FloatingActionButton login_fab;

    Endpoints endpoints;
    AppController app;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        ButterKnife.bind(this, view);
        login_fab.setOnClickListener(this);

        app = (AppController) getActivity().getApplication();
        Retrofit retrofit = app.getRetrofit();
        endpoints = retrofit.create(Endpoints.class);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_fab:
                String username = username_et.getText().toString();
                if(!username.contentEquals("")){
                    loginUser(username);
                }
                break;
        }
    }


    @Override
    public void loginUser(String user) {
        Call<loginResponseModel> call;
        call = endpoints.loginUser(user);
        call.enqueue(new Callback<loginResponseModel>() {

            @Override
            public void onResponse(Call<loginResponseModel> call, Response<loginResponseModel> response) {
                if(!response.body().getError()){
                    app.setUserId(response.body().getUser().get(0).getId());
                    Intent intent = new Intent(getActivity(), ListsActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<loginResponseModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
