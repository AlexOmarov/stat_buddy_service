package ru.shedlab.scheduleconstruction.presentation.web.standard

import io.swagger.v3.oas.annotations.media.Schema
import ru.berte.statbuddyservice.presentation.web.standard.ResponseMetadata

@Schema(description = "Standard response object with response in 'response' field and metadata")
data class StandardResponse<T>(val response: T, val metadata: ResponseMetadata)
