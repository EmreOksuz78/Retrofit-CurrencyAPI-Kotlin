package com.emreoksuz.retrofitrxjavakotlin.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.emreoksuz.retrofitrxjavakotlin.R
import com.emreoksuz.retrofitrxjavakotlin.model.CryptoModel

class RecyclerViewAdapter(private val cryptoList : ArrayList<CryptoModel>,private val listener:Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {


    interface Listener {
        fun onItemClick(cryptoModel: CryptoModel)
    }

    private val colors : Array<String> = arrayOf("#8a2a3a","#8c1aff","#ff035c","#c7bfa6","#35031f","#6699cc","#c7ab32","#f8b71d")

    class RowHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(cryptoModel: CryptoModel,colors:Array<String>,position: Int,listener:Listener){

            itemView.setOnClickListener {
                listener.onItemClick(cryptoModel)
            }
            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))
            val textCurrency = itemView.findViewById<TextView>(R.id.text_name)
            val textPrice = itemView.findViewById<TextView>(R.id.text_price)
            textCurrency.text=cryptoModel.currency
            textPrice.text= cryptoModel.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent,false)
        return RowHolder(view)
    }

    override fun getItemCount(): Int {
        return cryptoList.count()
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(cryptoList[position],colors,position,listener)
    }
}