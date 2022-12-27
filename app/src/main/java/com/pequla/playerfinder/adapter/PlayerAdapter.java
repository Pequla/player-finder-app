package com.pequla.playerfinder.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.pequla.playerfinder.AppUtils;
import com.pequla.playerfinder.R;
import com.pequla.playerfinder.model.status.PlayerDataModel;

import java.util.ArrayList;

public class PlayerAdapter extends ArrayAdapter<PlayerDataModel> {

    private final Context mContext;
    private final int mResource;

    public PlayerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<PlayerDataModel> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @SuppressLint("ViewHolder") // seams like you cant have uncontrolled inflation.. smh
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        ImageView playerImage = convertView.findViewById(R.id.status_player_icon);
        TextView playerName = convertView.findViewById(R.id.status_player_name);

        PlayerDataModel model = getItem(position);
        Glide.with(mContext)
                .load(AppUtils.getMinecraftIconUrl(model.getId()))
                .into(playerImage);
        playerName.setText(getItem(position).getName());

        return convertView;
    }
}
