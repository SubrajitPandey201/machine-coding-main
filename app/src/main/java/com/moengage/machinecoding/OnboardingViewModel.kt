package com.moengage.machinecoding

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moengage.machinecoding.model.Question
import com.moengage.machinecoding.repository.OnboardingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OnboardingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = OnboardingRepository(application)
    private val _currentQuestionIndex = MutableLiveData(0)
    private val _questions = MutableLiveData<List<Question>>()
    private val _currentQuestion = MutableLiveData<Question>()

    val currentQuestionIndex: LiveData<Int> = _currentQuestionIndex
    val questions: LiveData<List<Question>> = _questions
    val currentQuestion: LiveData<Question> = _currentQuestion

    init {
        fetchQuestions()
    }

    private fun fetchQuestions() {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedQuestions = repository.getQuestions()
            _questions.postValue(fetchedQuestions)
            _currentQuestion.postValue(fetchedQuestions.firstOrNull())
        }
    }

    fun nextQuestion() {
        _currentQuestionIndex.value?.let { currentIndex ->
            val newIndex = currentIndex + 1
            if (newIndex < _questions.value?.size ?: 0) {
                _currentQuestionIndex.value = newIndex
                _currentQuestion.value = _questions.value?.get(newIndex)
            }
        }
    }

    fun isLastQuestion(): Boolean {
        return _currentQuestionIndex.value == (_questions.value?.size?.minus(1) ?: 0)
    }
}
