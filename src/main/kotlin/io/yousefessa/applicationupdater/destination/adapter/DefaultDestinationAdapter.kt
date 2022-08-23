package io.yousefessa.applicationupdater.destination.adapter

import io.yousefessa.applicationupdater.destination.Destination
import io.yousefessa.applicationupdater.util.ApplicationUtil
import java.io.InputStream
import java.net.URLConnection

class DefaultDestinationAdapter(private val destination: Destination): DestinationAdapter {
    override fun fetchFile(): Pair<URLConnection, InputStream>? {
        return ApplicationUtil.getInputStreamFrom(destination.fileDestination())
    }

    override fun fetchVersion(): String {
        return ApplicationUtil.versionFrom(destination.versionDestination())
    }
}