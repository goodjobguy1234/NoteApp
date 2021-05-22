package com.example.noteapp.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.Extension.dp
import com.example.noteapp.R
import com.example.noteapp.NoteDB.entity.NoteItem


class NoteItemListAdapter(private val items: List<NoteItem>?, val callBack: (Int) -> Unit): RecyclerView.Adapter<NoteItemListAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvContent = itemView.findViewById<TextView>(R.id.tvContent)

        fun bind(item: NoteItem?){
            tvTitle.text = item?.title
            tvContent.text = item?.content
            val constraintLayout = tvTitle.parent as ConstraintLayout

            item?.content?.let {
                if (it.trim() != "") {
                    with(tvContent) {
                        visibility = View.VISIBLE
                        text = it

                        ConstraintSet().apply {
                            clone(constraintLayout)
                            clear(tvTitle.id, ConstraintSet.BOTTOM)
                        }.applyTo(constraintLayout)

                    }
                } else {
                    with(tvContent) {
                        visibility = View.GONE

                        ConstraintSet().apply {
                           clone(constraintLayout)
                           connect(tvTitle.id, ConstraintSet.BOTTOM, constraintLayout.id, ConstraintSet.BOTTOM, 32.dp())
                       }.applyTo(constraintLayout)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_noteitem_view, parent, false)
        return ViewHolder(view).apply {
            this.itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callBack(adapterPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items?.get(position))
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

}