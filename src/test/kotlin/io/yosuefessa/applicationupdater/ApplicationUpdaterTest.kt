package io.yosuefessa.applicationupdater

import io.yosuefessa.applicationupdater.helper.ApplicationUpdaterHelper.predefinedUpdaterAndMockedBooleanWrapper
import io.yosuefessa.applicationupdater.helper.ApplicationUpdaterHelper.predefinedUpdaterAndMockedTask
import io.yosuefessa.applicationupdater.helper.MockitoHelper
import io.yousefessa.applicationupdater.destination.GitHubReleaseDestination
import io.yousefessa.applicationupdater.schedule.ScheduleTask
import org.junit.jupiter.api.Test
import org.mockito.Mockito.atMostOnce
import org.mockito.Mockito.never
import org.mockito.Mockito.timeout
import org.mockito.Mockito.verify
import java.time.Duration

private const val CURRENT_TEST_VERSION = "0.1.0"
private const val OLDER_TEST_VERSION = "0.0.1"

class SimpleApplicationUpdaterTest {
    private val defaultDestination =
        GitHubReleaseDestination("yousef-essa", "ApplicationUpdaterSample")

    @Test
    fun testInitForGradual() {
        val predefinedPair = predefinedUpdaterAndMockedTask(defaultDestination)

        val updater = predefinedPair.first
        val task: ScheduleTask = predefinedPair.second

        updater.init()
        verify(task, never()).handle(MockitoHelper.anyObject())
    }

    @Test
    fun testInitForTaskExecutionWithCurrentVersion() {
        val predefinedPair = predefinedUpdaterAndMockedBooleanWrapper(
            defaultDestination,
            CURRENT_TEST_VERSION
        )

        val updater = predefinedPair.first
        val isTaskCancelled = predefinedPair.second

        updater.init()

        verify(isTaskCancelled,
            timeout(Duration.ofSeconds(3).toMillis()).atLeastOnce()).boolean(true)
    }

    @Test
    fun testInitForTaskExecutionWithOlderVersion() {
        val predefinedPair = predefinedUpdaterAndMockedBooleanWrapper(
            defaultDestination,
            OLDER_TEST_VERSION
        )

        val updater = predefinedPair.first
        val isTaskCancelled = predefinedPair.second

        updater.init()

        verify(isTaskCancelled,
            timeout(Duration.ofSeconds(3).toMillis()).atLeastOnce()).boolean(false)
    }

    @Test
    fun testUpdateCheckForImmediateness() {
        val predefinedPair = predefinedUpdaterAndMockedTask(defaultDestination)

        val updater = predefinedPair.first
        val task: ScheduleTask = predefinedPair.second

        updater.handleUpdateCheck()
        verify(task, atMostOnce()).handle(MockitoHelper.anyObject())
    }
}