package io.yousefessa.applicationupdater.util

import io.yousefessa.applicationupdater.destination.Destination
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object ApplicationUtil {
    fun isRemoteVersionNewer(destination: Destination, localVersion: String):
            Pair<String, Boolean> {
        val remoteVersion = readLineFrom(destination.versionDestination())

        println("remoteVersion: $remoteVersion")
        val isRemoteVersionNewer =
            remoteVersion.isNotEmpty() && remoteVersion > localVersion

        return Pair(remoteVersion, isRemoteVersionNewer)
    }

    private fun readLineFrom(destinationLink: String): String {
        val inputStream = getInputStreamFrom(destinationLink) ?: return ""
        inputStream.bufferedReader().use {
            return it.readLine()
        }
    }

    fun getInputStreamFrom(destinationLink: String): InputStream? {
        runCatching {
            val url = URL(destinationLink)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            connection.setRequestProperty("Content-Length", "")
            connection.setRequestProperty("Content-Language", "en-US")

            connection.useCaches = false
            connection.doOutput = false

            if (connection.responseCode > 299) {
                return connection.errorStream
            }

            return connection.inputStream
        }.getOrElse {
            return null
        }
    }
}