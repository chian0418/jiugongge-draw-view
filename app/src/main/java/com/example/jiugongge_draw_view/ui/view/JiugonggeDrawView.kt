package com.example.jiugongge_draw_view.ui.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jiugongge_draw_view.R
import com.example.jiugongge_draw_view.ui.model.LotteryData
import kotlin.random.Random

/**
 * @author Ryan
 * @Date 2022-01-04
 */
enum class DrawStatus {
    PREPARE,
    DRAWING
}

class JiugonggeDrawView(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {
    private val animator = ValueAnimator()
    private var animatorDuration = 2000L //Default
    private var luckyIndex: Int? = null
    private val adapter: JiugonggeDrawAdapter
    private var listener: JiugonggeDrawAdapter.JiugonggeDrawListener
    private var result: ((luckIndex: Int) -> Unit)? = null
    private var status: DrawStatus = DrawStatus.PREPARE

    init {
        animator.duration = animatorDuration
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.addUpdateListener {
            val position = it.animatedValue as Int
            setCurrentPosition(position % 8)
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {/*ignore*/
            }

            override fun onAnimationEnd(p0: Animator?) {
                status = DrawStatus.PREPARE
                luckyIndex?.run {
                    result?.invoke(this)
                }
            }

            override fun onAnimationCancel(p0: Animator?) {/*ignore*/
            }

            override fun onAnimationRepeat(p0: Animator?) {/*ignore*/
            }
        })
        listener = object : JiugonggeDrawAdapter.JiugonggeDrawListener {
            override fun onClickDraw() {
                if (status == DrawStatus.PREPARE) {
                    val random = ((2 * 8 + Random.nextInt(0, 7)))
                    luckyIndex = random % 8
                    animator.setIntValues(0, random)
                    animator.start()
                    status = DrawStatus.DRAWING
                }
            }
        }
        adapter = JiugonggeDrawAdapter(listener)
        setAdapter(adapter)
        layoutManager = object : GridLayoutManager(context, 3) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator.cancel()
    }

    private fun setCurrentPosition(position: Int) {
        //刷新当前所在位置
        adapter.setSelectionPosition(position)
    }

    fun drawResult(result: (luckIndex: Int) -> Unit) {
        this.result = result
    }

    fun submitList(list: List<LotteryData>) {
        if (list.size != 8) throw  IllegalArgumentException("list size must = 8")
        adapter.updateList(addButtonItem(list))
    }

    fun setAnimatorDuration(duration: Long) {
        animatorDuration = duration
    }

    private fun addButtonItem(list: List<LotteryData>): ArrayList<LotteryData> {
        return arrayListOf<LotteryData>().apply {
            addAll(list)
            add(LotteryData(R.mipmap.red_envelope, "", true))
        }
    }

}