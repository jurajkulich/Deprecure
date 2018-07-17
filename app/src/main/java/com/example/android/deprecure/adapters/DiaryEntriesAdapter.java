package com.example.android.deprecure.adapters;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.deprecure.R;
import com.example.android.deprecure.model.DiaryEntry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        // holder.mEntryMood.setText(diaryEntry.getMood().getMoodName());
        holder.mMoodSmile.setImageResource(diaryEntry.getMood().getMoodDrawableId());
        setSmileColor(holder, diaryEntry.getMood().getMoodName());
        holder.mEntryDate.setText(dateToString(diaryEntry.getEntryDate()));

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
        public TextView mEntryDate;
        public ImageView mMoodSmile;

        public ViewHolder( View itemView ) {
            super(itemView);
            mEntryText = itemView.findViewById(R.id.entry_text);
            mEntryActivities = itemView.findViewById(R.id.entry_activities);
            mEntryDate = itemView.findViewById(R.id.diary_entry_date);
            mMoodSmile = itemView.findViewById(R.id.diary_entry_smile);
        }
    }

    private void setSmileColor(ViewHolder holder, String mood) {
        switch(mood) {
            case "Happy": {
                holder.mMoodSmile.setColorFilter(ContextCompat.getColor(mContext, R.color.smile_level_0));
                break;
            }
            case "Smiling": {
                holder.mMoodSmile.setColorFilter(ContextCompat.getColor(mContext, R.color.smile_level_1));
                break;
            }
            case "Neutral": {
                holder.mMoodSmile.setColorFilter(ContextCompat.getColor(mContext, R.color.smile_level_2));
                break;
            }
            case "Sad": {
                holder.mMoodSmile.setColorFilter(ContextCompat.getColor(mContext, R.color.smile_level_3));
                break;
            }
            case "Angry": {
                holder.mMoodSmile.setColorFilter(ContextCompat.getColor(mContext, R.color.smile_level_4));
                break;
            }
            case "Crying": {
                holder.mMoodSmile.setColorFilter(ContextCompat.getColor(mContext, R.color.smile_level_5));
                break;
            }
            default: {
                holder.mMoodSmile.setColorFilter(ContextCompat.getColor(mContext, R.color.smile_level_0));
                break;
            }
        }
    }

    public void updateAdapter(ArrayList<DiaryEntry> mDiaryEntries) {
        this.mDiaryEntries = mDiaryEntries;
        notifyDataSetChanged();
    }

    public String dateToString(Date date) {
        DateFormat simpleDateFormat = new SimpleDateFormat("EEEE MM/dd/yyyy HH:mm");
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        calendar.add(Calendar.DATE, -1);
        Date yesterdayDate = calendar.getTime();
        String entryDate = simpleDateFormat.format(date);
        String today = simpleDateFormat.format(currentDate);
        String yesterday = simpleDateFormat.format(yesterdayDate);
        if( entryDate.substring(0, 14).equals(today.substring(0, 14))) {
            return "Today" + entryDate.substring(entryDate.length() - 6, entryDate.length());
        } else if(entryDate.substring(0, 14).equals(yesterday.substring(0, 14))) {
            return "Yesterday" + entryDate.substring(entryDate.length() - 6, entryDate.length());
        } else {
            return entryDate;
        }
    }
}
