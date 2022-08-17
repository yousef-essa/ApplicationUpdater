package io.yosuefessa.applicationupdater.helper

import io.yosuefessa.applicationupdater.wrapper.BooleanWrapper
import io.yousefessa.applicationupdater.ApplicationUpdater
import io.yousefessa.applicationupdater.DefaultApplicationUpdater
import io.yousefessa.applicationupdater.adapter.ApplicationAdapter
import io.yousefessa.applicationupdater.destination.Destination
import io.yousefessa.applicationupdater.schedule.ScheduleContext
import io.yousefessa.applicationupdater.schedule.ScheduleTask
import org.mockito.Mockito
import java.util.function.Consumer

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
        val isTaskCancelled = Mockito.mock(BooleanWrapper::class.java)

        val task: ScheduleTask = mockScheduleTask { context ->
            println("task ran")
            isTaskCancelled.boolean(context.cancel)
        }

        val adapter = Mockito.mock(ApplicationAdapter::class.java)
        val updater = DefaultApplicationUpdater(destination,
            adapter,
            task,
            localVersion)

        return Pair(updater, task)
    }

    fun predefinedUpdaterAndMockedBooleanWrapper(
        destination: Destination,
        localVersion: String = "unknown",
    ): Pair<ApplicationUpdater, BooleanWrapper> {
        val isTaskCancelled = Mockito.mock(BooleanWrapper::class.java)

        val task: ScheduleTask = mockScheduleTask { context ->
            println("task ran")
            isTaskCancelled.boolean(context.cancel)
        }

        val adapter = Mockito.mock(ApplicationAdapter::class.java)
        val updater = DefaultApplicationUpdater(destination,
            adapter,
            task,
            localVersion)

        return Pair(updater, isTaskCancelled)
    }
}