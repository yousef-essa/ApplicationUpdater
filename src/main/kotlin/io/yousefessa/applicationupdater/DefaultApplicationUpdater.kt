package io.yousefessa.applicationupdater

import io.yousefessa.applicationupdater.destination.Destination
import io.yousefessa.applicationupdater.listener.UpdaterListener
import io.yousefessa.applicationupdater.schedule.ScheduleTask
import java.util.concurrent.Executors

class DefaultApplicationUpdater(private val destination: Destination, private val
listener: UpdaterListener, private val task: ScheduleTask): ApplicationUpdater {
    private var executor = Executors.newSingleThreadScheduledExecutor()

    override fun init() {
        val scheduledTask = scheduledTask()
        executor.scheduleWithFixedDelay({
            scheduledTask.handle(this)
        }, scheduledTask
            .initialDelay(), scheduledTask.delay(), scheduledTask.timeUnit())
    }

    override fun scheduledTask(): ScheduleTask {
        return this.task
    }

    override fun destination(): Destination {
        return this.destination
    }
}