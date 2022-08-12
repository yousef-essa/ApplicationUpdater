package io.yousefessa.applicationupdater.destination

interface Destination {
    fun fileDestination(): String
    fun versionDestination(): String
}