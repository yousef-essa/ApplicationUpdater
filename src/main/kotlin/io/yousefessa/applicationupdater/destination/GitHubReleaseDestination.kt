package io.yousefessa.applicationupdater.destination

import io.yousefessa.applicationupdater.destination.adapter.DestinationAdapter

open class GitHubReleaseDestination(
    private val username: String,
    private val repository: String,
    private val fileName: String,
): Destination {
    private val adapter: DestinationAdapter = GitHubReleaseDestinationAdapter(this)

    override fun fileDestination(): String {
        return GITHUB_LATEST_FILE_LINK.format(username, repository, fileName)
    }

    override fun versionDestination(): String {
        return GITHUB_LATEST_VERSION_LINK.format(username, repository)
    }

    override fun adapter(): DestinationAdapter {
        return adapter
    }

    companion object {
        private const val GITHUB_LINK = "https://github.com"

        private const val GITHUB_RELEASE_API_LINK = "https://api.github" +
                ".com"

        @JvmStatic
        val GITHUB_LATEST_FILE_LINK = "$GITHUB_LINK/%s/%s/releases/latest/download/%s"

        @JvmStatic
        val GITHUB_LATEST_VERSION_LINK = "$GITHUB_RELEASE_API_LINK/repos/%s/%s/releases/latest"
    }
}