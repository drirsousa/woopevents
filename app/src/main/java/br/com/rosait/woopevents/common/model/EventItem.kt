package br.com.rosait.woopevents.common.model

import java.io.Serializable

data class EventItem (
    var id: Int?,
    var title: String?,
    var description: String?,
    var date: Long?,
    var price: Double?,
    var image: String?,
    var longitude: Double?,
    var latitude: Double?
) : Serializable