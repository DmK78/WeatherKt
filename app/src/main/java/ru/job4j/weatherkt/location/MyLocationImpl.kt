package ru.job4j.weatherkt.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import javax.inject.Inject

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 01.12.2019
 */
class MyLocationImpl //this.application = application;
//getLocation();
@Inject constructor(var context: Context) :
    LocationListener {
    private var isGPSEnabled = false
    private var isNetworkEnabled = false
    private var isCanGetLocation = false
    private var location: Location? = null
    protected var locationManager: LocationManager? = null

    override fun onLocationChanged(location: Location) {}
    override fun onStatusChanged(
        provider: String,
        status: Int,
        extras: Bundle
    ) {
    }

    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}
    @SuppressLint("MissingPermission")
    fun getLocation(): Location? {
        try {
            locationManager = context
                .getSystemService(Context.LOCATION_SERVICE) as LocationManager
            isGPSEnabled = locationManager!!
                .isProviderEnabled(LocationManager.GPS_PROVIDER)
            isNetworkEnabled = locationManager!!
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!isGPSEnabled && !isNetworkEnabled) { // no network provider is enabled
                Log.i(TAG, "Network-GPS”, “Disable")
            } else {
                isCanGetLocation = true
                if (isNetworkEnabled) {
                    locationManager!!.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                    )
                    Log.i(TAG, "Network")
                    if (locationManager != null) {
                        location = locationManager!!
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                        if (location != null) {

                        }
                    }
                } else  // if GPS Enabled get lat/long using GPS Services
                    if (isGPSEnabled) {
                        if (location == null) {
                            locationManager!!.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(),
                                this
                            )
                            Log.i(TAG, "GPS Enabled")
                            if (locationManager != null) {
                                location = locationManager!!
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                                if (location != null) {

                                }
                            }
                        }
                    }
            }
        } catch (e: Exception) {
            //Log.i("my_location", e.message)
            e.printStackTrace()
        }
        return location
    }

    companion object {
        private const val TAG = "my_location"
        // The minimum distance to change Updates in meters
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10 // 10 meters
        // The minimum time between updates in milliseconds
        private const val MIN_TIME_BW_UPDATES = 200 * 10 * 1 // 2 seconds
            .toLong()
    }

}