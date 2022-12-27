package com.pequla.playerfinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.pequla.playerfinder.dialog.SaveDialog;
import com.pequla.playerfinder.model.DataModel;
import com.pequla.playerfinder.service.DialogCallback;
import com.pequla.playerfinder.service.RestService;

public class DetailsActivity extends AppCompatActivity {

    public static final String PLAYER_DATA_ID_KEY = "com.pequla.playerfinder.PLAYER_DATA_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Enabling the back/up button
        getSupportActionBar().setTitle(R.string.details_activity_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Loading dialog
        ProgressDialog loading = ProgressDialog.show(this, "Loading",
                "Please wait...", true);

        // Init components
        TextView mId = findViewById(R.id.details_id);
        TextView mDiscordId = findViewById(R.id.details_discord_id);
        TextView mGuildId = findViewById(R.id.details_guild_id);
        TextView mMinecraftId = findViewById(R.id.details_minecraft_id);
        ImageView mPlayerIcon = findViewById(R.id.details_player_icon);
        ImageView mDiscordIcon = findViewById(R.id.details_discord_icon);
        TextView mMinecraftName = findViewById(R.id.details_minecraft_name);
        TextView mDiscordName = findViewById(R.id.details_discord_name);
        TextView mCreatedAt = findViewById(R.id.details_created_at);
        ImageView mSkin = findViewById(R.id.details_player_skin);

        Button mBtnBrowser = findViewById(R.id.details_btn_web);
        Button mBtnDiscord = findViewById(R.id.details_btn_discord);
        Button mBtnFavourite = findViewById(R.id.details_btn_favourite);

        // Disabling buttons before the REST API response
        mBtnBrowser.setEnabled(false);
        mBtnDiscord.setEnabled(false);
        mBtnFavourite.setEnabled(false);

        // Fetching data
        int id = Integer.parseInt(getIntent().getExtras().getString(PLAYER_DATA_ID_KEY));
        RestService service = RestService.getInstance();
        service.getDataById(id, new DialogCallback(this, (response -> {
            String json = response.body().string();
            DataModel model = service.getMapper().readValue(json, DataModel.class);
            runOnUiThread(() -> {
                mId.setText(String.valueOf(model.getId()));
                mDiscordId.setText(model.getDiscordId());
                mGuildId.setText(model.getGuildId());
                mMinecraftId.setText(model.getUuid());
                Glide.with(this)
                        .load(AppUtils.getMinecraftIconUrl(model.getUuid()))
                        .into(mPlayerIcon);
                Glide.with(this).load(model.getAvatar()).into(mDiscordIcon);
                mDiscordName.setText(model.getTag());
                mMinecraftName.setText(model.getName());
                mCreatedAt.setText(AppUtils.formatDate(model.getCreatedAt()));
                Glide.with(this).load(AppUtils.getMinecraftSkinUrl(model.getUuid())).into(mSkin);

                // Enabling buttons
                mBtnBrowser.setEnabled(true);
                mBtnDiscord.setEnabled(true);
                mBtnFavourite.setEnabled(true);

                // Buttons
                mBtnBrowser.setOnClickListener((view -> startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse("https://datavue.pequla.com/user/" + model.getDiscordId()))
                )));
                mBtnDiscord.setOnClickListener((view -> startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse("https://discord.com/users/" + model.getDiscordId()))
                )));
                mBtnFavourite.setOnClickListener((view -> {
                    SaveDialog dialog = new SaveDialog(model);
                    dialog.show(getSupportFragmentManager(), "save_dialog");
                }));

                // Finished loading
                loading.hide();
            });
        })));
    }
}