package com.example.zoho.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.zoho.model.Countries
import android.text.method.TextKeyListener.clear
import com.example.zoho.R


class ProductListAdapter(private val productList: ArrayList<Countries>) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {
    private var onItemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.adapter_product_list, p0, false)
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        System.out.println("the list in adapter is "+productList.size);
        return productList.size

    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.name?.text = productList[position].name
        viewHolder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(viewHolder.itemView, position)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tvName)
    }

    fun setFilter(newsArrayList: ArrayList<Countries>) {
        productList.clear()
        productList.addAll(newsArrayList)
        notifyDataSetChanged()
    }

    fun setItemClickListener(clickListener: ItemClickListener) {
        onItemClickListener = clickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}