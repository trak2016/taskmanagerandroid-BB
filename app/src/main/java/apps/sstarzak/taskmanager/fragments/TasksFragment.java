package apps.sstarzak.taskmanager.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import apps.sstarzak.taskmanager.R;
import apps.sstarzak.taskmanager.adapters.TasksAdapter;
import apps.sstarzak.taskmanager.globals.Globals;
import apps.sstarzak.taskmanager.helper.ItemClickSupport;
import apps.sstarzak.taskmanager.helper.SimpleItemTouchHelperCallback;
import apps.sstarzak.taskmanager.parse.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment {

    private RecyclerView recList;

    private LinearLayoutManager llm;

    private ItemTouchHelper mItemTouchHelper;

    FloatingActionsMenu menu;

    FloatingActionButton removeAction;

    FloatingActionButton addAction;

    public TasksFragment() {
        // Required empty public constructor
    }

    public static TasksFragment newInstance(int position) {
        TasksFragment tasksFragment = new TasksFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        tasksFragment.setArguments(bundle);
        return tasksFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View v = inflater.inflate(R.layout.fragment_tasks, container, false);

        recList = (RecyclerView) v.findViewById(R.id.tasks);
        llm = new LinearLayoutManager(getActivity());
        recList.setLayoutManager(llm);
        recList.setHasFixedSize(true);

        ParseQuery<Task> selected = ParseQuery.getQuery(Task.class);
        selected.whereEqualTo("taskList", Globals.task_lists.get(getArguments().getInt("position")));
        selected.addDescendingOrder("priority");

        selected.findInBackground(new FindCallback<Task>() {
            @Override
            public void done(final List<Task> objects, ParseException e) {

                for (Task t : objects) {
                    t.pinInBackground();
                }

                ItemClickSupport.addTo(recList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Log.d("position", objects.get(position).getName());
                    }
                });
                final TasksAdapter tasksAdapter = new TasksAdapter(objects);

                ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(tasksAdapter);
                mItemTouchHelper = new ItemTouchHelper(callback);
                mItemTouchHelper.attachToRecyclerView(recList);

                recList.setAdapter(tasksAdapter);

                menu = (FloatingActionsMenu) v.findViewById(R.id.fab_tasks);

                removeAction = (FloatingActionButton) v.findViewById(R.id.fab_del);
                removeAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ParseQuery<Task> del_query = ParseQuery.getQuery(Task.class);
                        del_query.whereEqualTo("status", 1);
                        del_query.fromLocalDatastore();
                        del_query.findInBackground(new FindCallback<Task>() {
                            @Override
                            public void done(List<Task> objects, ParseException e) {
                                for (Task t : objects) {
                                    t.deleteInBackground();
                                }
                                tasksAdapter.deleteItems(objects);

                                if(menu.isExpanded())
                                    menu.collapse();
                            }
                        });
                    }
                });

                addAction = (FloatingActionButton) v.findViewById(R.id.fab_add);
                addAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(menu.isExpanded())
                            menu.collapse();
                    }
                });
            }
        });


        return v;
    }


}
