package com.example.jiugongge_draw_view.ui.view

import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jiugongge_draw_view.R
import com.example.jiugongge_draw_view.ui.model.LotteryData
import kotlinx.android.synthetic.main.view_jiugongge_draw_item.view.*

class JiugonggeDrawAdapter(private val listener: JiugonggeDrawListener) :
    RecyclerView.Adapter<JiugonggeDrawAdapter.JiugonggeDrawViewHolder>() {
    private var selectPosition = -1
    private var list = arrayListOf<LotteryData>()
    private var posMap =
        mapOf<Int, Int>(0 to 0, 1 to 1, 2 to 2, 3 to 5, 4 to 8, 5 to 7, 6 to 6, 7 to 3, 8 to 4)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JiugonggeDrawViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_jiugongge_draw_item, parent, false)
        return JiugonggeDrawViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: JiugonggeDrawViewHolder, position: Int) {
        holder.bind(list[position], position, posMap[selectPosition])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setSelectionPosition(selectPos: Int) {
        selectPosition = selectPos
        notifyDataSetChanged()

    }

    fun updateList(data: List<LotteryData>) {
        list.clear()
        list.addAll(sortList(data))
        notifyDataSetChanged()
    }

    private fun sortList(list: List<LotteryData>): List<LotteryData> {
        return arrayListOf<LotteryData>().apply {
            addAll(list)
        }.sortedBy {
            posMap[list.indexOf(it)]
        }
    }

    class JiugonggeDrawViewHolder(
        view: View,
        private val listener: JiugonggeDrawAdapter.JiugonggeDrawListener
    ) : RecyclerView.ViewHolder(view) {
        fun bind(data: LotteryData, displayPos: Int?, selectPos: Int?) {
            if (data.isDrawButton) {
                //set draw button view
                itemView.tv_lottery_name.text = "Draw"
                itemView.iv_lottery.setImageDrawable(itemView.context.getDrawable(R.mipmap.lottery_game))
                itemView.setOnClickListener {
                    listener.onClickDraw()
                }
                itemView.view_shadow.visibility = View.GONE
            } else {
                itemView.iv_lottery.setImageDrawable(itemView.context.getDrawable(data.lotterySrc))
                itemView.tv_lottery_name.text = data.lotteryName
                itemView.view_shadow.visibility =
                    if (displayPos == selectPos) View.VISIBLE else View.GONE
            }
        }
    }


    interface JiugonggeDrawListener {
        fun onClickDraw()
    }
}
