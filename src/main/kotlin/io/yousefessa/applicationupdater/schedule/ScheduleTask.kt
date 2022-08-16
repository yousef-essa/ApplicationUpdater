package io.yousefessa.applicationupdater.schedule

import io.yousefessa.applicationupdater.ApplicationUpdater
import java.util.concurrent.TimeUnit

interface ScheduleTask {
    fun handle(updater: ApplicationUpdater, context: ScheduleContext)

    fun initialDelay(): Long
    fun delay(): Long
    fun timeUnit(): TimeUnit
}