package ru.berte.statbuddyservice.presentation.http

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import ru.berte.statbuddyservice.presentation.web.dto.Actor
import ru.shedlab.scheduleconstruction.presentation.web.standard.StandardResponse

interface ISwaggerCommonController {

    @Operation(summary = "Gets all actors", description = "Returns 200 if successful")
    @ApiResponses(
        value = [ApiResponse(
            responseCode = "200",
            description = "Successful Operation"
        )]
    )
    suspend fun getActors(): StandardResponse<List<Actor>>
}
