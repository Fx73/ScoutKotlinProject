package com.example.scoutkotlinproject

import android.content.Context
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import java.security.AccessController.getContext

open class Scenario(private val context: Context,private val layout:RelativeLayout,public var prog : Int)
{
    val top = 180
    open fun Beggin ()
    {
        InstanceTextAt("Bienvenue ! \n Vous devez choisir un scenario",0)


    }



    open fun GetAns(S:String)
    {


    }

    fun InstanceTextAt(s:String,pos:Int)
    {
        val tv_dynamic = TextView(context)
        tv_dynamic.textSize = 20f
        tv_dynamic.text = s
        val r = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        r.topMargin = top+pos
        tv_dynamic.layoutParams = r
        layout?.addView(tv_dynamic)

    }

}