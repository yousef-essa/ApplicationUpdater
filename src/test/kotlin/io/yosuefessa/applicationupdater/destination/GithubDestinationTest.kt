package io.yosuefessa.applicationupdater.destination

import io.yousefessa.applicationupdater.destination.GitHubReleaseDestination
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val USERNAME = "yousef-essa"
private const val REPOSITORY = "ApplicationUpdaterSample"
private const val VERSION_NAME = "version"

private const val RELEASE_FILE_NAME = "ApplicationUpdaterSample-0.1.0.jar"

class GithubDestinationTest {
    private val githubReleaseDestination = GitHubReleaseDestination(USERNAME,
        REPOSITORY, RELEASE_FILE_NAME)

    @Test
    fun testVersionDestination() {
        val expectedVersionDestination = githubReleaseDestination.versionDestination()
        val actualVersionDestination = GitHubReleaseDestination.GITHUB_LATEST_VERSION_LINK
            .format(USERNAME, REPOSITORY, VERSION_NAME)

        assertEquals(expectedVersionDestination, actualVersionDestination)
    }

    @Test
    fun testFileDestination() {
        val expectedFileDestination = githubReleaseDestination.fileDestination()
        val actualFileDestination = GitHubReleaseDestination.GITHUB_LATEST_FILE_LINK.format(
            USERNAME, REPOSITORY)

        assertEquals(expectedFileDestination, actualFileDestination)
    }
}