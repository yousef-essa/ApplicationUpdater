package io.yosuefessa.applicationupdater

import io.yosuefessa.applicationupdater.helper.ApplicationUpdaterHelper.mockAdapter
import io.yosuefessa.applicationupdater.helper.ApplicationUpdaterHelper.predefinedUpdaterAndMockedAdapter
import io.yosuefessa.applicationupdater.helper.ApplicationUpdaterHelper.predefinedUpdaterAndMockedBooleanWrapper
import io.yosuefessa.applicationupdater.helper.ApplicationUpdaterHelper.predefinedUpdaterAndMockedTask
import io.yosuefessa.applicationupdater.helper.MockitoHelper
import io.yousefessa.applicationupdater.destination.GitHubReleaseDestination
import io.yousefessa.applicationupdater.schedule.ScheduleTask
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertTimeout
import org.mockito.Mockito.atMostOnce
import org.mockito.Mockito.never
import org.mockito.Mockito.timeout
import org.mockito.Mockito.verify
import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import java.time.Duration
import java.time.Instant

private const val CURRENT_TEST_VERSION = "0.1.0"
private const val OLDER_TEST_VERSION = "0.0.1"

private const val LOWER_CASE_32_CASE_HEX_LEFT_PADDED = "%032x"

private const val GITHUB_USERNAME = "yousef-essa"
private const val GITHUB_REPOSITORY = "ApplicationUpdaterSample"
private const val RELEASE_FILE_NAME = "ApplicationUpdaterSample.jar"

class SimpleApplicationUpdaterTest {
    private val defaultDestination =
        GitHubReleaseDestination(GITHUB_USERNAME, GITHUB_REPOSITORY, RELEASE_FILE_NAME)

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
            CURRENT_TEST_VERSION,
        )

        val updater = predefinedPair.first
        val isTaskCancelled = predefinedPair.second

        updater.init()

        verify(isTaskCancelled,
            timeout(Duration.ofSeconds(5).toMillis()).only()).boolean(true)
    }

    @Test
    fun testInitForTaskExecutionWithOlderVersion() {
        val predefinedPair = predefinedUpdaterAndMockedBooleanWrapper(
            defaultDestination,
            OLDER_TEST_VERSION,
        )

        val updater = predefinedPair.first
        val isTaskCancelled = predefinedPair.second

        updater.init()

        verify(isTaskCancelled,
            timeout(Duration.ofSeconds(5).toMillis()).only()).boolean(false)
    }

    @Test
    fun testUpdateCheckForImmediateness() {
        val predefinedPair = predefinedUpdaterAndMockedTask(defaultDestination)

        val updater = predefinedPair.first
        val task: ScheduleTask = predefinedPair.second

        updater.handleUpdateCheck()
        verify(task, atMostOnce()).handle(MockitoHelper.anyObject())
    }

    @Test
    fun testDownloadInvokeAdapterWhenUpdateIsAvailable() {
        val predefinedPair = predefinedUpdaterAndMockedAdapter(
            defaultDestination,
            OLDER_TEST_VERSION,
        )

        val updater = predefinedPair.first
        val adapter = predefinedPair.second

        updater.init()

        verify(
            adapter,
            timeout(Duration.ofSeconds(5).toMillis()).only()
        ).onDownload(MockitoHelper.anyObject())
    }

    @Test
    fun testDownloadFunctionalityWhenUpdateIsAvailable() {
        var signature = ""
        val predefinedPair = predefinedUpdaterAndMockedAdapter(
            defaultDestination,
            OLDER_TEST_VERSION,
            mockAdapter { context ->
                val file = File("sample.jar")

                val input = context.inputStream
                input.use {
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }

                // todo: this can be improved by either caching the bytes above or
                //  handling our own digest, and create a method that formats the
                //  signature accordingly
                signature = transformMD5Checksum(file.readBytes())
            }
        )

        val updater = predefinedPair.first
        val adapter = predefinedPair.second

        updater.init()

        verify(
            adapter,
            timeout(Duration.ofSeconds(5).toMillis()).only()
        ).onDownload(MockitoHelper.anyObject())

        val startInstant = Instant.now()
        assertTimeout(Duration.ofSeconds(5)) {
            var elapsed = Duration.between(startInstant, Instant.now())
            while (elapsed.seconds < 5 &&
                signature.isEmpty()) {
                elapsed = Duration.between(startInstant, Instant.now())
            }

            val actualSignature = "b6ef6767f694833589249ed563e64c12"
            assertEquals(signature, actualSignature)
        }
    }

    @Test
    fun testMD5ChecksumAccuracy() {
        val md5Sample = File("libs/md5-sample.jar")

        val expectedMD5Checksum = transformMD5Checksum(md5Sample.readBytes())
        val actualMD5 = "b6ef6767f694833589249ed563e64c12"

        assertEquals(expectedMD5Checksum, actualMD5)
    }

    private fun transformMD5Checksum(byteArray: ByteArray): String {
        val messageDigest = MessageDigest.getInstance("MD5")
        val digest = messageDigest.digest(byteArray)
        return String.format(LOWER_CASE_32_CASE_HEX_LEFT_PADDED, BigInteger(1, digest))
    }
}