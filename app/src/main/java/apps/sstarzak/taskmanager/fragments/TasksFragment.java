package apps.sstarzak.taskmanager.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import apps.sstarzak.taskmanager.R;
import apps.sstarzak.taskmanager.adapters.TasksAdapter;
import apps.sstarzak.taskmanager.globals.Globals;
import apps.sstarzak.taskmanager.parse.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment {

    RecyclerView recList;

    public TasksFragment() {
        // Required empty public constructor
    }

    public static TasksFragment newInstance(int position){
        TasksFragment tasksFragment = new TasksFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        tasksFragment.setArguments(bundle);
        return tasksFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_tasks, container, false);


        recList = (RecyclerView) v.findViewById(R.id.tasks);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        recList.setHasFixedSize(true);


        ParseQuery<Task> selected = ParseQuery.getQuery(Task.class);
        selected.whereEqualTo("taskList", Globals.task_lists.get(getArguments().getInt("position")));
        selected.findInBackground(new FindCallback<Task>() {
            @Override
            public void done(List<Task> objects, ParseException e) {
                for (Task t : objects) {
                    Log.d("qqq",t.getName());
                    recList.setAdapter(new TasksAdapter(objects));
                }
            }
        });


        return v ;
    }


}
