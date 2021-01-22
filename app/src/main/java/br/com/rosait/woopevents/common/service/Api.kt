package br.com.rosait.woopevents.common.service

import br.com.rosait.woopevents.common.model.EventResult
import br.com.rosait.woopevents.common.model.EventItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

    @GET("events")
    fun getEvents() : Call<List<EventItem>>

    @GET("events/{id}")
    fun getEvent(@Path("id") id: Int) : Call<EventItem>

    @POST("checkin")
    fun doCheckin(@Body parameters: HashMap<String, Any>) : Call<EventResult>
}