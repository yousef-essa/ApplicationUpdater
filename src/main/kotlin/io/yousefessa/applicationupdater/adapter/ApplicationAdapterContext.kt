package io.yousefessa.applicationupdater.adapter

import io.yousefessa.applicationupdater.meta.TaskContext
import java.io.InputStream
import java.net.URLConnection

class ApplicationAdapterContext(
    val connection: URLConnection,
    val inputStream: InputStream,
): TaskContext()