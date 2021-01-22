package br.com.rosait.woopevents.common.di

import br.com.rosait.woopevents.viewmodel.EventViewModel
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(app: App)

    fun inject(eventViewModel: EventViewModel)
}