package io.yousefessa.applicationupdater.util

import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

// todo: ClassNotFound is being thrown whenever this utility class (or so called "object")
//  is being accessed. Further research needs to be achieved to continue throughout the project
object ApplicationUtil {
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