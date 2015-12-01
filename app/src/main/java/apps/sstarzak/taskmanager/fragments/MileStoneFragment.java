package apps.sstarzak.taskmanager.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.Date;
import java.util.List;

import apps.sstarzak.taskmanager.R;
import apps.sstarzak.taskmanager.adapters.MileStoneAdapter;
import apps.sstarzak.taskmanager.globals.Globals;
import apps.sstarzak.taskmanager.helper.SimpleItemTouchHelperCallback;
import apps.sstarzak.taskmanager.parse.MileStone;

/**
 * Created by sstarzak on 30/11/2015.
 */
public class MileStoneFragment extends Fragment {


    private RecyclerView recList;
    private LinearLayoutManager llm;
    private ItemTouchHelper mItemTouchHelper;
    MileStoneAdapter mileStoneAdapter;
    FloatingActionsMenu menu;
    FloatingActionButton removeAction;
    FloatingActionButton addAction;

    public static MileStoneFragment newInstance(String task) {
        MileStoneFragment fragment = new MileStoneFragment();
        Bundle bundle = new Bundle();
        bundle.putString("task",task);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_milestone, container, false);

        recList = (RecyclerView) v.findViewById(R.id.milestone);
        llm = new LinearLayoutManager(getActivity());
        recList.setLayoutManager(llm);

        menu = (FloatingActionsMenu) v.findViewById(R.id.fab_tasks);

        ParseQuery<MileStone> query = ParseQuery.getQuery(MileStone.class);
        query.whereEqualTo("task", Globals.clicked_task);
        query.orderByAscending("sequence");
        query.findInBackground(new FindCallback<MileStone>() {
            @Override
            public void done(List<MileStone> objects, ParseException e) {
                for (MileStone m : objects) {
                    m.pinInBackground();
                }
                mileStoneAdapter = new MileStoneAdapter(objects);
                ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mileStoneAdapter);
                mItemTouchHelper = new ItemTouchHelper(callback);
                mItemTouchHelper.attachToRecyclerView(recList);

                recList.setAdapter(mileStoneAdapter);

                removeAction = (FloatingActionButton) v.findViewById(R.id.fab_del);
                removeAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ParseQuery<MileStone> del_query = ParseQuery.getQuery(MileStone.class);
                        del_query.whereEqualTo("status", 1);
                        del_query.fromLocalDatastore();
                        del_query.findInBackground(new FindCallback<MileStone>() {
                            @Override
                            public void done(List<MileStone> objects, ParseException e) {
                                for (final MileStone t : objects) {
                                    t.unpinInBackground(new DeleteCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            t.deleteEventually();
                                        }
                                    });
                                }
                                mileStoneAdapter.deleteItems(objects);

                                if (menu.isExpanded())
                                    menu.collapse();
                            }
                        });


                    }
                });

                addAction = (FloatingActionButton) v.findViewById(R.id.fab_add);
                addAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Adding new subtask...");
                        final EditText editText = new EditText(getActivity());
                        editText.setHint("Type your new subtask name");
                        editText.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.setView(editText);

                        if (menu.isExpanded())
                            menu.collapse();

                        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String name = editText.getText().toString();

                                if (name.isEmpty())
                                    return;

                                final MileStone mileStone = new MileStone();
                                mileStone.setTask(Globals.clicked_task);
                                mileStone.setDescription(name);
                                mileStone.setDate(new Date());
                                mileStone.setSequence(0);
                                mileStone.setStatus(0);

                                mileStone.saveEventually(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        mileStone.pinInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                mileStoneAdapter.addItem(mileStone);
                                            }
                                        });
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
                });


            }
        });


        return v;

    }
}
