package com.example.scoutkotlinproject
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences


class User (progression : Int,var teamname : String, val sharedPref : SharedPreferences)
{
    var progression : Int = progression
        set(value){print("Bravo");field++}

    companion object{
        const val PREFERENCE_FILE_KEY = "ScoutAppPreference"
        const val KEY_TEAMNAME= "prefUserNameKey"
        const val KEY_PROG = "prefUserProg"
    }



    fun Save(){
        with (sharedPref.edit()) {
            putString(KEY_TEAMNAME, teamname)
            putInt(KEY_PROG, progression)
            commit()
        }
    }

}