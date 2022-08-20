package io.yosuefessa.applicationupdater.helper

import io.yosuefessa.applicationupdater.wrapper.BooleanWrapper
import io.yousefessa.applicationupdater.ApplicationUpdater
import io.yousefessa.applicationupdater.SimpleApplicationUpdater
import io.yousefessa.applicationupdater.adapter.ApplicationAdapter
import io.yousefessa.applicationupdater.destination.Destination
import io.yousefessa.applicationupdater.schedule.ScheduleContext
import io.yousefessa.applicationupdater.schedule.ScheduleTask
import org.mockito.Mockito
import org.tinylog.kotlin.Logger
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

private const val INITIAL_DELAY_INTERVAL = 1L
private const val DELAY_INTERVAL = 60L
private val TIME_UNIT = TimeUnit.SECONDS

object ApplicationUpdaterHelper {
    fun mockAdapter(consumer: Consumer<String>) {
        val adapter = Mockito.mock(ApplicationAdapter::class.java)
        Mockito.`when`(adapter.onDownload(MockitoHelper.anyObject())).then {
            val fileDestinationLink = it.getArgument<String>(0)
            consumer.accept(fileDestinationLink)
        }
    }

    private fun mockScheduleTask(
        consumer: Consumer<ScheduleContext>,
    ): ScheduleTask {
        val mockTask: ScheduleTask = Mockito.mock(ScheduleTask::class.java)
        Mockito.`when`(mockTask.handle(MockitoHelper.anyObject())).then {
            val scheduleContext: ScheduleContext = it.getArgument(0)

            consumer.accept(scheduleContext)
        }
        return mockTask
    }

    fun predefinedUpdaterAndMockedTask(
        destination: Destination,
        localVersion: String = "unknown",
    ): Pair<ApplicationUpdater, ScheduleTask> {
        val task: ScheduleTask = mockScheduleTask {
            Logger.debug("task ran")
        }

        val adapter = Mockito.mock(ApplicationAdapter::class.java)
        val updater = defaultApplicationUpdater(destination, adapter, task, localVersion)

        return Pair(updater, task)
    }

    fun predefinedUpdaterAndMockedBooleanWrapper(
        destination: Destination,
        localVersion: String = "unknown",
    ): Pair<ApplicationUpdater, BooleanWrapper> {
        val isTaskCancelled = Mockito.mock(BooleanWrapper::class.java)

        val task: ScheduleTask = mockScheduleTask { context ->
            Logger.debug("task ran")
            isTaskCancelled.boolean(context.cancel)
        }

        val adapter = Mockito.mock(ApplicationAdapter::class.java)
        val updater = defaultApplicationUpdater(destination, adapter, task, localVersion)

        return Pair(updater, isTaskCancelled)
    }

    fun predefinedUpdaterAndMockedAdapter(
        destination: Destination,
        localVersion: String = "unknown",
    ): Pair<ApplicationUpdater, ApplicationAdapter> {
        val task: ScheduleTask = mockScheduleTask {
            Logger.debug("task ran: $it")
        }

        val adapter = Mockito.mock(ApplicationAdapter::class.java)
        val updater = defaultApplicationUpdater(destination, adapter, task, localVersion)

        return Pair(updater, adapter)
    }

    private fun defaultApplicationUpdater(
        destination: Destination,
        adapter: ApplicationAdapter,
        task: ScheduleTask,
        localVersion: String,
    ): ApplicationUpdater {
        return SimpleApplicationUpdater(destination, adapter, task, localVersion,
            INITIAL_DELAY_INTERVAL, DELAY_INTERVAL, TIME_UNIT)
    }
}