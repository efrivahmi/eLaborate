package com.efrivahmi.elaborate.ui.main.diagnose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.efrivahmi.elaborate.data.model.Diagnose
import com.efrivahmi.elaborate.data.response.DiagnoseResponse
import com.efrivahmi.elaborate.databinding.ActivityDiagnoseBinding
import com.efrivahmi.elaborate.ui.main.MainActivity
import com.efrivahmi.elaborate.utils.ViewModelFactoryMl

class DiagnoseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiagnoseBinding
    private lateinit var factory: ViewModelFactoryMl
    private val diagnoseViewModel: DiagnoseViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnoseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactoryMl.getInstance(this)

        binding.interpretationButton.setOnClickListener {
                val age = binding.inputage.text.toString().toInt()
                val sex = binding.inputsex.text.toString().toInt()
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
                    rdw_cv = rdwCv,
                    wbc = wbc,
                    neu = neu,
                    lym = lym,
                    mo = mo,
                    eos = eos,
                    ba = ba
                )

                diagnoseViewModel.diagnoseClient(diagnose)
                showToast()
                showLoading()
                moveAction()
            }

        diagnoseViewModel.diagnoseResult.observe(this) { diagnoseResponse: DiagnoseResponse ->
            val prediction = diagnoseResponse.prediction
            val result = "Diagnose Result: Prediction - $prediction"
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
        }
    }

    private fun moveAction(){
        val intent = Intent(this@DiagnoseActivity, MainActivity::class.java )
        startActivity(intent)
        finish()
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