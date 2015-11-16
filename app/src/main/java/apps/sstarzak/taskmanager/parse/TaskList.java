package apps.sstarzak.taskmanager.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;


/**
 * Created by sstarzak on 10/11/2015.
 */
@ParseClassName("TaskList")
public class TaskList extends ParseObject {

    public String getName() {
        return getString("name");
    }
    public void setName(String name) {
        put("name",name);
    }
    public ParseUser getParseUser() {
        return getParseUser("user");
    }

    public void setParseUser(ParseUser parseUser) {
        put("user",parseUser);
    }
}

