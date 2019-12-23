package com.example.scoutkotlinproject.Scenarios

import android.content.Context
import android.widget.RelativeLayout
import com.example.scoutkotlinproject.Scenario

class Scenario1 (c: Context,l:RelativeLayout,p:Int ) : Scenario(c,l,p)
{
    override fun Beggin()
    {
        InstanceTextAt("Cecl est le scenario 1.\n Bienvenue.\n Le contenu est la !!", 200)
    }


}