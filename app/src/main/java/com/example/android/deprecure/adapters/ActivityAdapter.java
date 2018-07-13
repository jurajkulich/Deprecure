package com.example.android.deprecure.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.deprecure.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by juraj on 6/18/18.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private HashMap<Integer, ViewHolder> mHolderHashMap;
    private ArrayList<Integer> mSavedActivities;

    public interface OnClickListener {
        void onItemClick(String activity, int position);
    }

    private ArrayList<String> mActivities;
    private Context mContext;
    private final OnClickListener mOnClickListener;

    public ActivityAdapter(ArrayList<String> mActivities, OnClickListener onClickListener, ArrayList<Integer> savedActivities) {
        this.mActivities = mActivities;
        this.mOnClickListener = onClickListener;
        this.mSavedActivities = savedActivities;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final int colorAccent = ContextCompat.getColor(mContext, R.color.colorAccent);
        final int colorPrimary = ContextCompat.getColor(mContext, R.color.colorPrimary);

        if( mHolderHashMap == null) {
            mHolderHashMap = new HashMap<>();
        }

        final String activity = mActivities.get(position);
        holder.mActivityName.setText(activity);

        if(mSavedActivities != null && mSavedActivities.contains(position)) {
            mHolderHashMap.put(position, holder);
            holder.mActivityName.setTextColor(colorAccent);
            mOnClickListener.onItemClick(activity, position);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickListener.onItemClick(activity, position);

                if( mHolderHashMap.containsKey(position)) {
                    mHolderHashMap.get(position).mActivityName.setTextColor(colorPrimary);
                    mHolderHashMap.remove(position);
                } else {
                    mHolderHashMap.put(position, holder);
                    holder.mActivityName.setTextColor(colorAccent);
                }
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
