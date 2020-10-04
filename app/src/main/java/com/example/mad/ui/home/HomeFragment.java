package com.example.mad.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad.R;
import com.example.mad.eventCardAdapter;
import com.example.mad.eventData;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    View view;
    List<eventData> list = new ArrayList<>();
    eventCardAdapter adapter;
    RecyclerView recyclerView;
    private HomeViewModel homeViewModel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.scheduled_event_viewer);
        adapter = new eventCardAdapter(list, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return view;
    }


    private List<eventData> getData()
    {
        List<eventData> list = new ArrayList<>();
        list.add(new eventData("Test", 1, "Test"));
        list.add(new eventData("Test2", 1, "Test"));

        return list;
    }


}

