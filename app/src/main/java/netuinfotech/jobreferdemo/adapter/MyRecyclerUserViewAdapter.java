package netuinfotech.jobreferdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import netuinfotech.jobreferdemo.R;
import netuinfotech.jobreferdemo.model.User;

public class MyRecyclerUserViewAdapter extends RecyclerView.Adapter<MyRecyclerUserViewAdapter.DataObjectHolder> {

    private static String LOG_TAG = "MyRecyclerUserViewAdapter";
    private static ArrayList<User> mDataset;
    public static ArrayList<User> arraylist;
    private static MyClickListener myClickListener;
    static Context context;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        TextView txtName, txtTechnology;

        public DataObjectHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.name);
            txtTechnology= (TextView) itemView.findViewById(R.id.tech);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

//            Intent ob = new Intent(context, JobDetail.class);
//
//            ob.putExtra("title", mDataset.get(getAdapterPosition()).getName());
//            ob.putExtra("company", mDataset.get(getAdapterPosition()).getQualification());
//
//            context.startActivity(ob);

//            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
//        this.myClickListener = myClickListener;
    }

    public MyRecyclerUserViewAdapter(ArrayList<User> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
        arraylist = new ArrayList<>();
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_cardview, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        holder.txtName.setText(mDataset.get(position).getName());

        if(mDataset.get(position).getQualification().equals("0"))
            holder.txtTechnology.setText("BCA");
        else if(mDataset.get(position).getQualification().equals("1"))
            holder.txtTechnology.setText("Bsc IT");
        else if(mDataset.get(position).getQualification().equals("2"))
            holder.txtTechnology.setText("MCA");
        else
            holder.txtTechnology.setText("");

//        holder.txtTechnology.setText(mDataset.get(position).getQualification());
    }

    public void addItem(User dataObj, int index) {
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
            for (User wp : arraylist) {
                Log.d("templename", wp.getName());
                if (wp.getName().toLowerCase(Locale.getDefault())
                        .startsWith(charText)) {
                    mDataset.add(wp);
                }
            }
        }
    }

}