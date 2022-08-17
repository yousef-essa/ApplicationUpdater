package io.yosuefessa.applicationupdater.destination

import io.yousefessa.applicationupdater.destination.GitHubDestination
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private const val USERNAME = "yousef-essa"
private const val REPOSITORY = "ApplicationUpdaterSample"
private const val VERSION_NAME = "version"

class GithubDestinationTest {
    private val githubDestination = GitHubDestination(USERNAME,
        REPOSITORY, VERSION_NAME)

    @Test
    fun testVersionDestination() {
        val expectedVersionDestination = githubDestination.versionDestination()
        val actualVersionDestination = GitHubDestination.GITHUB_LATEST_VERSION_LINK
            .format(USERNAME, REPOSITORY, VERSION_NAME)

        assertEquals(expectedVersionDestination, actualVersionDestination)
    }

    @Test
    fun testFileDestination() {
        val expectedFileDestination = githubDestination.fileDestination()
        val actualFileDestination = GitHubDestination.GITHUB_LATEST_FILE_LINK.format(
            USERNAME, REPOSITORY)

        assertEquals(expectedFileDestination, actualFileDestination)
    }
}