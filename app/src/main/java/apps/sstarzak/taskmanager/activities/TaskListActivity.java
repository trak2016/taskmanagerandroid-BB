package apps.sstarzak.taskmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import apps.sstarzak.taskmanager.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskListActivity extends AppCompatActivity {

    @OnClick(R.id.logout)
    public void logout() {
        ParseUser.getCurrentUser().logOut();
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

    @Bind(R.id.task_list)
    TextView task_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        ButterKnife.bind(this);

        ParseQuery<ParseObject> query = new ParseQuery("TaskList");
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                task_list.setText(ParseUser.getCurrentUser().getUsername() + "\n\n");

                for(ParseObject p : objects) {
                    task_list.append(p.getString("name") + "\n");
                }
            }
        });

        Log.d("Logged as", ParseUser.getCurrentUser().getUsername());
    }

}
