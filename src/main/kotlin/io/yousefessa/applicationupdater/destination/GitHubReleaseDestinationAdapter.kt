package io.yousefessa.applicationupdater.destination

import io.yousefessa.applicationupdater.destination.adapter.DestinationAdapter
import io.yousefessa.applicationupdater.util.ApplicationUtil
import java.io.InputStream

private const val JSON_RESPONSE_VERSION_TAG = "tag_name"

class GitHubReleaseDestinationAdapter(private val destination: Destination):
    DestinationAdapter {
    override fun fetchFile(): InputStream? {
        return ApplicationUtil.getInputStreamFrom(destination.fileDestination())
    }

    override fun fetchVersion(): String {
        return ApplicationUtil.versionFrom(destination.versionDestination(),
            JSON_RESPONSE_VERSION_TAG)
    }
}