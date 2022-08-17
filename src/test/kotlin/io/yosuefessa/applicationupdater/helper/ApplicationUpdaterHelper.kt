package io.yosuefessa.applicationupdater.helper

import io.yousefessa.applicationupdater.adapter.ApplicationAdapter
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

    fun mockScheduleTask(
        consumer: Consumer<ScheduleContext>,
    ): ScheduleTask {
        val mockTask: ScheduleTask = Mockito.mock(ScheduleTask::class.java)
        Mockito.`when`(mockTask.handle(MockitoHelper.anyObject())).then {
            val scheduleContext: ScheduleContext = it.getArgument(0)

            consumer.accept(scheduleContext)
        }
        return mockTask
    }
}