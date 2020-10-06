package com.example.mad.ui.viewEvent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad.R;

import java.util.ArrayList;
import java.util.List;

public class scheduled_eventsFragment extends Fragment {

    View view;
    eventCardAdapter adapter;
    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_scheduled_events, container, false);

        List<eventData> list = new ArrayList<>();
        list.add(new eventData("Test Event",
                R.drawable.ic_baseline_cake_24,
                "24th October 2020"));
        list.add(new eventData("Test",
                R.drawable.ic_baseline_cake_24,
                "23rd Octoboer 2020"));
        list.add(new eventData("My Test ",
                R.drawable.ic_baseline_cake_24,
                "This is testing exam .."));

        recyclerView = view.findViewById(R.id.scheduled_event_viewer);
        adapter = new eventCardAdapter(list, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;

    }


}

