package apps.sstarzak.taskmanager.globals;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

import apps.sstarzak.taskmanager.parse.Task;
import apps.sstarzak.taskmanager.parse.TaskList;

/**
 * Created by sstarzak on 16/11/2015.
 */
public class Globals {
    public static List<TaskList> task_lists = new ArrayList<>();
    public static List<Task> task = new ArrayList<>();
    public static List<ParseObject> mile_stones = new ArrayList<>();

    public static Task task_added = null;

    public static Task clicked_task = null;

}
