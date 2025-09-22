package com.paw.key.core.util

import com.naver.maps.geometry.LatLng
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

fun flattenCoordinatesToLatLng(
    coordinates: List<List<List<Pair<Double, Double>>>>
): ImmutableList<ImmutableList<LatLng>> {
    return coordinates.map { polygon ->  // 각 Polygon
        polygon.firstOrNull()?.map { point ->
            LatLng(point.first, point.second)
        }.orEmpty().toPersistentList()
    }.toPersistentList()
}