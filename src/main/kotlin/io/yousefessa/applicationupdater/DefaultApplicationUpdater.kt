package io.yousefessa.applicationupdater

import io.yousefessa.applicationupdater.destination.Destination
import io.yousefessa.applicationupdater.listener.ApplicationAdapter
import io.yousefessa.applicationupdater.schedule.ScheduleContext
import io.yousefessa.applicationupdater.schedule.ScheduleTask
import io.yousefessa.applicationupdater.util.ApplicationUtil.getInputStreamFrom
import java.util.concurrent.Executors

class DefaultApplicationUpdater(
    private val destination: Destination,
    private val adapter: ApplicationAdapter,
    private val task: ScheduleTask,
    private val version:
    String,
) : ApplicationUpdater {
    private var executor = Executors.newScheduledThreadPool(4)

    override fun init() {
        val scheduledTask = scheduledTask()
        executor.scheduleWithFixedDelay({
            try {
                val versionResult = readLineFrom(destination.versionDestination())

                println("versionResult: $versionResult")
                if (versionResult.isEmpty() || versionResult <= this.version) {
                    println("there is no up-to-date updates at the moment, returning")
                    return@scheduleWithFixedDelay
                }

                val context = ScheduleContext(versionResult)

                // pass down the context to the given scheduled task
                scheduledTask.handle(this, context)

                // if the context happen to be cancelled, do not continue
                if (context.cancel) {
                    println("cancelled. returning")
                    return@scheduleWithFixedDelay
                }

                adapter.onDownload(destination.fileDestination())
            } catch (exception: Exception) {
                throw IllegalStateException(exception)
            }
        }, scheduledTask
            .initialDelay(), scheduledTask.delay(), scheduledTask.timeUnit()
        )
    }

    private fun readLineFrom(destinationLink: String): String {
        val inputStream = getInputStreamFrom(destinationLink) ?: return ""
        inputStream.bufferedReader().use {
            return it.readLine()
        }
    }

    override fun scheduledTask(): ScheduleTask {
        return this.task
    }

    override fun destination(): Destination {
        return this.destination
    }
}