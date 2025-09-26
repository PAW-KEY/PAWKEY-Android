package com.paw.key.core.extension

import android.location.Location
import com.naver.maps.geometry.LatLng

fun Location.toLatLng(): LatLng {
    return LatLng(this.latitude, this.longitude)
}