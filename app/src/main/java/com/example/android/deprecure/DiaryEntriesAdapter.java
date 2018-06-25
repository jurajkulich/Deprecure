package com.example.android.deprecure;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.deprecure.model.DiaryEntry;

import java.util.ArrayList;

/**
 * Created by juraj on 6/18/18.
 */

public class DiaryEntriesAdapter extends RecyclerView.Adapter<DiaryEntriesAdapter.ViewHolder> {

    public interface OnClickListener {
        void onItemClick(String activity);
    }

    private ArrayList<DiaryEntry> mDiaryEntries;
    private Context mContext;
    private final OnClickListener mOnClickListener;

    public DiaryEntriesAdapter(ArrayList<DiaryEntry> mDiaryEntries, OnClickListener onClickListener) {
        this.mDiaryEntries = mDiaryEntries;
        this.mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View itemView = layoutInflater.inflate(R.layout.item_diary_entry, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DiaryEntry diaryEntry = mDiaryEntries.get(position);
        Log.d("Adapter", diaryEntry.getTextEntry());
        holder.mEntryText.setText(diaryEntry.getTextEntry());
        holder.mEntryMood.setText(diaryEntry.getMood().getMoodName());
        String activities = "";
        if( diaryEntry.getActivityEntries() != null) {
            for (String activity : diaryEntry.getActivityEntries()) {
                activities += activity + " ";
            }
        }
        holder.mEntryActivities.setText(activities);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClickListener.onItemClick("");
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDiaryEntries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mEntryText;
        public TextView mEntryActivities;
        public TextView mEntryMood;

        public ViewHolder( View itemView ) {
            super(itemView);
            mEntryText = itemView.findViewById(R.id.entry_text);
            mEntryActivities = itemView.findViewById(R.id.entry_activities);
            mEntryMood = itemView.findViewById(R.id.entry_mood);
        }
    }


}
