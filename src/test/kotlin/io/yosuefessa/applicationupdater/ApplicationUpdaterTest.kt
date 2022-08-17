package io.yosuefessa.applicationupdater

import io.yosuefessa.applicationupdater.helper.ApplicationUpdaterHelper.mockScheduleTask
import io.yosuefessa.applicationupdater.helper.MockitoHelper
import io.yosuefessa.applicationupdater.wrapper.BooleanWrapper
import io.yousefessa.applicationupdater.DefaultApplicationUpdater
import io.yousefessa.applicationupdater.adapter.ApplicationAdapter
import io.yousefessa.applicationupdater.destination.GitHubDestination
import io.yousefessa.applicationupdater.schedule.ScheduleTask
import org.junit.jupiter.api.Test
import org.mockito.Mockito.atMostOnce
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.timeout
import org.mockito.Mockito.verify
import java.time.Duration

private const val CURRENT_TEST_VERSION = "0.1.0"
private const val OLDER_TEST_VERSION = "0.0.1"

class DefaultApplicationUpdaterTest {
    private val defaultDestination =
        GitHubDestination("yousef-essa", "ApplicationUpdaterSample", "version")

    @Test
    fun testInitForGradual() {
        val isTaskCancelled = mock(BooleanWrapper::class.java)

        val task: ScheduleTask = mockScheduleTask { context ->
            println("task ran")
            isTaskCancelled.boolean(context.cancel)
        }

        val adapter = mock(ApplicationAdapter::class.java)
        val updater = DefaultApplicationUpdater(defaultDestination,
            adapter,
            task,
            CURRENT_TEST_VERSION)

        updater.init()
        verify(task, never()).handle(MockitoHelper.anyObject())
    }

    @Test
    fun testInitForTaskExecutionWithCurrentVersion() {
        val isTaskCancelled = mock(BooleanWrapper::class.java)

        val task: ScheduleTask = mockScheduleTask { context ->
            println("task ran")
            isTaskCancelled.boolean(context.cancel)
        }

        val adapter = mock(ApplicationAdapter::class.java)
        val updater = DefaultApplicationUpdater(defaultDestination,
            adapter,
            task,
            CURRENT_TEST_VERSION)

        updater.init()

        verify(isTaskCancelled,
            timeout(Duration.ofSeconds(3).toMillis()).atLeastOnce()).boolean(true)
    }

    @Test
    fun testInitForTaskExecutionWithOlderVersion() {
        val isTaskCancelled = mock(BooleanWrapper::class.java)
        val task = mockScheduleTask { context ->
            println("task ran")
            isTaskCancelled.boolean(context.cancel)
        }

        val adapter = mock(ApplicationAdapter::class.java)
        val updater = DefaultApplicationUpdater(defaultDestination,
            adapter,
            task,
            OLDER_TEST_VERSION)

        updater.init()

        verify(isTaskCancelled,
            timeout(Duration.ofSeconds(3).toMillis()).atLeastOnce()).boolean(false)
    }

    @Test
    fun testUpdateCheckForImmediateness() {
        val isTaskCancelled = mock(BooleanWrapper::class.java)
        val task = mockScheduleTask { context ->
            println("task ran")
            isTaskCancelled.boolean(context.cancel)
        }

        val adapter = mock(ApplicationAdapter::class.java)
        val updater = DefaultApplicationUpdater(defaultDestination,
            adapter,
            task,
            OLDER_TEST_VERSION)

        updater.handleUpdateCheck()
        verify(task, atMostOnce()).handle(MockitoHelper.anyObject())
    }
}