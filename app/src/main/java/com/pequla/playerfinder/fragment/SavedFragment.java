package com.pequla.playerfinder.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pequla.playerfinder.DetailsActivity;
import com.pequla.playerfinder.R;
import com.pequla.playerfinder.adapter.DataAdapter;
import com.pequla.playerfinder.model.DataModel;
import com.pequla.playerfinder.service.DialogCallback;
import com.pequla.playerfinder.service.FileService;
import com.pequla.playerfinder.service.RestService;

import java.util.ArrayList;

public class SavedFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static SavedFragment newInstance() {
        return new SavedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView mSavedList = view.findViewById(R.id.saved_list);
        ArrayList<Integer> favourites = FileService.loadFavourites();
        ArrayList<DataModel> models = new ArrayList<>();
        RestService service = RestService.getInstance();

        // Retrieving data
        if (favourites == null) return;
        for (Integer id : favourites)
            service.getDataById(id, new DialogCallback(getActivity(), (response) -> {
                models.add(service.getMapper().readValue(response.body().string(), DataModel.class));
                if (models.size() == favourites.size()) {
                    getActivity().runOnUiThread(() -> {
                        DataAdapter adapter = new DataAdapter(getActivity(), R.layout.player_list_row, models);
                        mSavedList.setAdapter(adapter);
                        mSavedList.setOnItemClickListener(this);
                    });
                }
            }));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // Open player details
        DataModel model = (DataModel) adapterView.getItemAtPosition(i);
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.PLAYER_DATA_ID_KEY, String.valueOf(model.getId()));
        startActivity(intent);
    }
}