package io.yousefessa.applicationupdater.destination

import io.yousefessa.applicationupdater.destination.adapter.DestinationAdapter

interface Destination {
    fun fileDestination(): String
    fun versionDestination(): String

    fun adapter(): DestinationAdapter
}