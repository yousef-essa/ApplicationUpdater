package io.yousefessa.applicationupdater.schedule

import io.yousefessa.applicationupdater.meta.TaskContext

open class ScheduleContext(private val remoteVersion: String): TaskContext() {
    var cancel = false
    fun remoteVersion(): String = this.remoteVersion
}