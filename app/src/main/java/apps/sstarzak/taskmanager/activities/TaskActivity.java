package apps.sstarzak.taskmanager.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import apps.sstarzak.taskmanager.R;
import apps.sstarzak.taskmanager.fragments.MileStoneFragment;

public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("task"));
        setSupportActionBar(toolbar);

        setFragment(getIntent().getStringExtra("task"));

    }


    public void setFragment(String task) {
        MileStoneFragment fragment;
        fragment = MileStoneFragment.newInstance(task);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right);
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.commit();
    }

}
