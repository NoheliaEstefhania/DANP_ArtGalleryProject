package com.example.danp_artgallery.beacon.utils

data class Beacon(
    val id1: String,
    val id2: String,
    val id3: String,
    val rssi: Int,
    val distance: Double
)

fun Beacon.Companion.fromAltBeacon(beacon: org.altbeacon.beacon.Beacon): Beacon {
    return Beacon(
        id1 = beacon.id1.toString(),
        id2 = beacon.id2.toString(),
        id3 = beacon.id3.toString(),
        rssi = beacon.rssi,
        distance = beacon.distance
    )
}
