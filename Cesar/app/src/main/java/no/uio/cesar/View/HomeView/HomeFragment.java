package no.uio.cesar.View.HomeView;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import no.uio.cesar.R;
import no.uio.cesar.View.MonitorActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ArrayList<Sensor> mDummyData;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        CardView cv = v.findViewById(R.id.monitor_button);

        cv.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), MonitorActivity.class));
        });

        /*mDummyData = new ArrayList<>();

        mDummyData.add(new Sensor("OarZpot"));
        mDummyData.add(new Sensor("Bitalino"));

        mRecyclerView = v.findViewById(R.id.available_sensors);

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DeviceAdapter(mDummyData);

        mRecyclerView.setAdapter(mAdapter);*/

        return v;
    }

}