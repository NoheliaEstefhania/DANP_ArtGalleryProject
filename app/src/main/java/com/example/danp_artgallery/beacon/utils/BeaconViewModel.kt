package com.example.danp_artgallery.beacon.utils

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.Region
import com.example.danp_artgallery.beacon.utils.BeaconData
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.MonitorNotifier

class BeaconViewModel(application: Application) : AndroidViewModel(application) {

    // Referencia al BeaconManager y al servicio BeaconSearcher
    private val beaconManager = BeaconManager.getInstanceForApplication(application)
    private val beaconSearcher = application as BeaconReferenceApplication

    // LiveData para los beacons detectados y el estado de monitoreo/ranging
    private val _beacons = MutableLiveData<List<Beacon>>()
    val beacons: LiveData<List<Beacon>>
        get() = _beacons

    private val _isMonitoring = MutableLiveData<Boolean>()
    val isMonitoring: LiveData<Boolean>
        get() = _isMonitoring

    private val _isRanging = MutableLiveData<Boolean>()
    val isRanging: LiveData<Boolean>
        get() = _isRanging

    init {
        setupObservers()
    }

    // Configuración de los observadores para actualizar LiveData según el estado de los beacons
    private fun setupObservers() {
        val regionViewModel = beaconManager.getRegionViewModel(beaconSearcher.region)

        // Observador para los beacons detectados
        regionViewModel.rangedBeacons.observeForever { beacons ->
            _beacons.postValue(beacons.toList())
            _isRanging.postValue(beacons.isNotEmpty())
        }

        // Observador para el estado de monitoreo (dentro o fuera de la región)
        regionViewModel.regionState.observeForever { state ->
            _isMonitoring.postValue(state == MonitorNotifier.INSIDE)
        }
    }

    // Métodos para iniciar y detener el rastreo (ranging) y monitoreo de beacons
    fun startRangingBeacons() {
        beaconManager.startRangingBeacons(beaconSearcher.region)
    }

    fun stopRangingBeacons() {
        beaconManager.stopRangingBeacons(beaconSearcher.region)
    }

    fun startMonitoring() {
        beaconManager.startMonitoring(beaconSearcher.region)
    }

    fun stopMonitoring() {
        beaconManager.stopMonitoring(beaconSearcher.region)
    }
}
