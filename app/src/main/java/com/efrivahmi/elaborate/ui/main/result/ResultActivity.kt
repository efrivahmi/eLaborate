package com.efrivahmi.elaborate.ui.main.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.efrivahmi.elaborate.data.model.Diagnose
import com.efrivahmi.elaborate.data.response.DiagnoseResponse
import com.efrivahmi.elaborate.databinding.ActivityResultBinding
import com.efrivahmi.elaborate.utils.ViewModelFactoryMl

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var factory: ViewModelFactoryMl
    private val resultViewModel: ResultViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactoryMl.getInstance(this)

        resultViewModel.diagnoseResult.observe(this) { diagnoseResponse ->
            val dataDiagnosisLiveData = resultViewModel.getDataDiagnosis()
            dataDiagnosisLiveData.observe(this) { diagnosis ->
                if (diagnosis != null) {
                    useDiagnosisData(diagnosis)
                }
            }
            handleDiagnoseResponse(diagnoseResponse)
        }

        val diagnosisIdLiveData = resultViewModel.getDiagnosisId()
        val userIdLiveData = resultViewModel.getUserId()

        userIdLiveData.observe(this) { userModel ->
            val userId = userModel.userId
            diagnosisIdLiveData.observe(this) { dResponse ->
                val diagnosisId = dResponse.diagnosisId

                resultViewModel.getResult(userId, diagnosisId)
            }
        }

        showLoading()
        showToast()
    }

    private fun showLoading() {
        resultViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar9.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun handleDiagnoseResponse(diagnoseResponse: DiagnoseResponse) {
        val diagnosis = diagnoseResponse.input_data

    }

    private fun useDiagnosisData(diagnosis: Diagnose) {
        binding.tvNeuGraResult.text = diagnosis.neu.toString()
        binding.tvLymphsResult.text = diagnosis.lym.toString()
        binding.tvMonocytesResult.text = diagnosis.mo.toString()
        binding.tvEosResult.text = diagnosis.eos.toString()
        binding.tvBasosResult.text = diagnosis.ba.toString()
        binding.tvAgePatient.text = diagnosis.age.toString()
        binding.tvSexPatient.text = diagnosis.sex.toString()
        binding.tvRbcResult.text = diagnosis.rbc.toString()
        binding.tvHgbResult.text = diagnosis.hgb.toString()
        binding.tvHctResult.text = diagnosis.hct.toString()
        binding.tvMcvResult.text = diagnosis.mcv.toString()
        binding.tvMchResult.text = diagnosis.mch.toString()
        binding.tvMchcResult.text = diagnosis.mchc.toString()
        binding.tvRdwCvResult.text = diagnosis.rdw_cv.toString()
        binding.tvWbcResult.text = diagnosis.wbc.toString()
    }

    private fun showToast() {
        resultViewModel.toastText.observe(this) {
            it.getContentIfNotHandled()?.let { toastText ->
                Toast.makeText(
                    this, toastText, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}