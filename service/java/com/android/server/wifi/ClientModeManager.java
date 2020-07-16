/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.server.wifi;

import android.net.DhcpResultsParcelable;
import android.net.Network;
import android.net.wifi.IWifiConnectedNetworkScorer;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiAnnotations;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.hotspot2.IProvisioningCallback;
import android.net.wifi.hotspot2.OsuProvider;
import android.net.wifi.nl80211.WifiNl80211Manager;
import android.os.IBinder;
import android.os.Message;
import android.os.WorkSource;

import com.android.server.wifi.util.ActionListenerWrapper;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/**
 * Base class for ClientModeManager.
 */
public interface ClientModeManager extends ActiveModeManager {
    void connectNetwork(NetworkUpdateResult result, ActionListenerWrapper wrapper, int callingUid);

    void saveNetwork(NetworkUpdateResult result, ActionListenerWrapper wrapper, int callingUid);

    void disconnect();

    void reconnect(WorkSource ws);

    void reassociate();

    void startConnectToNetwork(int networkId, int uid, String bssid);

    void startRoamToNetwork(int networkId, ScanResult scanResult);

    boolean setWifiConnectedNetworkScorer(IBinder binder, IWifiConnectedNetworkScorer scorer);

    void clearWifiConnectedNetworkScorer();

    void resetSimAuthNetworks(@ClientModeImpl.ResetSimReason int resetReason);

    void sendBluetoothAdapterConnectionStateChange(int state);

    void sendBluetoothAdapterStateChange(int state);

    int syncGetWifiState();

    WifiInfo syncRequestConnectionInfo();

    boolean syncQueryPasspointIcon(long bssid, String fileName);

    Network syncGetCurrentNetwork();

    DhcpResultsParcelable syncGetDhcpResultsParcelable();

    long syncGetSupportedFeatures();

    boolean syncStartSubscriptionProvisioning(int callingUid, OsuProvider provider,
            IProvisioningCallback callback);

    boolean isWifiStandardSupported(@WifiAnnotations.WifiStandard int standard);

    void enableTdls(String remoteMacAddress, boolean enable);

    void dumpIpClient(FileDescriptor fd, PrintWriter pw, String[] args);

    void dumpWifiScoreReport(FileDescriptor fd, PrintWriter pw, String[] args);

    void updateLinkLayerStatsRssiAndScoreReport();

    String getFactoryMacAddress();

    WifiConfiguration getCurrentWifiConfiguration();

    WifiLinkLayerStats getWifiLinkLayerStats();

    boolean setPowerSave(boolean ps);

    boolean setLowLatencyMode(boolean enabled);

    WifiMulticastLockManager.FilterController getMcastLockManagerFilterController();

    boolean isConnected();

    boolean isDisconnected();

    boolean isSupplicantTransientState();

    void probeLink(WifiNl80211Manager.SendMgmtFrameCallback callback, int mcs);

    /** Send a {@link Message} to ClientModeImpl's StateMachine. */
    void sendMessageToClientModeImpl(Message msg);
}
