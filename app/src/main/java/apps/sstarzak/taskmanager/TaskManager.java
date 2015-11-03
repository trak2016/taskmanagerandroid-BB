package apps.sstarzak.taskmanager;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;

/**
 * Created by sstarzak on 02/11/2015.
 */
public class TaskManager extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this,
                "zG1XiepKQw4AYekUcSGKXePvP4dRjyr4S0ZtL7wV",
                "v3rjHqoICpjjfVSJJVOAC6G4okwxGykQ2JlH0H9l"
        );
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
