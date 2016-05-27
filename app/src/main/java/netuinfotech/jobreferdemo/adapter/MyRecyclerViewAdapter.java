package netuinfotech.jobreferdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import netuinfotech.jobreferdemo.R;
import netuinfotech.jobreferdemo.activity.JobDetail;
import netuinfotech.jobreferdemo.model.Job;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder> {

    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static ArrayList<Job> mDataset;
    public static ArrayList<Job> arraylist;
    private static MyClickListener myClickListener;
    static Context context;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        TextView txtTitle, txtCompany, txtDate, txtAddress, txtDescription;

        public DataObjectHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.title);
            txtDate= (TextView) itemView.findViewById(R.id.date);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent ob = new Intent(context, JobDetail.class);

            ob.putExtra("title", mDataset.get(getAdapterPosition()).getTitle());
            ob.putExtra("company", mDataset.get(getAdapterPosition()).getName());
            ob.putExtra("date", mDataset.get(getAdapterPosition()).getDate());
            ob.putExtra("address", mDataset.get(getAdapterPosition()).getAddress());
            ob.putExtra("description", mDataset.get(getAdapterPosition()).getDescription());
            ob.putExtra("salarytype", mDataset.get(getAdapterPosition()).getSalarytype());

            context.startActivity(ob);

//            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
//        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapter(ArrayList<Job> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
        arraylist = new ArrayList<>();
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.txtTitle.setText(mDataset.get(position).getTitle());
        holder.txtDate.setText(mDataset.get(position).getDate());
    }

    public void addItem(Job dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    // Filter Class
    public static void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mDataset.clear();

        if (charText.length() == 0) {
            mDataset.addAll(arraylist);
        } else {
            for (Job wp : arraylist) {
                Log.d("templename", wp.getTitle());
                if (wp.getTitle().toLowerCase(Locale.getDefault())
                        .startsWith(charText)) {
                    mDataset.add(wp);
                }
            }
        }
    }

}