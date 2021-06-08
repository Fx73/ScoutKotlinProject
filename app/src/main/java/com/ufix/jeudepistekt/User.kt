package com.ufix.jeudepistekt

import android.content.Context
import android.content.SharedPreferences


class User (context: Context)
{
    companion object{
        const val PREFERENCE_FILE_KEY = "ScoutAppPreference"
        const val KEY_TEAMNAME= "prefUserNameKey"
        const val KEY_CUR_SCENARIO = "prefUserKeyCur"
        fun KEY_SCENARIO(n:Int):String = "prefUserKeyScenario$n"

    }

    var teamname : String = "Default"
    var scenarioSave: String = "0"
    var curscenario : Int = 0
    private val sharedPref : SharedPreferences = context.getSharedPreferences(PREFERENCE_FILE_KEY,Context.MODE_PRIVATE)



    fun SaveName(){
        with (sharedPref.edit()) {
            putString(KEY_TEAMNAME, teamname)
            commit()
        }
    }
    fun SaveCurrent(){
        with (sharedPref.edit()) {
            putInt(KEY_CUR_SCENARIO, curscenario)
            commit()
        }
    }
    fun SaveScenario(){
        val n:Int =scenarioSave[0].toString().toInt()
        with (sharedPref.edit()) {
            putString(KEY_SCENARIO(n), scenarioSave)
            commit()
        }
    }

    fun LoadInit(){
        teamname = sharedPref.getString(KEY_TEAMNAME, "Default")?:""
        curscenario = sharedPref.getInt(KEY_CUR_SCENARIO, 0)

    }
    fun LoadScenario(n:Int) {
        scenarioSave = sharedPref.getString(KEY_SCENARIO(n), "$n")?:"0"
        if (scenarioSave == "")scenarioSave = "0"
    }

}