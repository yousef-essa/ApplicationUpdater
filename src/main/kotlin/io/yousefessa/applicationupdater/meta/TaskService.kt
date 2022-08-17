package io.yousefessa.applicationupdater.meta

import java.util.concurrent.TimeUnit

interface TaskService {
    fun init()

    fun initialDelay(): Long
    fun delay(): Long
    fun timeUnit(): TimeUnit
}