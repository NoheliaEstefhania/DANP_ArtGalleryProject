package com.example.danp_artgallery.beacon.utils

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.Region
import com.example.danp_artgallery.beacon.utils.Beacon

class BeaconViewModel(application: Application) : AndroidViewModel(application) {
    private val beaconManager = BeaconManager.getInstanceForApplication(application)
    private val region = Region("all-beacons", null, null, null)

    private val _beacons = MutableLiveData<List<Beacon>>()
    val beacons: LiveData<List<Beacon>> = _beacons

    private val rangingObserver = Observer<Collection<org.altbeacon.beacon.Beacon>> { beacons ->
        _beacons.value = beacons.map { Beacon.fromAltBeacon(it) }
    }

    init {
        val regionViewModel = beaconManager.getRegionViewModel(region)
        regionViewModel.rangedBeacons.observeForever(rangingObserver)
        beaconManager.startRangingBeacons(region)
    }
}
