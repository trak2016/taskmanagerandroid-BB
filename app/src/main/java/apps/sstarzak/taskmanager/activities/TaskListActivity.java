package apps.sstarzak.taskmanager.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import apps.sstarzak.taskmanager.R;
import apps.sstarzak.taskmanager.fragments.FragmentDrawer;
import apps.sstarzak.taskmanager.fragments.TasksFragment;
import apps.sstarzak.taskmanager.globals.Globals;
import apps.sstarzak.taskmanager.models.NavDrawerItem;
import apps.sstarzak.taskmanager.parse.TaskList;
import butterknife.ButterKnife;

public class TaskListActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        ButterKnife.bind(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Select list..");

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            ParseUser.getCurrentUser().logOut();
            startActivity(new Intent(this, MainActivity.class));
            this.finish();
            return true;
        }

        if (id == R.id.action_delete_list) {
            if (position != -1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Deleting list: " + Globals.task_lists.get(position).getName());

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Globals.task_lists.get(position).deleteInBackground(new DeleteCallback() {
                            @Override
                            public void done(ParseException e) {
                                Globals.task_lists.remove(position);
                                drawerFragment.adapter.delete(position);
                                setEmptyFragment();
                                position = -1;
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        }

        if (id == R.id.action_add_list) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Adding new list...");
            final EditText editText = new EditText(this);
            editText.setHint("Type your new list name");
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(editText);

            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final String name = editText.getText().toString();

                    if (name.isEmpty())
                        return;

                    final TaskList taskList = new TaskList();
                    taskList.setParseUser(ParseUser.getCurrentUser());
                    taskList.setName(name);
                    taskList.saveEventually(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Globals.task_lists.add(taskList);
                            NavDrawerItem navDrawerItem = new NavDrawerItem();
                            navDrawerItem.setTitle(name);
                            drawerFragment.adapter.add(navDrawerItem);
                        }
                    });
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_list, menu);
        return true;
    }

    public void setEmptyFragment() {

        getSupportActionBar().setTitle("Select list..");

        TasksFragment fragment = null;

        fragment = TasksFragment.newInstance(-1);

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right);
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

        getSupportActionBar().setTitle(Globals.task_lists.get(position).getName());

        TasksFragment fragment = null;

        switch (position) {
            default:
                fragment = TasksFragment.newInstance(position);
        }

        this.position = position;

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right);
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
        }

    }
}
