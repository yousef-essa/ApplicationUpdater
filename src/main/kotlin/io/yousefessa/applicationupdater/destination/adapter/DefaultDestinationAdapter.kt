package io.yousefessa.applicationupdater.destination.adapter

import io.yousefessa.applicationupdater.destination.Destination
import io.yousefessa.applicationupdater.util.ApplicationUtil
import java.io.InputStream

class DefaultDestinationAdapter(private val destination: Destination): DestinationAdapter {
    override fun fetchFile(): InputStream? {
        return ApplicationUtil.getInputStreamFrom(destination.fileDestination())
    }

    override fun fetchVersion(): String {
        return ApplicationUtil.versionFrom(destination.versionDestination())
    }
}