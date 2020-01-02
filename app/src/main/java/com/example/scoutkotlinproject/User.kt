package com.example.scoutkotlinproject
import android.content.Context
import android.content.SharedPreferences


class User (private val context: Context)
{
    companion object{
        const val PREFERENCE_FILE_KEY = "ScoutAppPreference"
        const val KEY_TEAMNAME= "prefUserNameKey"
        fun KEY_SCENARIO(n:Int):String = "prefUserKeyScenario$n"

    }

    var teamname : String = "Default"
    var scenarioSave: String = "0"
    val sharedPref : SharedPreferences = context.getSharedPreferences(User.PREFERENCE_FILE_KEY,Context.MODE_PRIVATE)



    fun SaveName(){
        with (sharedPref.edit()) {
            putString(KEY_TEAMNAME, teamname)
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

    fun LoadName(){
        teamname = sharedPref.getString(User.KEY_TEAMNAME, "Default")?:""
    }
    fun LoadScenario(n:Int) {
        scenarioSave = sharedPref.getString(KEY_SCENARIO(n), "$n")?:"0"
        if (scenarioSave == "")scenarioSave = "0"
    }

}