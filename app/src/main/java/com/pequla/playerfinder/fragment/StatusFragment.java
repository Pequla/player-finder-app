package com.pequla.playerfinder.fragment;

import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.pequla.playerfinder.R;
import com.pequla.playerfinder.adapter.PlayerAdapter;
import com.pequla.playerfinder.model.status.PlayerModel;
import com.pequla.playerfinder.model.status.StatusModel;
import com.pequla.playerfinder.service.DialogCallback;
import com.pequla.playerfinder.service.RestService;

import java.util.Objects;

public class StatusFragment extends Fragment {

    private static final String SERVER_ADDRESS = "server_address";
    private static final String SERVER_NAME = "server_name";
    private String mAddress;
    private String mName;

    public static StatusFragment newInstance(String address, String name) {
        StatusFragment fragment = new StatusFragment();
        Bundle args = new Bundle();
        args.putString(SERVER_ADDRESS, address);
        args.putString(SERVER_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAddress = getArguments().getString(SERVER_ADDRESS);
            mName = getArguments().getString(SERVER_NAME);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView image = view.findViewById(R.id.server_icon);
        TextView name = view.findViewById(R.id.server_name);
        TextView address = view.findViewById(R.id.server_address);
        TextView online = view.findViewById(R.id.online);
        TextView version = view.findViewById(R.id.version);
        ListView listView = view.findViewById(R.id.status_player_list);

        name.setText(mName);
        address.setText(mAddress);

        RestService service = RestService.getInstance();
        service.getServerStatus(mAddress, new DialogCallback(getActivity(), response -> {
            String json = Objects.requireNonNull(response.body()).string();
            final StatusModel model = service.getMapper().readValue(json, StatusModel.class);

            requireActivity().runOnUiThread(() -> {
                // Generating image from base64 string
                String[] rawBase64 = model.getFavicon().split(",");
                Glide.with(requireActivity())
                        .load(Base64.decode(rawBase64[1], Base64.DEFAULT))
                        .into(image);

                PlayerModel pm = model.getPlayers();
                online.setText(String.format("Currently %s out of %s players online", pm.getOnline(), pm.getMax()));
                version.setText(String.format("Version: %s", model.getVersion().getName()));

                // Inflate player list
                if (pm.getOnline() > 0) {
                    PlayerAdapter adapter = new PlayerAdapter(requireActivity(), R.layout.status_player_row, pm.getSample());
                    listView.setAdapter(adapter);
                }
            });
        }));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status, container, false);
    }
}