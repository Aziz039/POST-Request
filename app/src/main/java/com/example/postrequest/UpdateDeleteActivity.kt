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

class UpdateDeleteActivity : AppCompatActivity() {
    private lateinit var clUpdateDelete: ConstraintLayout
    private lateinit var ti_pk: TextInputEditText
    private lateinit var ti_name: TextInputEditText
    private lateinit var ti_location: TextInputEditText
    private lateinit var bt_update: Button
    private lateinit var bt_delete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_delete_activity)
        initVars()
    }

    private fun initVars() {
        clUpdateDelete = findViewById(R.id.clUpdateDelete)
        ti_pk = findViewById(R.id.ti_pk)
        ti_name = findViewById(R.id.ti_name)
        ti_location = findViewById(R.id.ti_location)
        bt_update = findViewById(R.id.bt_update)
        bt_delete = findViewById(R.id.bt_delete)

        bt_update.setOnClickListener { handleUpdate() }
        bt_delete.setOnClickListener { handleDelete() }
    }

    private fun handleUpdate() {
        if (ti_pk.text.isNullOrBlank() || ti_name.text.isNullOrBlank() || ti_location.text.isNullOrBlank()) {
            Toast.makeText(clUpdateDelete.context, "Empty field", Toast.LENGTH_SHORT).show()
        } else {
            val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
            apiInterface?.updateUser(
                ti_pk.text.toString().toInt(),
                ResultModel.ResultValue(
                    ti_pk.text.toString().toInt(),
                    ti_name.text.toString(),
                    ti_location.text.toString()
                )
            )?.enqueue(object : Callback<ResultModel.ResultValue> {
                override fun onResponse(
                    call: Call<ResultModel.ResultValue>,
                    response: Response<ResultModel.ResultValue>
                ) {
                    Log.d("LogInUpdate", "User Updated")
                    Toast.makeText(
                        clUpdateDelete.context,
                        "User has been updated",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(clUpdateDelete.context, MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onFailure(call: Call<ResultModel.ResultValue>, t: Throwable) {
                    Log.d("LogInUpdate", "Connection failed: $t")
                }
            })
        }
    }

    private fun handleDelete() {
        if (ti_pk.text.isNullOrBlank()) {
            Toast.makeText(clUpdateDelete.context, "Empty ID", Toast.LENGTH_SHORT).show()
        } else {
            val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
            apiInterface?.deleteUser(ti_pk.text.toString().toInt())?.enqueue(object: Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Toast.makeText(
                        clUpdateDelete.context,
                        "User has been deleted",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(clUpdateDelete.context, MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("LogInUpdate", "Connection failed: $t")
                }

            })
        }
    }
}