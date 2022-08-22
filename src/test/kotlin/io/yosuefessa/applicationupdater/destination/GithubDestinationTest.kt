package io.yosuefessa.applicationupdater.destination

import io.yousefessa.applicationupdater.destination.GitHubReleaseDestination
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val GITHUB_USERNAME = "yousef-essa"
private const val GITHUB_REPOSITORY = "ApplicationUpdaterSample"
private const val RELEASE_FILE_NAME = "ApplicationUpdaterSample-0.1.0.jar"

class GithubDestinationTest {
    private val githubReleaseDestination = GitHubReleaseDestination(GITHUB_USERNAME,
        GITHUB_REPOSITORY, RELEASE_FILE_NAME)

    @Test
    fun testVersionDestination() {
        val expectedVersionDestination = githubReleaseDestination.versionDestination()
        val actualVersionDestination = GitHubReleaseDestination.GITHUB_LATEST_VERSION_LINK
            .format(GITHUB_USERNAME, GITHUB_REPOSITORY)

        assertEquals(expectedVersionDestination, actualVersionDestination)
    }

    @Test
    fun testFileDestination() {
        val expectedFileDestination = githubReleaseDestination.fileDestination()
        val actualFileDestination = GitHubReleaseDestination.GITHUB_LATEST_FILE_LINK.format(
            GITHUB_USERNAME, GITHUB_REPOSITORY, RELEASE_FILE_NAME)

        assertEquals(expectedFileDestination, actualFileDestination)
    }
}