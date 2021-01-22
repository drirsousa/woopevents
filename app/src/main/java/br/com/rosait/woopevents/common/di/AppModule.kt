package br.com.rosait.woopevents.common.di

import br.com.rosait.woopevents.common.service.Service
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: App) {

    @Provides @Singleton
    fun provideApplication() = app

    @Provides
    fun provideService() : Service {
        return Service()
    }
}