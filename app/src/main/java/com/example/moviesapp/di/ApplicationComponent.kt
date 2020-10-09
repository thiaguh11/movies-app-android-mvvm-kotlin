package com.example.moviesapp.di

import android.content.Context
import com.example.moviesapp.ui.di.MainComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, ViewModelBuilderModule::class, SubComponentsModule::class, ApiModule::class])
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context) : ApplicationComponent
    }

    fun mainComponent() : MainComponent.Factory

}

@Module(subcomponents = [MainComponent::class])
object SubComponentsModule