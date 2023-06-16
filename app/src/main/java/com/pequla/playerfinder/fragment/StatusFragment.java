package com.pequla.playerfinder.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.pequla.playerfinder.AppUtils;
import com.pequla.playerfinder.DetailsActivity;
import com.pequla.playerfinder.R;
import com.pequla.playerfinder.adapter.DataAdapter;
import com.pequla.playerfinder.model.DataModel;
import com.pequla.playerfinder.model.status.PlayerData;
import com.pequla.playerfinder.model.status.PlayerModel;
import com.pequla.playerfinder.model.status.ServerStatus;
import com.pequla.playerfinder.service.DialogCallback;
import com.pequla.playerfinder.service.RestService;

import java.util.ArrayList;
import java.util.Objects;

public class StatusFragment extends Fragment implements AdapterView.OnItemClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView image = view.findViewById(R.id.server_icon);
        TextView online = view.findViewById(R.id.online);
        ListView listView = view.findViewById(R.id.status_player_list);

        RestService service = RestService.getInstance();
        service.getServerStatus(new DialogCallback(getActivity(), response -> {
            String json = Objects.requireNonNull(response.body()).string();
            final ServerStatus status = service.getMapper().readValue(json, ServerStatus.class);

            requireActivity().runOnUiThread(() -> {
                Glide.with(requireActivity())
                        .load(AppUtils.getServerIcon(getResources().getString(R.string.status_server_address)))
                        .into(image);

                PlayerData data = status.getPlayers();
                online.setText(String.format("Currently %s out of %s players online", data.getOnline(), data.getMax()));
            });

            // Inflate player list
            ArrayList<DataModel> models = new ArrayList<>();
            if (status.getPlayers().getOnline() > 0) {
                for (PlayerModel model : status.getPlayers().getSample())
                    service.getDataByUuid(model.getId().replaceAll("-", ""), new DialogCallback(getActivity(), (rsp) -> {
                        models.add(service.getMapper().readValue(rsp.body().string(), DataModel.class));

                        // Making sure all the data got converted async
                        if (models.size() == status.getPlayers().getSample().size()) {
                            requireActivity().runOnUiThread(() -> {
                                listView.setAdapter(new DataAdapter(requireActivity(), R.layout.player_list_row, models));
                                listView.setOnItemClickListener(this);
                            });
                        }
                    }));
            }
        }));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status, container, false);
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