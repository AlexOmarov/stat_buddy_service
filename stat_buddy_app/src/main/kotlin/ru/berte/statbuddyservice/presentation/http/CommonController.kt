package ru.berte.statbuddyservice.presentation.http

import kotlinx.coroutines.flow.toList
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.berte.statbuddyservice.application.service.CommonService
import ru.berte.statbuddyservice.presentation.web.dto.Actor
import ru.shedlab.scheduleconstruction.presentation.web.standard.ResponseMetadata
import ru.shedlab.scheduleconstruction.presentation.web.standard.ResultCode
import ru.shedlab.scheduleconstruction.presentation.web.standard.StandardResponse

@RestController
@RequestMapping("/actors")
@Validated
class CommonController(private val service: CommonService) : ISwaggerCommonController {
    private val logger = LoggerFactory.getLogger(CommonController::class.java)

    @GetMapping
    override suspend fun getActors(): StandardResponse<List<Actor>> {
        logger.info("Got getRequestStatuses request")
        val response = service.getActors().toList()
        return StandardResponse(response, ResponseMetadata(ResultCode.OK, ""))
    }
}
