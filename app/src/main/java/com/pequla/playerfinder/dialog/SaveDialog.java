package com.pequla.playerfinder.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.pequla.playerfinder.model.DataModel;
import com.pequla.playerfinder.service.FileService;

import java.util.ArrayList;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SaveDialog extends AppCompatDialogFragment {

    private final DataModel model;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder sb = new AlertDialog.Builder(getActivity());
        sb.setTitle("Add to Favourites")
                .setMessage(String.format("Do you want to add %s (%s) to your favorites", model.getName(), model.getTag()))
                .setPositiveButton("Yes", this::onClickSave)
                .setNegativeButton("No", this::onClickDismiss);
        AlertDialog mSaveDialog = sb.create();

        AlertDialog.Builder rb = new AlertDialog.Builder(getActivity());
        rb.setTitle("Player already in Favourites")
                .setMessage(String.format("Do you want to remove %s (%s) from your favorites", model.getName(), model.getTag()))
                .setPositiveButton("Yes", this::onClickRemove)
                .setNegativeButton("No", this::onClickDismiss);
        AlertDialog mRemoveDialog = rb.create();

        // Decide what dialog to display
        return (alreadyExists()) ? mRemoveDialog : mSaveDialog;
    }

    public void onClickSave(DialogInterface dialogInterface, int i) {
        Toast.makeText(getActivity(), "Saving user", Toast.LENGTH_SHORT).show();
        ArrayList<Integer> favourites = FileService.loadFavourites();
        if (favourites == null) {
            Toast.makeText(getActivity(), "Failed to read favourites", Toast.LENGTH_SHORT).show();
            return;
        }
        favourites.add(model.getId());
        FileService.saveFavourite(favourites);
    }

    public void onClickRemove(DialogInterface dialogInterface, int i) {
        Toast.makeText(getActivity(), "Removing user", Toast.LENGTH_SHORT).show();
        ArrayList<Integer> favourites = FileService.loadFavourites();
        if (favourites == null) {
            Toast.makeText(getActivity(), "Failed to read favourites", Toast.LENGTH_SHORT).show();
            return;
        }
        favourites.remove(model.getId());
        FileService.saveFavourite(favourites);
    }

    public void onClickDismiss(DialogInterface dialogInterface, int i) {
        if (DialogInterface.BUTTON_NEGATIVE == i) {
            dialogInterface.dismiss();
        }
    }

    public boolean alreadyExists() {
        ArrayList<Integer> favourites = FileService.loadFavourites();
        if (favourites == null) {
            Toast.makeText(getActivity(), "Failed to read favourites", Toast.LENGTH_SHORT).show();
            return false;
        }
        return favourites.contains(model.getId());
    }
}
