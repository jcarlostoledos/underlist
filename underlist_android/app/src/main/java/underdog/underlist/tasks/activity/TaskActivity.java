package underdog.underlist.tasks.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import underdog.underlist.R;
import underdog.underlist.tasks.fragment.TaskFragment;
import underdog.underlist.tasks.interfaces.TaskInterface;

public class TaskActivity extends AppCompatActivity implements TaskInterface {

    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        showTaskFragment();
    }

    @Override
    public void showTaskFragment() {
        // Begin the transaction
        ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.fragment_placeholder, new TaskFragment());
        // Complete the changes added above
        ft.commitAllowingStateLoss();
    }
}
