package com.moengage.machinecoding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.moengage.machinecoding.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {

    private val viewModel: OnboardingViewModel by activityViewModels()
    private lateinit var binding: FragmentOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentQuestion.observe(viewLifecycleOwner, Observer { question ->
            binding.questionText.text = question.text
            when (question.inputType) {
                "text" -> binding.answerInput.inputType = InputType.TYPE_CLASS_TEXT
                "number" -> binding.answerInput.inputType = InputType.TYPE_CLASS_NUMBER
                // Add more input types as needed
            }
        })

        viewModel.currentQuestionIndex.observe(viewLifecycleOwner, Observer { index ->
            binding.nextButton.visibility = if (viewModel.isLastQuestion()) View.GONE else View.VISIBLE
            binding.finishButton.visibility = if (viewModel.isLastQuestion()) View.VISIBLE else View.GONE
        })

        binding.nextButton.setOnClickListener {
            viewModel.nextQuestion()
        }

        binding.finishButton.setOnClickListener {
            // Navigate to SummaryFragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SummaryFragment())
                .commit()
        }
    }
}
