package br.com.rosait.woopevents.common.service

import br.com.rosait.woopevents.common.model.EventResult
import br.com.rosait.woopevents.common.model.EventItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Service {

    fun getEvents(callback:(List<EventItem>?) -> Unit) {
        val endpoint =
            RestManager.getEndpoint()

        val call = endpoint.getEvents()

        call.enqueue(object : Callback<List<EventItem>> {
            override fun onResponse(call: Call<List<EventItem>>, response: Response<List<EventItem>>) {
                if(response.isSuccessful) {
                    response.body()?.let {
                        callback(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<EventItem>>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getEvent(id: Int, callback: (EventItem?) -> Unit) {
        val endpoint = RestManager.getEndpoint()

        val call = endpoint.getEvent(id)

        call.enqueue(object : Callback<EventItem> {
            override fun onResponse(call: Call<EventItem>, response: Response<EventItem>) {
                if(response.isSuccessful) {
                    response.body()?.let {
                        callback(it)
                    }
                }
            }

            override fun onFailure(call: Call<EventItem>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun doCheckin(idEvent: Int, name: String, email: String, callback:(Boolean, String?) -> Unit) {
        val endpoint = RestManager.getEndpoint()

        val parameters = HashMap<String, Any>()
        parameters.put("eventId", idEvent)
        parameters.put("name", name)
        parameters.put("email", email)

        val call = endpoint.doCheckin(parameters)

        call.enqueue(object : Callback<EventResult> {
            override fun onResponse(call: Call<EventResult>, response: Response<EventResult>) {
                if(response.isSuccessful) {
                    response.body()?.let {
                        it.code?.let {
                            if(it.equals("200"))
                                callback(true, null)
                            else
                                callback(false, null)
                        }
                    }
                } else {
                    callback(false, null)
                }
            }

            override fun onFailure(call: Call<EventResult>, t: Throwable) {
                callback(false, t.message)
            }
        })



    }
}