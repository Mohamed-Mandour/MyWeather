/*
 * Copyright (c) 2018. OpenSignal.com
 */

package com.mando.myweather.location;


import android.content.Context;

public class LocationDataStoreFactory {
    public LocationDataStore getLocationDataStore(Context context) {
            return FusedLocationDataStore.getInstance(context);
    }
}
