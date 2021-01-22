package br.com.rosait.woopevents.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.rosait.woopevents.common.di.App
import br.com.rosait.woopevents.common.di.App.Companion.firstInitViewModel
import br.com.rosait.woopevents.common.di.AppModule
import br.com.rosait.woopevents.common.di.DaggerAppComponent
import br.com.rosait.woopevents.common.model.EventItem
import br.com.rosait.woopevents.common.service.Service
import javax.inject.Inject

class EventViewModel : ViewModel() {

    companion object {
        private lateinit var _viewModel: EventViewModel
        fun getInstance() : EventViewModel {
            if(firstInitViewModel || !::_viewModel.isInitialized) {
                firstInitViewModel = false
                _viewModel = EventViewModel()
            }
            return _viewModel
        }
    }

    private val component by lazy { DaggerAppComponent.builder().appModule(
       AppModule(
           App()
       )
   ).build() }

    @Inject
    lateinit var mService: Service

    init {
        component.inject(this)
    }

    val mResultMutable: MutableLiveData<List<EventItem>> = MutableLiveData()

    val mEvent: MutableLiveData<EventItem> = MutableLiveData()

    fun getEvents() : LiveData<List<EventItem>> {
        mService.getEvents { eventList ->
            mResultMutable.value = eventList
        }

        return mResultMutable
    }

    fun getEvent(id: Int) : LiveData<EventItem> {
        mService.getEvent(id) { event ->
            mEvent.value = event
        }

        return mEvent
    }

    fun doCheckin(idEvent: Int, name: String, email: String, callback:(Boolean, String?) -> Unit) {
        mService.doCheckin(idEvent, name, email) { isSuccessfull: Boolean, errorMessage: String? ->
            callback(isSuccessfull, errorMessage)
        }
    }
}