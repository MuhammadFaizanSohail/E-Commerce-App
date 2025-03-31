package com.example.e_commerceapp.ui.component.dialog

import android.R
import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.e_commerceapp.databinding.ExitDialogBinding
import kotlin.system.exitProcess

class ExitDialog(private val activity: Activity) : Dialog(activity) {
    private val binding by lazy {
        ExitDialogBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setInitialViews()
    }

    private fun setInitialViews() {
        setWindow()
        setBtnClicks()
    }

    private fun setBtnClicks() {
        binding.apply {
            cancelButton.setOnClickListener {
                dismiss()
            }
            okayButton.setOnClickListener {
                dismiss()
                activity.finishAffinity()
                exitProcess(0)
            }
        }
    }

    private fun setWindow() {
        window?.setBackgroundDrawableResource(R.color.transparent)
        val width = (context.resources.displayMetrics.widthPixels * 0.9).toInt()
        window?.setLayout(width, ConstraintLayout.LayoutParams.WRAP_CONTENT)
    }
}