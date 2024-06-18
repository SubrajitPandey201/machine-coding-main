package com.moengage.machinecoding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.moengage.machinecoding.databinding.FragmentSummaryBinding

class SummaryFragment : Fragment() {

    private val viewModel: OnboardingViewModel by activityViewModels()
    private lateinit var binding: FragmentSummaryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fetch and display the summary of questions and answers
        viewModel.questions.observe(viewLifecycleOwner, { questions ->
            for (question in questions) {
                val questionView = TextView(context).apply { text = "Question: ${question.text}" }
                val answerView = TextView(context).apply { text = "Answer: ${question.inputType}" } // Replace with actual answer
                binding.summaryLayout.addView(questionView)
                binding.summaryLayout.addView(answerView)
            }
        })
    }
}
