package com.kemp.acpsd.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import com.kemp.acpsd.R
import kotlinx.android.synthetic.main.dialog_edit_account.*

class EditAccountDialog(context: Context) : Dialog(context) {

    private lateinit var etText: EditText
    private lateinit var onSaveListener: (Dialog, String) -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_edit_account)
        setSize()

        etText = findViewById(R.id.et_account)

        btn_save.setOnClickListener {
            onSaveListener.invoke(this, etText.text.toString())
        }
        btn_cancel.setOnClickListener {
            cancel()
        }
    }

    private fun setSize() {
        val outSize = Point()
        window?.windowManager?.defaultDisplay?.getSize(outSize)
        val lp: WindowManager.LayoutParams? = window?.attributes
        lp?.width = outSize.x * 4 / 5
        lp?.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.attributes = lp
    }

    fun setText(text: String?) {
        etText.setText(text)
        if (text != null) {
            etText.setSelection(text.length)
        }
    }

    fun setOnSaveListener(onSaveListener: (Dialog, String) -> Unit) {
        this.onSaveListener = onSaveListener
    }
}