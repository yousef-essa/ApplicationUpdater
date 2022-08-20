package io.yousefessa.applicationupdater.destination.adapter

import io.yousefessa.applicationupdater.destination.Destination
import java.io.InputStream

interface DestinationAdapter {
    fun fetchFile(): InputStream?
    fun fetchVersion(): String

    companion object {
        @JvmStatic
        fun defaultAdapter(destination: Destination): DestinationAdapter {
            return DefaultDestinationAdapter(destination)
        }
    }
}