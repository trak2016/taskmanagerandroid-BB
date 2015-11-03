package apps.sstarzak.taskmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.parse.ParseUser;

import apps.sstarzak.taskmanager.R;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ParseUser parseUser = ParseUser.getCurrentUser();

        if(parseUser == null) {
            startActivity(new Intent(this,UserActivity.class));
            this.finish();
        } else {
            startActivity(new Intent(this, TaskListActivity.class));
            this.finish();
        }
    }
}
