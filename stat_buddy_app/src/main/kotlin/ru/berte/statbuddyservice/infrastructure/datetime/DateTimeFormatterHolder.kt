package ru.berte.statbuddyservice.infrastructure.datetime

import ru.berte.statbuddyservice.presentation.constant.StatBuddyServiceApiConstants
import java.time.format.DateTimeFormatter

object DateTimeFormatterHolder {
    val dtf = DateTimeFormatter.ofPattern(StatBuddyServiceApiConstants.DATE_TIME_FORMAT)
}
