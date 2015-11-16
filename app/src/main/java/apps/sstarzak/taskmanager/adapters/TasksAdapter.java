package apps.sstarzak.taskmanager.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import apps.sstarzak.taskmanager.R;
import apps.sstarzak.taskmanager.parse.Task;

/**
 * Created by sstarzak on 16/11/2015.
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {

    private List<Task> tasks;

    public TasksAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View item = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_row_item,parent,false);
        return new TaskViewHolder(item);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.priority.setText(String.valueOf(tasks.get(position).getPriority()));
        holder.name.setText(tasks.get(position).getName());
        holder.desc.setText(tasks.get(position).getDescription());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yy");


//        holder.due_to.setText(simpleDateFormat.format(tasks.get(position).getDueTo()));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        public TextView priority;
        public TextView name;
        public TextView desc;
        public TextView due_to;

        public TaskViewHolder(View itemView) {
            super(itemView);
            priority = (TextView) itemView.findViewById(R.id.priority);
            name = (TextView) itemView.findViewById(R.id.name);
            desc = (TextView) itemView.findViewById(R.id.desc);
            due_to = (TextView) itemView.findViewById(R.id.dueTo);

        }
    }
}
