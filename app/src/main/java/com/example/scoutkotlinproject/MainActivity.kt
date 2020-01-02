package com.example.scoutkotlinproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.scoutkotlinproject.Scenarios.Scenario1
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var maintv: TextView
    lateinit var layout : RelativeLayout
    lateinit var mlayout : LinearLayout
    lateinit var nlayout : LinearLayout
    lateinit var clayout : LinearLayout
    lateinit var scenario : Scenario
    lateinit var mappic : ImageView
    lateinit var user : User




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //Setting up Globals
        maintv=findViewById(R.id.TextViewTeam)
        layout = findViewById<RelativeLayout>(R.id.contentLayout)
        mlayout = findViewById<LinearLayout>(R.id.mapLayout)
        mappic = findViewById<ImageView>(R.id.imageViewMap)
        nlayout = findViewById<LinearLayout>(R.id.nameLayout)
        clayout = findViewById<LinearLayout>(R.id.codeLayout)
        scenario = Scenario(this,layout)

        //Loading User
        user = User(this)
        user.LoadName()
        maintv.text = user.teamname
        findViewById<EditText>(R.id.editTextName).setText(user.teamname)


        findViewById<Button>(R.id.buttonName).setOnClickListener {ChangenameValidate(findViewById<EditText>(R.id.editTextName).text.toString())}
        findViewById<Button>(R.id.buttonCode).setOnClickListener {EnterCodeValidate(findViewById<EditText>(R.id.editTextCode).text.toString())}
        fab.setOnClickListener {ScanQr() }
        fab2.setOnClickListener{PrintMap()}
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        menuInflater.inflate(R.menu.menu_teamname, menu)
        menuInflater.inflate(R.menu.menu_code,menu)
        menuInflater.inflate(R.menu.menu_scenario, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> MyOptions()
            R.id.code -> EnterCode()
            R.id.teamnamechange ->changename()
            R.id.scenario ->ChooseScenario()
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun ChooseScenario():Boolean
    {
        layout.removeAllViews()

        val b_dynamic = Button(this)
        b_dynamic.text = "Kalte"
        b_dynamic.setOnClickListener{ ChooseScenarioValidate(b_dynamic.text.toString())}
        val t = RelativeLayout.LayoutParams(MATCH_PARENT,WRAP_CONTENT)
        t.topMargin = 10
        b_dynamic.layoutParams = t
        layout?.addView(b_dynamic)


        return true
    }

    fun ChooseScenarioValidate(s : String)
    {
        when(s) {
            "Kalte" -> {
                scenario = Scenario1(this,layout)
                user.LoadScenario(1)
            }
            else ->  Toast.makeText(this, "Oups ! Erreur !", Toast.LENGTH_LONG).show()
        }
        scenario.RestoreSave(user.scenarioSave)
        layout.removeAllViews()

        setTitle(s)
        scenario.Beggin()
    }


    fun PrintMap()
    {
        user.scenarioSave = scenario.SendSave()
        user.SaveScenario()
        mappic.setBackgroundResource(scenario.GetImage())
        if(mlayout.visibility == View.VISIBLE)
        {
            SwapLayout(0)
        }
        else{
            SwapLayout(3)
        }
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
                scenario.GetAns(result.contents)
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
            }
        } else { // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data)
        }
        SwapLayout(0)
    }

    fun changename(): Boolean
    {
        user.scenarioSave = scenario.SendSave()
        user.SaveScenario()
        SwapLayout(1)
        return true
    }

    fun ChangenameValidate(s:String): Boolean
    {
        user.teamname = s
        user.SaveName()

        SwapLayout(0)

        maintv.text = s
        return true
    }


    fun EnterCode():Boolean
    {
        user.scenarioSave = scenario.SendSave()
        user.SaveScenario()
        SwapLayout(2)
        return true
    }


    fun EnterCodeValidate(s:String)
    {
        if(!scenario.GetAns(s))
            Toast.makeText(this, "Mauvais code", Toast.LENGTH_LONG).show()
        else
            SwapLayout(0)
    }


    fun MyOptions():Boolean
    {
        return true
    }

    fun SwapLayout(l:Int)
    {
        when(l){
            0->{
                layout.visibility = View.VISIBLE
                nlayout.visibility = View.INVISIBLE
                clayout.visibility = View.INVISIBLE
                mlayout.visibility = View.INVISIBLE
            }
            1->{
                layout.visibility = View.INVISIBLE
                nlayout.visibility = View.VISIBLE
                clayout.visibility = View.INVISIBLE
                mlayout.visibility = View.INVISIBLE
            }
            2->{
                layout.visibility = View.INVISIBLE
                nlayout.visibility = View.INVISIBLE
                clayout.visibility = View.VISIBLE
                mlayout.visibility = View.INVISIBLE
            }
            3->{
                layout.visibility = View.INVISIBLE
                nlayout.visibility = View.INVISIBLE
                clayout.visibility = View.INVISIBLE
                mlayout.visibility = View.VISIBLE
            }

        }

    }

    override fun onBackPressed() {
        SwapLayout(0)
    }

    override fun onPause() {
        user.scenarioSave = scenario.SendSave()
        user.SaveScenario()
        super.onPause()
    }
}
