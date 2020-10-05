package com.example.mad.ui.viewEvent;

import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad.R;

public class eventViewHolder extends RecyclerView.ViewHolder {

    ImageView mImageView;
    TextView mTitle;
    TextView mDate;


    public eventViewHolder(@NonNull View itemView) {
        super(itemView);

        this.mImageView = itemView.findViewById(R.id.typeImage);
        this.mTitle = itemView.findViewById(R.id.eventTitle);
        this.mDate = itemView.findViewById(R.id.eventDate);
    };

}
