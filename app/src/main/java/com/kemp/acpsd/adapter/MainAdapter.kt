package com.kemp.acpsd.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kemp.acpsd.R
import com.kemp.acpsd.bean.AccountPsd

class MainAdapter(val context: Context, val list: ArrayList<AccountPsd>) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private lateinit var onClickListener: (AccountPsd) -> Unit

    private lateinit var onLongClickListener: (AccountPsd) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_main, parent, false)
        return MainViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = list[position]
        holder.setContent(item)
        holder.setOnClickListener(onClickListener)
        holder.setOnLongClickListener(onLongClickListener)
    }

    class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val tvName: TextView = v.findViewById(R.id.tv_name)
        private val tvPsd: TextView = v.findViewById(R.id.tv_psd)
        private lateinit var itemBean: AccountPsd

        fun setContent(itemBean: AccountPsd) {
            this.itemBean = itemBean
            setName(itemBean.name)
            setPSD(itemBean.passWord)
        }

        private fun setName(name: String) {
            tvName.text = name
        }

        private fun setPSD(psd: String) {
            tvPsd.text = psd
        }

        fun setOnClickListener(onClickListener: (AccountPsd) -> Unit) {
            itemView.setOnClickListener {
                onClickListener.invoke(itemBean)
            }
        }

        fun setOnLongClickListener(onLongClickListener: (AccountPsd) -> Unit) {
            itemView.setOnLongClickListener {
                onLongClickListener.invoke(itemBean)
                return@setOnLongClickListener true
            }
        }
    }

    fun setOnClickListener(onClickListener: (AccountPsd) -> Unit) {
        this.onClickListener = onClickListener
    }

    fun setOnLongClickListener(onLongClickListener: (AccountPsd) -> Unit) {
        this.onLongClickListener = onLongClickListener
    }
}