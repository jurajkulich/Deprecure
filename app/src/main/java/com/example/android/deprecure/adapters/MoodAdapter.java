package com.example.android.deprecure.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.deprecure.R;
import com.example.android.deprecure.model.Mood;

import java.util.ArrayList;

/**
 * Created by juraj on 6/18/18.
 */

public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.ViewHolder> {

    private ViewHolder lastClicked = null;
    private int savedInstance;

    public interface OnClickListener {
        void onItemClick(Mood mood);
    }

    private ArrayList<Mood> mMoods;
    private Context mContext;
    private final OnClickListener mOnClickListener;

    public MoodAdapter(ArrayList<Mood> mMoods, OnClickListener onClickListener, int mood) {
        this.mMoods = mMoods;
        this.mOnClickListener = onClickListener;
        this.savedInstance = mood;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View itemView = layoutInflater.inflate(R.layout.item_mood, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Mood mood = mMoods.get(position);
        holder.mMoodName.setText(mood.getMoodName());
        holder.mMoodSmile.setImageDrawable(mContext.getDrawable(mood.getMoodDrawableId()));

        if( savedInstance != -1 && position == savedInstance) {
            holder.mMoodSmile.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent));
            holder.mMoodName.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
            mOnClickListener.onItemClick(mood);
            lastClicked = holder;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickListener.onItemClick(mood);
                if( lastClicked != null) {
                    lastClicked.mMoodSmile.clearColorFilter();
                    lastClicked.mMoodName.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                }
                holder.mMoodSmile.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent));
                holder.mMoodName.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                lastClicked = holder;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMoods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mMoodName;
        public ImageView mMoodSmile;

        public ViewHolder( View itemView ) {
            super(itemView);
            mMoodName = itemView.findViewById(R.id.mood_name);
            mMoodSmile = itemView.findViewById(R.id.mood_smile);
        }
    }


}
