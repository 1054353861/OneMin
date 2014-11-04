package me.ele.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class NetUtil {
    
    private NetUtil() { }
    
    /**
     * mac地址
     * 
     * @param con
     * @return
     */
    public static String getLocalMacAddress(Context con) {
        WifiManager wifi = (WifiManager) con
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            return null;
        }
        return null;
    }

    public static boolean isWiFi(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null 
                        && info.isConnected() 
                        && info.getState() == NetworkInfo.State.CONNECTED 
                        && "WIFI".equalsIgnoreCase(info.getTypeName())) {
                    // 判断当前网络是否已经连接
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null)
            return false;
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo == null)
            return false;
        if (netinfo.isAvailable())
            return true;
        return false;
    }

    public static String getWlanMac(Context context) {
        WifiManager wm = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        String wlanMac = wm.getConnectionInfo().getMacAddress();
        return wlanMac;
    }

    public static String getBlueToothMac(Context context) {
        return BluetoothAdapter.getDefaultAdapter().getAddress();
    }

}
