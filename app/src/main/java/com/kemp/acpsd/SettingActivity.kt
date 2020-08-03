package com.kemp.acpsd

import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.kemp.acpsd.dialog.EditAccountDialog
import com.kemp.acpsd.utils.Constants
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_setting.tv_unify_account

class SettingActivity : AppCompatActivity() {

    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        setSupportActionBar(tool_bar)
        supportActionBar?.title = "设置"

        sp = getSharedPreferences(Constants.SP_NAME, MODE_PRIVATE)
        val account = sp.getString(Constants.SP_ACCOUNT, null)
        tv_unify_account.text = account

        ll_unify_account.setOnClickListener {
            showEditAccountDialog(account)
        }
    }

    private fun showEditAccountDialog(account: String?) {
        val dialog = EditAccountDialog(this)
        dialog.show()
        dialog.setText(account)
        dialog.setOnSaveListener { d, text ->
            if (TextUtils.isEmpty(text.trim())) {
                Toast.makeText(this, "请填写统一账号", Toast.LENGTH_LONG).show()
            } else {
                d.cancel()
                sp.edit {
                    putString(Constants.SP_ACCOUNT, text)
                }
                tv_unify_account.text = text
            }
        }
    }
}