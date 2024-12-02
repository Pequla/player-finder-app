package com.pequla.playerfinder;


import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtils {

    public static String getMinecraftIconUrl(String uuid) {
        return "https://crafatar.com/avatars/" + uuid;
    }

    public static String getMinecraftSkinUrl(String uuid) {
        return "https://crafatar.com/renders/body/" + uuid;
    }

    public static String getServerIcon(String address) {
        return "https://api.mcsrvstat.us/icon/" + address;
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatDate(Date date) {
        return new SimpleDateFormat("HH:mm dd-MM-yyyy").format(date) + " CEST";
    }
}
