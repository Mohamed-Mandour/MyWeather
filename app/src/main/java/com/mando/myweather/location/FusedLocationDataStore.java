/*
 * Copyright (c) 2018. OpenSignal.com
 */

package com.mando.myweather.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.mando.myweather.utils.AndroidPermissionChecker;
import com.mando.myweather.utils.PermissionChecker;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FusedLocationDataStore implements LocationDataStore {
    public static final String TAG = "FusedLocationDataStore";

    private FusedLocationProviderClient mFusedLocationClient;

    private LocationCallback mLocationCallback;
    private DeviceLocation mLocation;
    private PermissionChecker permissionChecker;

    private CopyOnWriteArrayList<LocationDataStoreListener> mListeners = new CopyOnWriteArrayList<>();

    private FusedLocationDataStore() {
    }

    @VisibleForTesting
    void initialize(Context context) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        permissionChecker = new AndroidPermissionChecker(context);

        initLocationCallback();
        updateLastLocation();
    }

    private void initLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Log.i(TAG, "onLocationResult() called with: locationResult = [" + locationResult + "]" + " From thread: " + Thread.currentThread().getId() + " isMainThread [" + (Looper.myLooper() == Looper.getMainLooper()) + "]");

                if (locationResult == null) {
                    Log.w(TAG, "onLocationResult() received invalid locationResult: [" + locationResult + "]");
                    return;
                }

                mLocation = new DeviceLocation(locationResult.getLastLocation());
                Log.i(TAG, "onLocationResult() called with: location = [" + mLocation + "]");
                dispatchLocationChanged();
            }
        };
    }


    public DeviceLocation getLocation() {
        updateLastLocation();
        Log.d(TAG, "getLocation() returned: " + mLocation);
        return mLocation;
    }

    @SuppressLint("MissingPermission")
    @VisibleForTesting
    void updateLastLocation() {
        Log.d(TAG, "updateLastLocation() called" +
                " From thread: " + Thread.currentThread().getId() +
                " isMainThread [" + (Looper.myLooper() == Looper.getMainLooper()) + "]");

        if (!permissionChecker.getHasAnyLocationPermissions()) {
            Log.d(TAG, "No Location permission granted");
            return;
        }
        final Task<Location> lastLocationTask = mFusedLocationClient.getLastLocation();
        lastLocationTask.addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (lastLocationTask.isSuccessful()) {
                    final Location location = lastLocationTask.getResult();
                    if (location != null) {
                        mLocation = new DeviceLocation(location);
                    }
                }else {
                    Log.d(TAG, "Location is null");
                }
            }
        });

    }

    @VisibleForTesting
    void dispatchLocationChanged() {
        for (LocationDataStoreListener listener : mListeners) {
            listener.onLocationChanged(mLocation);
        }
    }

    public static FusedLocationDataStore getInstance(Context context) {
        final FusedLocationDataStore fusedLocationDataStore = InstanceHolder.sInstance;

        if (fusedLocationDataStore.mFusedLocationClient == null) {
            fusedLocationDataStore.initialize(context);
        }

        return fusedLocationDataStore;
    }

    private static final class InstanceHolder {
        private static final FusedLocationDataStore sInstance = new FusedLocationDataStore();
    }
}
