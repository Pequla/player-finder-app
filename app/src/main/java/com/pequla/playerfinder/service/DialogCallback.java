package com.pequla.playerfinder.service;

import android.app.Activity;
import android.app.AlertDialog;

import androidx.annotation.NonNull;

import com.pequla.playerfinder.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DialogCallback implements Callback {

    private final Activity mActivity;
    private final ResponseHandler handler;

    public DialogCallback(Activity mActivity, ResponseHandler handler) {
        this.mActivity = mActivity;
        this.handler = handler;
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        mActivity.runOnUiThread(() -> new AlertDialog.Builder(mActivity)
                .setTitle(e.getClass().getSimpleName())
                .setMessage(e.getMessage())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.dialog_exit_app, (di, i) -> mActivity.finish())
                .show());
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        handler.onResponse(response);
    }

    public interface ResponseHandler {
        void onResponse(Response response) throws IOException;
    }
}
