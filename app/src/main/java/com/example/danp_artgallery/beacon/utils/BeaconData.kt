package com.example.danp_artgallery.beacon.utils

import org.altbeacon.beacon.Beacon

data class BeaconData(
    val id1: String,
    val id2: String,
    val id3: String,
    val rssi: Int,
    val distance: Double
) {
    companion object {
        fun fromAltBeacon(it: Beacon): BeaconData {
            return BeaconData(
                id1 = it.id1.toString(),
                id2 = it.id2.toString(),
                id3 = it.id3.toString(),
                rssi = it.rssi,
                distance = it.distance
            )
        }
    }
}
