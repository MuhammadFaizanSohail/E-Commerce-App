package com.example.e_commerceapp.ui.component.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.e_commerceapp.databinding.ErrorDialogBinding

class ErrorDialog(context: Context) : Dialog(context) {

    private val binding by lazy {
        ErrorDialogBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setWindows()
        setBtnClick()
    }

    private fun setBtnClick() = with(binding) {
        okayButton.setOnClickListener {
            dismiss()
        }
    }

    fun setErrorMessage(msg : String) = with(binding){
        errorMessage.text = msg
    }

    private fun setWindows() {
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setLayout(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
    }

}