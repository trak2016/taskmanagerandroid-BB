package apps.sstarzak.taskmanager.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Date;

import apps.sstarzak.taskmanager.R;
import apps.sstarzak.taskmanager.globals.Globals;
import apps.sstarzak.taskmanager.parse.Task;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sstarzak on 19/11/2015.
 */
public class NewTaskActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    @Bind(R.id.et_task_name)
    EditText task_name;

    @Bind(R.id.et_task_desc)
    EditText task_desc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_layout);

        ButterKnife.bind(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(ParseUser.getCurrentUser().getUsername() + "'s new task");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_task) {
            Task task = new Task();
            task.setName(task_name.getText().toString());
            task.setDescription(task_desc.getText().toString());
            task.setPriority(2);
            task.setStatus(0);
            task.setDueTo(new Date());
            task.setTaskList(Globals.task_lists.get(getIntent().getIntExtra("position", 2)));
            task.saveEventually(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    NewTaskActivity.this.finish();
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
