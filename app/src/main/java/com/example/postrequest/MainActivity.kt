package com.example.postrequest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var clMain: ConstraintLayout
    private lateinit var rv_viewUsers: RecyclerView
    private lateinit var bt_addUser: Button

    private var listOfUsers = arrayListOf<ArrayList<String>>()

    private fun initVars() {
        clMain = findViewById(R.id.clMain)
        rv_viewUsers = findViewById(R.id.rv_viewUsers)
        bt_addUser = findViewById(R.id.bt_addUser)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initVars()

        getUsers()
        bt_addUser.setOnClickListener { newUser() }
    }

    private fun getUsers() {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<List<ResultModel.ResultValue>> = apiInterface!!.getUser()

        call?.enqueue(object : Callback<List<ResultModel.ResultValue>> {
            override fun onResponse(
                call: Call<List<ResultModel.ResultValue>>,
                response: Response<List<ResultModel.ResultValue>>
            ) {
                for(User in response.body()!!){
                    val name = User.name.toString()
                    val location = User.location.toString()
                    val tempUser = arrayListOf<String>(name, location)
                    listOfUsers.add(tempUser)
                }
                addUserToUI(listOfUsers)
            }
            override fun onFailure(call: Call<List<ResultModel.ResultValue>>, t: Throwable) {
                Log.d("MainActivityAPI", "Connection failed.. $t")
                Toast.makeText(clMain.context, "Connection failed..", Toast.LENGTH_SHORT).show()
                call.cancel()
            }

//            override fun onResponse(call: Call<List<ResultModel?>?>, response: Response<List<ResultModel?>?>) {
//                if (response.code() == 200) {
//                    Log.d("MainActivityAPI", "Fetched Successfully!")
//                    try {
//                        Log.d("MainActivityAPI", "Hi ${response.body()}")
//                        Log.d("MainActivityAPI", "Hi ${response.body()!![0]}")
//                        var stringToBePritined:String? = "";
//                        for(User in response.body()!!){
//                            stringToBePritined = stringToBePritined + User.name+ "\n"+User.location + "\n"+"\n"
//                        }
//
//                    } catch (e: Exception) {
//                        Log.d("MainActivityAPI", "Try error: $e")
//                    }
//                } else {
//                    Toast.makeText(clMain.context, "Empty API..", Toast.LENGTH_SHORT).show()
//                }
//            }
//            override fun onFailure(call: Call<List<ResultModel?>?>, t: Throwable) {
//                Log.d("MainActivityAPI", "Connection failed.. $t")
//                Toast.makeText(clMain.context, "Connection failed..", Toast.LENGTH_SHORT).show()
//                call.cancel()
//            }
        })
    }

    private fun addUserToUI(user: ArrayList<ArrayList<String>>) {
        rv_viewUsers.adapter = RecyclerAdapter(user)
        rv_viewUsers.layoutManager = LinearLayoutManager(this)
    }

    private fun newUser() {
        val intent = Intent(this, AddUsersActivity::class.java)
        startActivity(intent)
    }
}