package com.example.mutualfundexplorationandtrackingplatform

import android.app.Application
import com.example.mutualfundexplorationandtrackingplatform.di.AppContainer
import com.example.mutualfundexplorationandtrackingplatform.di.AppDataContainer

class MutualFundApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}