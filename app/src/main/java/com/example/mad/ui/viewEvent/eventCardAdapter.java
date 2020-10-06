package com.example.mad;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

public class eventCardAdapter extends RecyclerView.Adapter<eventViewHolder> {

    List<eventData> list = Collections.emptyList();

    Context context;

    public eventCardAdapter(List<eventData> list, Context context)
    {
        this.list = list;
        this.context = context;
    }

    @Override
    public eventViewHolder
    onCreateViewHolder(ViewGroup parent,
                       int viewType)
    {

        View v;
        v = LayoutInflater.from(context).inflate(R.layout.event_card, parent, false);
        eventViewHolder e = new eventViewHolder(v);
        return e;


    }

    @Override
    public void
    onBindViewHolder(final eventViewHolder viewHolder,
                     final int position)
    {

        viewHolder.mImageView.setImageResource(list.get(position).getType());
        viewHolder.mTitle.setText(list.get(position).getTitle());
        viewHolder.mDate.setText(list.get(position).getDate());
    }



    @Override
    public int getItemCount()
    {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(
            RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
