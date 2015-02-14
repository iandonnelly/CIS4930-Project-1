package com.example.ianjavier.project1.domain.utils;

import android.util.Log;
import org.apache.http.conn.util.InetAddressUtils;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


public class NetworkHelper {
    static final String TAG = "NetworkHelper";

    public static String getIPv4Address() {
        String address = "";
        try {
            Enumeration<NetworkInterface> interfaces =
                    NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface current = interfaces.nextElement();
                Enumeration<InetAddress> currentAddresses =
                        current.getInetAddresses();
                while (currentAddresses.hasMoreElements()) {
                    InetAddress inetAddress =
                            currentAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() &&
                            InetAddressUtils.isIPv4Address(
                                    inetAddress.getHostAddress())) {
                        address = inetAddress.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException sockex) {
            Log.e(TAG, sockex.getLocalizedMessage(), sockex);
        }
        return address;
    }
    public static String getIPv6Address() {
        String address = "";
        try {
            Enumeration<NetworkInterface> interfaces =
                    NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface current = interfaces.nextElement();
                Enumeration<InetAddress> currentAddresses =
                        current.getInetAddresses();
                while (currentAddresses.hasMoreElements()) {
                    InetAddress inetAddress =
                            currentAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() &&
                            InetAddressUtils.isIPv6Address(
                                    inetAddress.getHostAddress())) {
                        address = inetAddress.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException sockex) {
            Log.e(TAG, sockex.getLocalizedMessage(), sockex);
        }
        return address;
    }
}
