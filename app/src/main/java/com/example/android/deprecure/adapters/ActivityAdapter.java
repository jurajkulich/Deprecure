package com.example.android.deprecure.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.deprecure.R;

import java.util.ArrayList;

/**
 * Created by juraj on 6/18/18.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    public interface OnClickListener {
        void onItemClick(String activity);
    }

    private ArrayList<String> mActivities;
    private Context mContext;
    private final OnClickListener mOnClickListener;

    public ActivityAdapter(ArrayList<String> mActivities, OnClickListener onClickListener) {
        this.mActivities = mActivities;
        this.mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View itemView = layoutInflater.inflate(R.layout.item_activity, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String activity = mActivities.get(position);
        holder.mActivityName.setText(activity);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickListener.onItemClick(activity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mActivities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mActivityName;

        public ViewHolder( View itemView ) {
            super(itemView);
            mActivityName = itemView.findViewById(R.id.activity_name);
        }
    }


}
