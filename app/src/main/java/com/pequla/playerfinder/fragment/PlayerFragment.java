package com.pequla.playerfinder.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pequla.playerfinder.R;
import com.pequla.playerfinder.adapter.DataAdapter;
import com.pequla.playerfinder.model.DataModel;
import com.pequla.playerfinder.model.DataPageModel;
import com.pequla.playerfinder.service.DialogCallback;
import com.pequla.playerfinder.service.RestService;

import java.util.ArrayList;

public class PlayerFragment extends Fragment {

    public PlayerFragment() {
        // Required empty public constructor
    }

    public static PlayerFragment newInstance() {
        return new PlayerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = view.findViewById(R.id.player_list);

        // Retrieve data
        RestService service = RestService.getInstance();
        service.getDataPaged(0, 12, new DialogCallback(getActivity(), response -> {
            String json = response.body().string();
            final ArrayList<DataModel> list = service.getMapper().readValue(json, DataPageModel.class).getContent();
            getActivity().runOnUiThread(() -> {
                // Create data adapter to form a list
                DataAdapter adapter = new DataAdapter(getActivity(), R.layout.player_list_row, list);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener((av, v, i, l) -> {
                    DataModel model = (DataModel) av.getItemAtPosition(i);
                    Toast.makeText(getContext(), model.getTag(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://discord.com/users/" + model.getDiscordId())));
                });
            });
        }));
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false);
    }
}