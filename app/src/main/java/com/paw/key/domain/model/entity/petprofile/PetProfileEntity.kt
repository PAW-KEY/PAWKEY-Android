package com.paw.key.domain.model.entity.petprofile

import android.net.Uri

data class PetProfileEntity(
    val petId: Long,
    val name: String,
    val gender: String,
    val isNeutered: Boolean,
    val age: Int,
    val isAgeKnown: Boolean,
    val breed: String,
    val imageUrl: Uri,
    val traits: List<TraitEntity>,
    val walkCount: Int
)

data class TraitEntity(
    val category: String,
    val option: String
)