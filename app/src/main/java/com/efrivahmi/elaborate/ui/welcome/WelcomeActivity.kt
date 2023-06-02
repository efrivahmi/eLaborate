package com.efrivahmi.elaborate.ui.welcome

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.efrivahmi.elaborate.R
import com.efrivahmi.elaborate.databinding.ActivityWelcomeBinding
import com.efrivahmi.elaborate.ui.login.LoginActivity
import com.efrivahmi.elaborate.ui.register.RegisterActivity

class WelcomeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var animator: ObjectAnimator
    private var currentImageIndex = 0
    private val images = arrayOf(R.drawable.component1, R.drawable.component2, R.drawable.component3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener(this)
        binding.registerButton.setOnClickListener(this)

        startImageSlideshow()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.loginButton -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.registerButton -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun startImageSlideshow() {
        animator = ObjectAnimator.ofFloat(binding.imageView1, "translationX", 0f, -binding.imageView1.width.toFloat())
        animator.duration = 500
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.repeatCount = ObjectAnimator.INFINITE
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()

        binding.imageView1.postDelayed({
            currentImageIndex++
            if (currentImageIndex >= images.size)
                currentImageIndex = 0

            binding.imageView1.setImageResource(images[currentImageIndex])
            binding.imageView1.translationX = 0f

            animator.target = binding.imageView1
            animator.start()

            binding.imageView1.postDelayed({ startImageSlideshow() }, 3000)
        }, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.imageView1.removeCallbacks(null)
    }
}