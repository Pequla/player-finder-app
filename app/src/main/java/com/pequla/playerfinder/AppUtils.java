package com.pequla.playerfinder;


import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtils {

    public static String getMinecraftIconUrl(String uuid) {
        return "https://visage.surgeplay.com/face/" + uuid;
    }

    public static String getMinecraftSkinUrl(String uuid) {
        return "https://visage.surgeplay.com/full/" + uuid;
    }

    public static String getServerIcon(String address) {
        return "https://api.mcsrvstat.us/icon/" + address;
    }

    public static String formatDate(Date date) {
        return new SimpleDateFormat("HH:mm dd-MM-yyyy").format(date) + " CEST";
    }
}
