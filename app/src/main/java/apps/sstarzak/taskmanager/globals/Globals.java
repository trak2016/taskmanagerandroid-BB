package apps.sstarzak.taskmanager.globals;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import apps.sstarzak.taskmanager.parse.Task;

/**
 * Created by sstarzak on 16/11/2015.
 */
public class Globals {
    public static List<ParseObject> task_lists = new ArrayList<>();
    public static List<Task> task = new ArrayList<>();
    public static List<ParseObject> mile_stones = new ArrayList<>();

}
