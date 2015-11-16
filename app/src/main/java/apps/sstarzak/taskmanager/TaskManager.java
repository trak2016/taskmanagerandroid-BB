package apps.sstarzak.taskmanager;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;

import apps.sstarzak.taskmanager.parse.MileStone;
import apps.sstarzak.taskmanager.parse.Task;
import apps.sstarzak.taskmanager.parse.TaskList;

/**
 * Created by sstarzak on 02/11/2015.
 */
public class TaskManager extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(TaskList.class);
        ParseObject.registerSubclass(Task.class);
        ParseObject.registerSubclass(MileStone.class);
        Parse.initialize(this,
                "zG1XiepKQw4AYekUcSGKXePvP4dRjyr4S0ZtL7wV",
                "v3rjHqoICpjjfVSJJVOAC6G4okwxGykQ2JlH0H9l"
        );
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
