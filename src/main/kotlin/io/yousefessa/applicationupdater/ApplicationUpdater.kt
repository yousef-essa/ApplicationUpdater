package io.yousefessa.applicationupdater

import io.yousefessa.applicationupdater.destination.Destination
import io.yousefessa.applicationupdater.schedule.ScheduleTask

interface ApplicationUpdater {
    fun init()
    fun scheduledTask(): ScheduleTask

    fun destination(): Destination
}