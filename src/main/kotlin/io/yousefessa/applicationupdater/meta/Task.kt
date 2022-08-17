package io.yousefessa.applicationupdater.meta

interface Task<C: TaskContext> {
    fun handle(context: C)
}