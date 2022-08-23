package io.yousefessa.applicationupdater.destination.adapter

import io.yousefessa.applicationupdater.destination.Destination
import java.io.InputStream
import java.net.URLConnection
import java.sql.Connection

interface DestinationAdapter {
    fun fetchFile(): Pair<URLConnection, InputStream>?
    fun fetchVersion(): String

    companion object {
        @JvmStatic
        fun defaultAdapter(destination: Destination): DestinationAdapter {
            return DefaultDestinationAdapter(destination)
        }
    }
}