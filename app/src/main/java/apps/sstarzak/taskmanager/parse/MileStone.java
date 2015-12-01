package apps.sstarzak.taskmanager.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by sstarzak on 10/11/2015.
 */
@ParseClassName("MileStone")
public class MileStone extends ParseObject {

    public String getDescription() {
        return getString("description");
    }

    public void setDescription(String description) {
        put("description",description);
    }

    public Number getStatus() {
        return getNumber("status");
    }
    public void setStatus(Number number) {
        put("status",number);
    }

    public ParseObject getTask() {
        return getParseObject("task");
    }

    public void setTask(ParseObject parseObject){
        put("task",parseObject);
    }

    public Number getSequence() {
        return getNumber("sequence");
    }

    public void setSequence(Number sequence) {
        put("sequence",sequence);
    }

    public Date getDueTo() {
        return getDate("dueTo");
    }

    public void setDate(Date date) {
        put("dueTo",date);
    }

}
