package com.efrivahmi.elaborate.ui.main.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.efrivahmi.elaborate.R
import com.efrivahmi.elaborate.databinding.FragmentHomeBinding
import com.efrivahmi.elaborate.ui.main.diagnose.DiagnoseActivity
import com.efrivahmi.elaborate.ui.main.result.ResultActivity

class HomeFragment : Fragment() {
    private  var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        activityDiagnose()
        activityResult()
    }

    private fun activityResult() {
        binding.ivResult.setOnClickListener {
            activity?.let {
                val intent = Intent(it, ResultActivity::class.java)
                it.startActivity(intent)
            }
        }
    }

    private fun activityDiagnose() {
        binding.ivDiagnose.setOnClickListener {
            activity?.let {
                val intent = Intent (it, DiagnoseActivity::class.java)
                it.startActivity(intent)
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}