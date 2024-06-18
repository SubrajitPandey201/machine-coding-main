package com.moengage.machinecoding.repository

import android.content.Context
import com.moengage.machinecoding.model.Question
import com.moengage.machinecoding.network.OnBoardingNetworkManager

class OnboardingRepository(context: Context) {
    private val networkManager = OnBoardingNetworkManager(context)

    fun getQuestions(): List<Question> {
        return networkManager.fetchQuestions()
    }
}
