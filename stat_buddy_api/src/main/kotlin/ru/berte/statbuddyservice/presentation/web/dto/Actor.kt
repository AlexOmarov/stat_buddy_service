package ru.berte.statbuddyservice.presentation.web.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "Actor dto")
data class Actor(
    @Schema(description = "Actor unique system identifier")
    val id: UUID,
    @Schema(description = "Actor unique business identifier")
    val code: String
)
