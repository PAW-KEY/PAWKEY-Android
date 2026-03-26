package com.paw.key.domain.entity.walkpreparation

data class WalkPreparationEntity(
    val preparationList: List<String>
)

data class WalkPreparationMessageEntity(
    val mainMessage: String,
    val subMessage: String
)
