package underdog.underlist;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import underdog.underlist.login_register.interfaces.login_register_interface;
import underdog.underlist.login_register.login.fragments.LoginFragment;
import underdog.underlist.login_register.register.Fragments.RegisterFragment;


public class MainActivity extends AppCompatActivity implements login_register_interface,
        View.OnClickListener{

    @Bind(R.id.background_iv)ImageView background_iv;
    @Bind(R.id.register_bt)Button register_bt;

    FragmentTransaction ft;
    //Flag that determines which view is being displayed
    boolean isLoginView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Glide.with(getApplicationContext()).load(R.drawable.mexico_city).centerCrop().into(background_iv);
        register_bt.setOnClickListener(this);
        isLoginView = true;
        showLoginFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_bt:
                if(isLoginView){
                    showRegisterFragment();
                }else{
                    showLoginFragment();
                }
                break;
        }
    }

    @Override
    public void showRegisterFragment() {
        // Begin the transaction
        ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.fragment_placeholder, new RegisterFragment());
        // Complete the changes added above
        ft.commitAllowingStateLoss();
        //Changin register button text
        register_bt.setText(getString(R.string.back));
        isLoginView = false;
    }

    @Override
    public void showLoginFragment() {
        // Begin the transaction
        ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.fragment_placeholder, new LoginFragment());
        // Complete the changes added above
        ft.commitAllowingStateLoss();
        //Changing register button text
        register_bt.setText(getString(R.string.register));
        isLoginView = true;
    }
}
