package apps.sstarzak.taskmanager.adapters;

import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Collections;
import java.util.List;

import apps.sstarzak.taskmanager.R;
import apps.sstarzak.taskmanager.helper.ItemTouchHelperAdapter;
import apps.sstarzak.taskmanager.helper.ItemTouchHelperViewHolder;
import apps.sstarzak.taskmanager.helper.OnStartDragListener;
import apps.sstarzak.taskmanager.parse.Task;

/**
 * Created by sstarzak on 16/11/2015.
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> implements ItemTouchHelperAdapter {

    public static final float ALPHA_FULL = 1.0f;
    private List<Task> tasks;
    private OnStartDragListener mDragStartListener;

    public TasksAdapter(List<Task> tasks) {
        this.tasks = tasks;
        this.mDragStartListener = new OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {

            }
        };
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View item = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_row_item, parent, false);

        return new TaskViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder holder, int position) {

        switch (tasks.get(position).getPriority().intValue()) {
            case 0:
                holder.priority.setImageResource(R.drawable.green_dot);
                break;
            case 1:
                holder.priority.setImageResource(R.drawable.yellow_dot);
                break;
            case 2:
                holder.priority.setImageResource(R.drawable.orange_dot);
                break;
            case 3:
                holder.priority.setImageResource(R.drawable.red_dot);
                break;
            case 4:
                holder.priority.setImageResource(R.drawable.black_dot);
                break;
        }
        holder.name.setText(tasks.get(position).getName());
        holder.desc.setText(tasks.get(position).getDescription());

        //TODO: fix date

        switch (tasks.get(position).getStatus().intValue()) {
            case 1:
                holder.ll_line.setBackgroundResource(R.drawable.diagonal_line);
                break;
            case 0:
                holder.ll_line.setBackgroundColor((int) ALPHA_FULL);
                break;
            default:
                holder.ll_line.setBackgroundColor((int) ALPHA_FULL);
                break;
        }

        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    @Override
    public void onItemSwipe(int position) {

        if (tasks.get(position).getStatus().equals(1)) {
            tasks.get(position).setStatus(0);
        } else {
            tasks.get(position).setStatus(1);
        }

        ParseQuery<Task> query = ParseQuery.getQuery(Task.class);
        query.fromLocalDatastore();
        ParseObject parseObject = null;
        try {
            parseObject = query.get(tasks.get(position).getObjectId());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        parseObject.put("status", tasks.get(position).getStatus());
        parseObject.saveInBackground();

        notifyDataSetChanged();
    }

    public void deleteItems(List<Task> list) {
        for (Task t : list) {
            tasks.remove(t);
        }
        notifyDataSetChanged();
    }

    public void addItem(Task t) {
        tasks.add(t);
        notifyDataSetChanged();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(tasks, fromPosition, toPosition);

        Integer priority_swap_helper;
        priority_swap_helper = tasks.get(fromPosition).getPriority().intValue();
        tasks.get(fromPosition).setPriority(tasks.get(toPosition).getPriority().intValue());
        tasks.get(toPosition).setPriority(priority_swap_helper);

        ParseQuery<Task> query = ParseQuery.getQuery(Task.class);
        query.fromLocalDatastore();
        try {
            ParseObject parseObject = query.get(tasks.get(fromPosition).getObjectId());
            parseObject.put("priority", tasks.get(fromPosition).getPriority());
            parseObject.saveInBackground();

            parseObject = query.get(tasks.get(toPosition).getObjectId());
            parseObject.put("priority", tasks.get(toPosition).getPriority());
            parseObject.saveInBackground();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        notifyItemMoved(fromPosition, toPosition);

        return true;
    }


    public class TaskViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        public ImageView priority;
        public TextView name;
        public TextView desc;
        public TextView due_to;
        public LinearLayout ll_line;

        public TaskViewHolder(View itemView) {
            super(itemView);
            priority = (ImageView) itemView.findViewById(R.id.priority);
            name = (TextView) itemView.findViewById(R.id.name);
            desc = (TextView) itemView.findViewById(R.id.desc);
            due_to = (TextView) itemView.findViewById(R.id.dueTo);
            ll_line = (LinearLayout) itemView.findViewById(R.id.ll_line);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
