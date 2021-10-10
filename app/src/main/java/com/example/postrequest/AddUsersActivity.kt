package com.example.postrequest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class AddUsersActivity : AppCompatActivity() {
    private lateinit var clAdd: ConstraintLayout
    private lateinit var ti_name: TextInputEditText
    private lateinit var ti_location: TextInputEditText
    private lateinit var bt_save: Button
    private lateinit var bt_view: Button

    private fun initVars() {
        clAdd = findViewById(R.id.clAdd)
        ti_name = findViewById(R.id.ti_name)
        ti_location = findViewById(R.id.ti_location)
        bt_save = findViewById(R.id.bt_save)
        bt_view = findViewById(R.id.bt_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_user)
        initVars()
        bt_save.setOnClickListener { saveUser() }
        bt_view.setOnClickListener { viewUsers() }
    }
    private fun viewUsers() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    private fun saveUser() {
        try {
            if (ti_name.text.isNullOrBlank() || ti_location.text.isNullOrBlank()) {
                Log.d("TryCatchError", "Empty text")
                Toast.makeText(clAdd.context, "Empty fields..", Toast.LENGTH_LONG).show()
            } else {
                val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
                if (apiInterface != null) {
                    var newUser = ResultModel.ResultValue(ti_name.text.toString(), ti_location.text.toString())
                    apiInterface.addUser(newUser).enqueue(object : Callback<ResultModel.ResultValue> {
                        override fun onResponse(
                            call: Call<ResultModel.ResultValue>,
                            response: Response<ResultModel.ResultValue>
                        ) {
                            ti_name.setText("")
                            ti_location.setText("")
                            viewUsers()
                        }
                        override fun onFailure(call: Call<ResultModel.ResultValue>, t: Throwable) {
                            Toast.makeText(clAdd.context, "Connection failed..", Toast.LENGTH_SHORT).show()
                            viewUsers()
                        }
                    })
                }
            }
        } catch (e : Exception) {
            Log.d("TryCatchError", "Error: $e")
        }
    }
}