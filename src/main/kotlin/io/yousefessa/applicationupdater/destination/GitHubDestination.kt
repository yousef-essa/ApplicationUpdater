package io.yousefessa.applicationupdater.destination

class GitHubDestination(
    private val username: String,
    private val repository: String,
    private val versionFileName: String,
): Destination {
    override fun fileDestination(): String {
        return GITHUB_LATEST_RELEASE_LINK.format(username, repository)
    }

    override fun versionDestination(): String {
        return GITHUB_LATEST_VERSION_LINK.format(username, repository, versionFileName)
    }

    companion object {
        @JvmStatic
        private val GITHUB_LINK = "https://github" +
                ".com/%s/%s"

        @JvmStatic
        private val GITHUB_LATEST_RELEASE_LINK = "$GITHUB_LINK/%s/releases/latest"

        @JvmStatic
        private val GITHUB_LATEST_VERSION_LINK = "$GITHUB_LINK/raw/%s"
    }
}