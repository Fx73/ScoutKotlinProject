package com.ufix.jeudepistekt

import android.content.Context
import android.text.Editable
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children

open class Scenario(protected val context: Context,protected val layout:RelativeLayout)
{
    lateinit var reader:EditText
    var prog = 0

    open fun Beggin ()
    {
        InstanceTextAt("Bienvenue ! \n Vous devez choisir un scenario \n Vous pouvez utiliser la fleche en bas a droite pour entrer un QR code",0)
    }



    open fun GetAns(S:String):Boolean
    {
        InstanceTextAt(S,0)
        return true
    }

    fun CheckProg(vararg p:Int):Boolean
    {
        for(e in p)
            if(e==prog)
                return true

        ShowToast("Vous etes peut-être perdu ! Ceci est l'etape "+p[0].toString()+", vous devez trouver l'etape "+prog.toString())
        return false
    }

    open fun RestoreSave(s:String,sVerif:Int = 0, lVerif:Int = 1):Boolean {
        if (s[0].toString().toInt() != sVerif || s.length != lVerif) {
            if(s[0].toString().toInt() == 0)
                Toast.makeText(context,"Creation d'une sauvegarde ...", Toast.LENGTH_LONG ).show()
            else
                Toast.makeText(context,"Attention ! La sauvegarde a l'air corrompue ! Tentative de récupération", Toast.LENGTH_LONG ).show()
            return false
        }
        return true
    }

    open fun SendSave():String
    {
        return "0"
    }


    open fun Reset()
    {
        prog=0
    }

    fun InstanceTextAt(s:String,pos:Int = 0)
    {
        val tv_dynamic = TextView(context)
        tv_dynamic.textSize = 22f
        tv_dynamic.text = s
        val r = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        r.topMargin = pos
        tv_dynamic.layoutParams = r
        layout.addView(tv_dynamic)
    }

    fun InstanceReaderAt(s:String ,pos:Int = 0):EditText
    {
        val et_dynamic = EditText(context)
        et_dynamic.textSize = 20f
        et_dynamic.setText(s)
        val r = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        r.topMargin = pos
        et_dynamic.layoutParams = r
        et_dynamic.setSingleLine()
        layout.addView(et_dynamic)
        return et_dynamic
    }
    fun InstanceButtonAt(s:String ,pos:Int = 0,f:(m: String) -> Unit,reader:Editable = EditText(context).text )
    {
        val b_dynamic = Button(context)
        b_dynamic.text = s
        b_dynamic.setOnClickListener{f(reader.toString())}
        val t = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        t.topMargin = pos
        b_dynamic.layoutParams = t
        layout.addView(b_dynamic)
    }

    fun InstanceImageAt(name:String,pos:Int = 0,size:Int=600)
    {
        val img_dynamic = ImageView(context)
        img_dynamic.setBackgroundResource(context.getResources().getIdentifier(name, "drawable", context.getPackageName()))
        val r = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        r.height = size
        r.setMargins(2,pos,2,0)
        img_dynamic.layoutParams = r
        layout.addView(img_dynamic)
        img_dynamic.bringToFront()
    }

    inline fun <reified T> DeleteAllOfTypeInLayout() {
        for (child in layout.children) {
            if (child is T)
                layout.removeView(child)
        }
    }

    open fun GetImage():Int
    {
        return android.R.drawable.ic_menu_help
    }

    fun String.toNormalCase() = this
        .trim()
        .toUpperCase()
        .replace('é','E',true)
        .replace('è','E',true)
        .replace('ê','E',true)
        .replace('ë','E',true)
        .replace('û','U',true)
        .replace('ù','U',true)
        .replace('â','A',true)
        .replace('à','A',true)
        .replace('ô','O',true)
        .replace('î','I',true)
        .replace('ï','I',true)
        .replace('ç','I',true)



    fun Boolean.toInt() = if (this) 1 else 0
    fun Int.toBoolean() = (this == 1)
    fun List<Boolean>.sum():Int {
        var sum = 0
        for(e in this)
            if(e)
                sum++
        return sum
    }

    fun ShowToast(s: String)= Toast.makeText(context,s, Toast.LENGTH_LONG ).show()

    fun ClearLayout()= layout.removeAllViews()
}