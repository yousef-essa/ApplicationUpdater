package io.yousefessa.applicationupdater.destination

import io.yousefessa.applicationupdater.destination.adapter.DestinationAdapter

class GitHubDestination(
    private val username: String,
    private val repository: String,
    private val versionFileName: String,
): Destination {
    private val adapter: DestinationAdapter = DestinationAdapter.defaultAdapter(this)

    override fun fileDestination(): String {
        return GITHUB_LATEST_FILE_LINK.format(username, repository)
    }

    override fun versionDestination(): String {
        return GITHUB_LATEST_VERSION_LINK.format(username, repository, versionFileName)
    }

    override fun adapter(): DestinationAdapter {
        return adapter
    }

    companion object {
        @JvmStatic
        private val GITHUB_LINK = "https://github.com"

        @JvmStatic
        private val GITHUB_USER_CONTENT_LINK = "https://raw.githubusercontent" +
                ".com"

        @JvmStatic
        val GITHUB_LATEST_FILE_LINK = "$GITHUB_LINK/%s/%s/releases/latest"

        @JvmStatic
        val GITHUB_LATEST_VERSION_LINK = "$GITHUB_USER_CONTENT_LINK/%s/%s/main/%s"
    }
}