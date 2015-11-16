package apps.sstarzak.taskmanager.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by sstarzak on 10/11/2015.
 */
@ParseClassName("Task")
public class Task extends ParseObject {

    public String getName() {
        return getString("name");
    }
    public void setName(String name) {
        put("name",name);
    }

    public Number getPriority(){
        return getNumber("priority");
    }
    public void setPriority(Number number) {
        put("priority",number);
    }

    public String getDescription() {
        return getString("description");
    }
    public void setDescription(String description) {
        put("description",description);
    }

    public Date getDueTo() {
        return getDate("dueTo");
    }
    public void setDueTo(Date date) {
        put("dueTo",date);
    }

    public ParseObject getTaskList() {
        return getParseObject("taskList");
    }
    public void setTaskList(ParseObject parseObject) {
        put("taskList",parseObject);
    }

    public Number getStatus() {
        return getNumber("status");
    }
    public void setStatus(Number status) {
        put("status", status);
    }
}
