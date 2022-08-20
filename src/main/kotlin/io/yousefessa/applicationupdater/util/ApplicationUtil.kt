package io.yousefessa.applicationupdater.util

import io.yousefessa.applicationupdater.destination.Destination
import org.json.simple.JSONObject
import org.json.simple.JSONValue
import org.tinylog.kotlin.Logger
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

object ApplicationUtil {
    fun isRemoteVersionNewer(destination: Destination, localVersion: String):
            Pair<String, Boolean> {
        val remoteVersion = destination.adapter().fetchVersion()

        Logger.debug("remoteVersion: $remoteVersion")
        val isRemoteVersionNewer =
            remoteVersion.isNotEmpty() && remoteVersion > localVersion

        return Pair(remoteVersion, isRemoteVersionNewer)
    }

    fun getInputStreamFrom(destinationLink: String): InputStream? {
        runCatching {
            val url = URL(destinationLink)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

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

    private fun getJsonInputStreamFrom(destinationLink: String): InputStream? {
        runCatching {
            val url = URL(destinationLink)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            connection.setRequestProperty("Accept", "application/vnd.github+json")
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

    fun versionFrom(versionDestination: String): String {
        val inputStream = getInputStreamFrom(versionDestination) ?: return ""

        inputStream.bufferedReader().use { reader ->
            return reader.readLine()
        }
    }

    fun versionFrom(versionDestination: String, key: String): String {
        val inputStream = getJsonInputStreamFrom(versionDestination) ?: return ""

        inputStream.bufferedReader().use { reader ->
            val response = reader.lines().collect(Collectors.joining())
            val responseJson = JSONValue.parse(response) as JSONObject

            val version = responseJson[key] as String
            Logger.debug("$key = $version")

            return version
        }
    }
}