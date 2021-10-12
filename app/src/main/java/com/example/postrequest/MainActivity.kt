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
    private lateinit var bt_updDel: Button

    private var listOfUsers = arrayListOf<ArrayList<String>>()

    private fun initVars() {
        clMain = findViewById(R.id.clMain)
        rv_viewUsers = findViewById(R.id.rv_viewUsers)
        bt_addUser = findViewById(R.id.bt_addUser)
        bt_updDel = findViewById(R.id.bt_updDel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initVars()

        getUsers()
        bt_addUser.setOnClickListener { newUser() }
        bt_updDel.setOnClickListener {
            val intent = Intent(this, UpdateDeleteActivity::class.java)
            startActivity(intent)
        }
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
                    val pk = User.pk.toString()
                    val name = User.name.toString()
                    val location = User.location.toString()
                    val tempUser = arrayListOf<String>(pk, name, location)
                    listOfUsers.add(tempUser)
                }
                addUserToUI(listOfUsers)
            }
            override fun onFailure(call: Call<List<ResultModel.ResultValue>>, t: Throwable) {
                Log.d("MainActivityAPI", "Connection failed.. $t")
                Toast.makeText(clMain.context, "Connection failed..", Toast.LENGTH_SHORT).show()
                call.cancel()
            }
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