package com.example.currency_converter2

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    var baseCurrency = "EUR"
    var convertedToCurrency = "USD"
    var conversionRate = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

        spinnerSetup()
        textChanged()
    }
    private fun getApiResult(){

        if(et_firstConversion !=null && et_firstConversion.text.isNotEmpty() && et_firstConversion.text.isNotBlank()) {

            var API = "https://api.getgeoapi.com/v2/currency/list?api_key=ef0923d124d5f1933e9f41ca4336f2b4ef32f887"

            if(baseCurrency == convertedToCurrency){

                Toast.makeText(applicationContext, "Cannot convert the same currency", Toast.LENGTH_SHORT).show()
            }else {
                GlobalScope.Launch(Dispatchers.IO) {
                    try{
                        val apiResult = URL(API).readText()
                        val jsonObject = JSONObject(apiResult)

                        conversionRate = jsonObject.getJSONObject( "rates").getString(convertedToCurrency).toFloat()

                        Log.d("Main", "$conversionRate")
                        Log.d("Main", apiResult)
                        withContext(Dispatchers.Main){
                            val text: String = ((et_firstConversion.text.toString().toFloat()) * conversionRate).toString()
                            et_secondConversion?.setText(text)
                        }

                    }catch (e: Exception) {

                        log.e("Main", "$e")
                    }

                }

            }
        }

    }

    private fun spinnerSetup() {
        val spinner: Spinner = findViewById(R.id.spinner_firstConversion)
        val spinner2: Spinner = findViewById(R.id.spinner_firstConversion)




    }

}