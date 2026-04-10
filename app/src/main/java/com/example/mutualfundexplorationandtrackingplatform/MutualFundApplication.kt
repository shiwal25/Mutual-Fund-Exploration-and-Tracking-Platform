package com.example.mutualfundexplorationandtrackingplatform

import android.app.Application
import com.example.mutualfundexplorationandtrackingplatform.data.AppContainer
import com.example.mutualfundexplorationandtrackingplatform.data.AppDataContainer

class MutualFundApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}