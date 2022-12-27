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
import com.pequla.playerfinder.model.DataModel;

import java.util.ArrayList;

public class DataAdapter extends ArrayAdapter<DataModel> {

    private Context mContext;
    private int mResource;

    public DataAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DataModel> objects) {
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

        ImageView playerImage = convertView.findViewById(R.id.player_image);
        ImageView discordImage = convertView.findViewById(R.id.discord_image);
        TextView playerName = convertView.findViewById(R.id.player_name);
        TextView discordName = convertView.findViewById(R.id.discord_name);

        DataModel model = getItem(position);
        Glide.with(mContext)
                .load(AppUtils.getMinecraftIconUrl(model.getUuid()))
                .into(playerImage);
        Glide.with(mContext).load(model.getAvatar()).into(discordImage);
        playerName.setText(getItem(position).getName());
        discordName.setText(getItem(position).getTag());

        return convertView;
    }
}
