package io.yousefessa.applicationupdater

import io.yousefessa.applicationupdater.adapter.ApplicationAdapter
import io.yousefessa.applicationupdater.destination.Destination
import io.yousefessa.applicationupdater.meta.TaskService
import io.yousefessa.applicationupdater.schedule.ScheduleContext
import io.yousefessa.applicationupdater.schedule.ScheduleTask
import io.yousefessa.applicationupdater.util.ApplicationUtil.isRemoteVersionNewer
import java.util.concurrent.Executors

// todo: replace ExecutorService with Coroutines
abstract class ApplicationUpdater: TaskService {
    private var executor = Executors.newSingleThreadScheduledExecutor()

    override fun init() {
        executor.scheduleWithFixedDelay({
            try {
                handleUpdateCheck()
            } catch (exception: Exception) {
                throw IllegalStateException(exception)
            }
        }, initialDelay(), delay(), timeUnit())
    }

    open fun handleUpdateCheck() {
        val remote = isRemoteVersionNewer(destination(), localVersion())
        val context = ScheduleContext(remote.first)

        val isRemoteVersionNewer = remote.second
        if (isRemoteVersionNewer.not()) {
            context.cancel = true
        }

        dispatchTask(context)

        // if the context happen to be cancelled, do not continue
        if (context.cancel) {
            println("cancelled. returning")
            return
        }

        adapter().onDownload(destination().fileDestination())
    }

    private fun dispatchTask(context: ScheduleContext) {
        scheduledTask().handle(context)
    }

    abstract fun scheduledTask(): ScheduleTask
    abstract fun destination(): Destination
    abstract fun localVersion(): String
    abstract fun adapter(): ApplicationAdapter
}