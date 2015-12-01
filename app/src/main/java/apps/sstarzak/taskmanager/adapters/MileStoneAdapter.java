package apps.sstarzak.taskmanager.adapters;

import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import apps.sstarzak.taskmanager.parse.MileStone;

/**
 * Created by sstarzak on 30/11/2015.
 */
public class MileStoneAdapter extends RecyclerView.Adapter<MileStoneAdapter.MileStoneViewHolder> implements ItemTouchHelperAdapter {

    public static final float ALPHA_FULL = 1.0f;
    private List<MileStone> mileStones;
    private OnStartDragListener mDragStartListener;

    public MileStoneAdapter(List<MileStone> mileStones) {
        this.mileStones = mileStones;
        this.mDragStartListener = new OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {

            }
        };
    }

    @Override
    public MileStoneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.mile_list_row_item, parent, false);

        return new MileStoneViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final MileStoneViewHolder holder, int position) {

        holder.desc.setText(mileStones.get(position).getDescription());

//        Date date = mileStones.get(position).getDate("dueTo");
//        Calendar c = Calendar.getInstance();
//        c.setTime(date);
//
//        holder.due_to.setText(
//                c.get(Calendar.DAY_OF_MONTH) + "-" +
//                        (c.get(Calendar.MONTH) + 1) + "-" +
//                        c.get(Calendar.YEAR)
//        );

        switch (mileStones.get(position).getStatus().intValue()) {
            case 1:
                holder.ll_line.setBackgroundResource(R.drawable.diagonal_line2);
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
       return mileStones.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mileStones, fromPosition, toPosition);

        Integer sequence_swap;
        sequence_swap = mileStones.get(fromPosition).getSequence().intValue();
        mileStones.get(fromPosition).setSequence(mileStones.get(toPosition).getSequence().intValue());
        mileStones.get(toPosition).setSequence(sequence_swap);

        ParseQuery<MileStone> query = ParseQuery.getQuery(MileStone.class);
        query.fromLocalDatastore();
        try {
            ParseObject parseObject = query.get(mileStones.get(fromPosition).getObjectId());
            parseObject.put("sequence", mileStones.get(fromPosition).getSequence());
            parseObject.saveInBackground();

            parseObject = query.get(mileStones.get(toPosition).getObjectId());
            parseObject.put("sequence", mileStones.get(toPosition).getSequence());
            parseObject.saveInBackground();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        notifyItemMoved(fromPosition, toPosition);

        return true;
    }

    @Override
    public void onItemSwipe(int position) {

        if (mileStones.get(position).getStatus().equals(1)) {
            mileStones.get(position).setStatus(0);
        } else {
            mileStones.get(position).setStatus(1);
        }

        ParseQuery<MileStone> query = ParseQuery.getQuery(MileStone.class);
        query.fromLocalDatastore();
        ParseObject parseObject = null;
        try {
            parseObject = query.get(mileStones.get(position).getObjectId());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        parseObject.put("status", mileStones.get(position).getStatus());
        parseObject.saveEventually();

        notifyDataSetChanged();
    }


    public void deleteItems(List<MileStone> list) {
        for (MileStone t : list) {
            mileStones.remove(t);
        }
        notifyDataSetChanged();
    }

    public void addItem(MileStone t) {
        mileStones.add(t);
        notifyDataSetChanged();
    }

    public class MileStoneViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        public TextView desc;
        public TextView due_to;
        public LinearLayout ll_line;


        public MileStoneViewHolder(View itemView) {
            super(itemView);
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
