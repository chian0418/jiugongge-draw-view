package com.example.jiugongge_draw_view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.jiugongge_draw_view.R
import com.example.jiugongge_draw_view.ui.model.LotteryData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        val fakeData = listOf(
            LotteryData(R.mipmap.money, "$10"),
            LotteryData(R.mipmap.money, "$20"),
            LotteryData(R.mipmap.money, "$30"),
            LotteryData(R.mipmap.red_envelope, "$120"),
            LotteryData(R.mipmap.money, "$50"),
            LotteryData(R.mipmap.red_envelope, "$160"),
            LotteryData(R.mipmap.money, "$70"),
            LotteryData(R.mipmap.red_envelope, "$180"),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        draw_view.submitList(fakeData)

        draw_view.drawResult {
            Log.d("alice",it.toString())
        }
    }
}