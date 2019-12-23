package com.example.scoutkotlinproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.*
import android.widget.RelativeLayout.CENTER_IN_PARENT
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import androidx.core.view.setMargins
import com.example.scoutkotlinproject.Scenarios.Scenario1
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var user : User
    lateinit var maintv: TextView
    lateinit var layout :RelativeLayout
    lateinit var scenario : Scenario




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        maintv=findViewById(R.id.TextViewTeam)
        layout = findViewById<RelativeLayout>(R.id.contentLayout)
        scenario = Scenario(this,layout,0)

        //Loading User
        val sharedPref : SharedPreferences = applicationContext.getSharedPreferences(User.PREFERENCE_FILE_KEY,Context.MODE_PRIVATE)
        val teamname = sharedPref.getString(User.KEY_TEAMNAME, "Default")?:""
        val progression = sharedPref.getInt(User.KEY_PROG, 0)
        user = User(progression,teamname,sharedPref)

        maintv.text = teamname
        fab.setOnClickListener {ScanQr() }


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        menuInflater.inflate(R.menu.menu_teamname, menu)
        menuInflater.inflate(R.menu.menu_scenario, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> MyOptions()
            R.id.teamnamechange ->changename()
            R.id.scenario ->ChooseScenario()
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun ChooseScenario():Boolean
    {
        layout.removeAllViews()

        val b_dynamic = Button(this)
        b_dynamic.text = "Scenario1"
        b_dynamic.setOnClickListener{ ChooseScenarioValidate(b_dynamic.text.toString())}
        val t = RelativeLayout.LayoutParams(MATCH_PARENT,WRAP_CONTENT)
        t.topMargin = 200
        b_dynamic.layoutParams = t
        layout?.addView(b_dynamic)


        return true
    }

    fun ChooseScenarioValidate(s : String)
    {
        when(s) {
            "Scenario1" -> scenario = Scenario1(this,layout,user.progression)
            else ->  Toast.makeText(this, "Oups ! Erreur !", Toast.LENGTH_LONG).show()
        }
        layout.removeAllViews()

        scenario.Beggin()
    }



    fun ScanQr()
    {
        val integrator = IntentIntegrator(this@MainActivity)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Scan")
        integrator.setCameraId(0)
        integrator.setBeepEnabled(false)
        integrator.setBarcodeImageEnabled(false)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Log.e("Scan*******", "Cancelled scan")
            } else {
                Log.e("Scan", "Scanned")
                maintv.setText(result.contents)
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
            }
        } else { // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


    fun changename(): Boolean
    {
        layout.removeAllViews()


        val tv_dynamic = TextView(this)
        tv_dynamic.textSize = 20f
        tv_dynamic.text = "Votre nom d'equipe :"
        val r = RelativeLayout.LayoutParams(MATCH_PARENT,WRAP_CONTENT)
        r.topMargin = 200
        tv_dynamic.layoutParams = r
        layout?.addView(tv_dynamic)

        val et_dynamic = EditText(this)
        et_dynamic.textSize = 20f
        et_dynamic.setText(user.teamname)
        val s = RelativeLayout.LayoutParams(MATCH_PARENT,WRAP_CONTENT)
        s.topMargin = 400
        et_dynamic.layoutParams = s
        layout?.addView(et_dynamic)

        val b_dynamic = Button(this)
        b_dynamic.text = "Ok"
        b_dynamic.setOnClickListener{ changenamevalidate(et_dynamic.text.toString())}
        val t = RelativeLayout.LayoutParams(MATCH_PARENT,WRAP_CONTENT)
        t.topMargin = 800
        b_dynamic.layoutParams = t
        layout?.addView(b_dynamic)

        return true
    }
    fun changenamevalidate(s:String): Boolean
    {
        user.teamname = s
        user.Save()
        layout.removeAllViews()

        maintv.text = s


        return true
    }



    fun MyOptions():Boolean
    {
        return true
    }

}
