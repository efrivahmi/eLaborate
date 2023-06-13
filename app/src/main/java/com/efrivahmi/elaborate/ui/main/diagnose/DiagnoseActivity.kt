package com.efrivahmi.elaborate.ui.main.diagnose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.efrivahmi.elaborate.data.model.Diagnose
import com.efrivahmi.elaborate.data.model.UserModel
import com.efrivahmi.elaborate.data.response.DiagnoseResponse
import com.efrivahmi.elaborate.data.response.DiagnosisData
import com.efrivahmi.elaborate.databinding.ActivityDiagnoseBinding
import com.efrivahmi.elaborate.utils.ViewModelFactory

class DiagnoseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiagnoseBinding
    private lateinit var factory: ViewModelFactory
    private val diagnoseViewModel: DiagnoseViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnoseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        binding.interpretationButton.setOnClickListener {
            diagnoseViewModel.getPatient().observe(this) { patient: UserModel ->
                val userId = patient.userId
                val age = binding.inputage.text.toString().toInt()
                val sex = binding.inputsex.text.toString()
                val rbc = binding.inputrbc.text.toString().toDouble()
                val hgb = binding.inputhgb.text.toString().toDouble()
                val hct = binding.inputhct.text.toString().toDouble()
                val mcv = binding.inputmcv.text.toString().toDouble()
                val mch = binding.inputmch.text.toString().toDouble()
                val mchc = binding.inputmchc.text.toString().toDouble()
                val rdwCv = binding.inputrdcv.text.toString().toDouble()
                val wbc = binding.inputwbc.text.toString().toDouble()
                val neu = binding.inputneugra.text.toString().toDouble()
                val lym = binding.inputlymphs.text.toString().toDouble()
                val mo = binding.inputmo.text.toString().toDouble()
                val eos = binding.inputeos.text.toString().toDouble()
                val ba = binding.inputbasophils.text.toString().toDouble()

                val diagnose = Diagnose(
                    age = age,
                    sex = sex,
                    rbc = rbc,
                    hgb = hgb,
                    hct = hct,
                    mcv = mcv,
                    mch = mch,
                    mchc = mchc,
                    rdwCv = rdwCv,
                    wbc = wbc,
                    neu = neu,
                    lym = lym,
                    mo = mo,
                    eos = eos,
                    ba = ba
                )

                diagnoseViewModel.diagnoseClient(userId, diagnose)
            }
        }

        diagnoseViewModel.diagnoseResult.observe(this) { diagnoseResponse: DiagnoseResponse ->
            if (!diagnoseResponse.error) {
                val diagnosisData: DiagnosisData? = diagnoseResponse.diagnosisData
                if (diagnosisData != null) {
                    val diagnosisId = diagnosisData.diagnosisId
                    val age = diagnosisData.age
                    val sex = diagnosisData.sex
                    val rbc = diagnosisData.rbc
                    val hgb = diagnosisData.hgb
                    val hct = diagnosisData.hct
                    val mcv = diagnosisData.mcv
                    val mch = diagnosisData.mch
                    val mchc = diagnosisData.mchc
                    val rdwCv = diagnosisData.rdwCv
                    val wbc = diagnosisData.wbc
                    val neu = diagnosisData.neu
                    val lym = diagnosisData.lym
                    val mo = diagnosisData.mo
                    val eos = diagnosisData.eos
                    val ba = diagnosisData.ba

                    val result = "Diagnose Result: " +
                            "Diagnosis ID: $diagnosisId, " +
                            "Age: $age, " +
                            "Sex: $sex, " +
                            "RBC: $rbc, " +
                            "HGB: $hgb, " +
                            "HCT: $hct, " +
                            "MCV: $mcv, " +
                            "MCH: $mch, " +
                            "MCHC: $mchc, " +
                            "RDW-CV: $rdwCv, " +
                            "WBC: $wbc, " +
                            "NEU: $neu, " +
                            "LYM: $lym, " +
                            "MO: $mo, " +
                            "EOS: $eos, " +
                            "BA: $ba"

                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                }
            } else {
                val errorMessage = "Error: ${diagnoseResponse.message}"
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        showLoading()
        showToast()
    }

    private fun showLoading() {
        diagnoseViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar6.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun showToast() {
        diagnoseViewModel.toastText.observe(this) {
            it.getContentIfNotHandled()?.let { toastText ->
                Toast.makeText(
                    this, toastText, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}