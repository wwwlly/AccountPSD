package com.kemp.acpsd.dialog

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.kemp.acpsd.R
import com.kemp.acpsd.bean.AccountPsd
import com.kemp.acpsd.utils.Constants

/**
 * 添加、详情、编辑
 */
class AccountDialog(context: Context) : Dialog(context) {

    private lateinit var etName: EditText
    private lateinit var etAccount: EditText
    private lateinit var etPsd: EditText
    private lateinit var etRemarks: EditText
    private lateinit var tvUnifyAccount: TextView
    private lateinit var btnSave: Button
    private lateinit var btnClose: Button

//    init {
//        setSize()
//    }

    private lateinit var sp: SharedPreferences

    private var unifyAccount: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add)
        setSize()

        etName = findViewById(R.id.et_name)
        etAccount = findViewById(R.id.et_account)
        etPsd = findViewById(R.id.et_psd)
        etRemarks = findViewById(R.id.et_remarks)
        tvUnifyAccount = findViewById(R.id.tv_unify_account)
        btnSave = findViewById(R.id.btn_save)
        btnClose = findViewById(R.id.btn_close)

        btnClose.setOnClickListener {
            cancel()
        }

        sp = context.getSharedPreferences(Constants.SP_NAME, AppCompatActivity.MODE_PRIVATE)
        unifyAccount = sp.getString(Constants.SP_ACCOUNT, null)
    }

    private fun setSize() {
        val outSize = Point()
        window?.windowManager?.defaultDisplay?.getSize(outSize)
//        Log.d("wwww", "x:" + outSize.x + " y:" + outSize.y)
        val lp: WindowManager.LayoutParams? = window?.attributes
        lp?.width = outSize.x * 4 / 5
        lp?.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.attributes = lp
//        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    fun setAddContent(saveListener: (Dialog, AccountPsd) -> Unit) {
        setEditAble(true)

        setUnifyAccount()

        btnSave.text = "保存"
        btnSave.setOnClickListener {
            clickSave(saveListener)
        }
    }

    fun setModifyContent(modifyListener: (Dialog, AccountPsd) -> Unit) {
        setEditAble(true)

        setUnifyAccount()

        btnSave.text = "保存"
        btnSave.setOnClickListener {
            clickSave(modifyListener)
        }
    }

    fun setDetailContent(accountPsd: AccountPsd, modifyListener: (Dialog, AccountPsd) -> Unit) {
        setEditAble(false)
        etName.setText(accountPsd.name)
        etAccount.setText(accountPsd.account)
        etPsd.setText(accountPsd.passWord)
        etRemarks.setText(accountPsd.remarks)

        tvUnifyAccount.visibility = GONE

        btnSave.text = "修改"
        btnSave.setOnClickListener {
            setModifyContent(modifyListener)
        }
    }

    private fun setEditAble(editAble: Boolean) {
        setEditAble(etName, editAble)
        setEditAble(etAccount, editAble)
        setEditAble(etPsd, editAble)
        setEditAble(etRemarks, editAble)
    }

    private fun setEditAble(editText: EditText, editAble: Boolean) {
        if (editAble) {
            editText.isFocusable = true
            editText.isFocusableInTouchMode = true
//            editText.requestFocus()
        } else {
            editText.isFocusable = false
            editText.isFocusableInTouchMode = false
        }
    }

    private fun clickSave(listener: (Dialog, AccountPsd) -> Unit) {
        val name = etName.text.toString()
        val account = etAccount.text.toString()
        val psd = etPsd.text.toString()
        val remarks = etRemarks.text.toString()

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(account) && !TextUtils.isEmpty(psd)) {
            val data = AccountPsd()
            data.name = name
            data.account = account
            data.passWord = psd
            data.remarks = remarks

            listener.invoke(this, data)

            if (unifyAccount.isNullOrBlank()) {
                showAddUnifyAccountDialog(account)
            }
        } else {
            Toast.makeText(context, "填写主要信息", Toast.LENGTH_LONG).show()
        }
    }

    private fun setUnifyAccount() {
        if (!unifyAccount.isNullOrBlank()) {
            tvUnifyAccount.visibility = VISIBLE
            tvUnifyAccount.setOnClickListener {
                etAccount.setText(unifyAccount)
                etAccount.setSelection(etAccount.text.length)
            }
        } else {
            tvUnifyAccount.visibility = GONE
        }
    }

    private fun showAddUnifyAccountDialog(account: String) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("将$account 设置为统一账号？")
            .setPositiveButton("确定") { _, _ ->
                sp.edit {
                    putString(Constants.SP_ACCOUNT, account)
                }
            }
            .setNegativeButton("取消", null)
        builder.create().show()
    }

    enum class TYPE {
        ADD, MODIFY, DETAIL
    }
}