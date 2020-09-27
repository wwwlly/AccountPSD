package com.kemp.acpsd

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kemp.acpsd.adapter.MainAdapter
import com.kemp.acpsd.bean.AccountPsd
import com.kemp.acpsd.db.DbManager
import com.kemp.acpsd.dialog.AccountDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_PERMISSION_STORAGE = 1;

    private val dbManager = DbManager.getInstance()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val toolbar: Toolbar = findViewById(R.id.tool_bar)
//        setSupportActionBar(toolbar)
        setSupportActionBar(tool_bar)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION_STORAGE
            )
        } else {
            dbManager.initDb(application)
            refreshList()
        }
    }

    private fun addAccount() {
        val addDialog = AccountDialog(this)
        addDialog.show()
        addDialog.setAddContent { dialog, data ->
            dialog.cancel()
            dbManager.add(data)
            Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show()
            refreshList()
        }
    }

    private fun initListView(list: List<AccountPsd>) {
        recyclerView = findViewById(R.id.recycler_view)

//        val layoutManager = LinearLayoutManager(this)
        val layoutManager = GridLayoutManager(this, 2)
        adapter = MainAdapter(this, list as ArrayList<AccountPsd>)
        adapter.setOnClickListener {
            showDetail(it)
        }
        adapter.setOnLongClickListener {
            confirmDelete(it)
        }
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun refreshList() {
        val list = dbManager.query();
        if (list == null || list.isEmpty()) {
            addAccount()
        } else {
            initListView(list)
        }
    }

    private fun showDetail(accountPsd: AccountPsd) {
        val detailDialog = AccountDialog(this)
        detailDialog.show()
        detailDialog.setDetailContent(accountPsd) { dialog, nowAccountPsd ->
            dialog.cancel()
            dbManager.set(nowAccountPsd)
            Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show()
            refreshList()
        }
    }

    private fun confirmDelete(accountPsd: AccountPsd) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("确定删除？")
            .setPositiveButton("确定") { dialog, _ ->
                dialog.cancel()
                dbManager.delete(accountPsd)

                adapter.list.remove(accountPsd)
                adapter.notifyDataSetChanged()
                refreshList()
            }
            .setNegativeButton("取消") { dialog, _ ->
                dialog.cancel()
            }
        builder.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> addAccount()
            R.id.action_setting -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dbManager.initDb(application)
                    refreshList()
                } else {
                    Toast.makeText(this, "请允许内存卡的读写权限", Toast.LENGTH_LONG).show()

                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ),
                        REQUEST_CODE_PERMISSION_STORAGE
                    )
                }
            }
        }
    }
}
