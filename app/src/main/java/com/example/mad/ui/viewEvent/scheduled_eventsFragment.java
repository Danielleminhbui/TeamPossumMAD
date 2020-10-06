package com.example.mad.ui.viewEvent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mad.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class scheduled_eventsFragment extends Fragment {

    private static final String TAG = "";
    private View view;
    private FirestoreRecyclerAdapter adapter;
    private String text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_scheduled_events, container, false);

        //getting user account uID so we can retrieve data under their uID


        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        RecyclerView firestoreList = view.findViewById(R.id.firestoreList);

        //Query

        Query query = firebaseFirestore.collection("events").
                whereEqualTo("user", userid);

        //Recycler options
        FirestoreRecyclerOptions<eventData> options = new FirestoreRecyclerOptions.Builder<eventData>()
                .setQuery(query, eventData.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<eventData, viewHolder>(options) {
            @NonNull
            @Override
            public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
                return new viewHolder(view);
            }


            @Override
            protected void onBindViewHolder(@NonNull viewHolder viewHolder, int i, @NonNull eventData model) {
                viewHolder.event_title.setText(model.getTitle());
                viewHolder.event_location.setText(model.getLocation());
                viewHolder.event_time.setText(model.getTime());
               // viewHolder.event_type.
            }
        };
        firestoreList.setHasFixedSize(true);
        firestoreList.setLayoutManager(new LinearLayoutManager(getContext()));
        firestoreList.setAdapter(adapter);


    }

    private class viewHolder extends RecyclerView.ViewHolder {
        private ImageView event_type;
        private TextView event_title;
        private TextView event_location;
        private TextView event_time;
        public viewHolder (@NonNull View itemView) {
            super(itemView);
            event_title = itemView.findViewById(R.id.eventTitlecard);
            event_location = itemView.findViewById(R.id.eventLocationcard);
            event_time = itemView.findViewById(R.id.eventTimecard);
           // event_type = itemView.findViewById(R.id.eventType);

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    /*
    private void showData()
    {
        recyclerView = view.findViewById(R.id.scheduled_event_viewer);
        adapter = new eventCardAdapter(list, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

    }
*/
}

