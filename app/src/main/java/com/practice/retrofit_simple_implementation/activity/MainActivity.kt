package com.practice.retrofit_simple_implementation.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.retrofit_simple_implementation.R
import com.practice.retrofit_simple_implementation.adapter.UserAdapter
import com.practice.retrofit_simple_implementation.api.Api
import com.practice.retrofit_simple_implementation.databinding.ActivityMainBinding
import com.practice.retrofit_simple_implementation.model.UserResponse
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var api: Api
    private lateinit var binding: ActivityMainBinding

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this,
        R.anim.rotate_open_anim
    ) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this,
        R.anim.rotate_close_anim
    ) }
    private val fromBottomAnimation: Animation by lazy { AnimationUtils.loadAnimation(this,
        R.anim.from_bottom_anim
    ) }
    private val toBottomAnimation: Animation by lazy { AnimationUtils.loadAnimation(this,
        R.anim.to_bottom_anim
    ) }

    private var clicked = false
    private val adapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floatingActionButtonMenu.setOnClickListener {
            onAddButtonClicked()
        }
        binding.floatingActionButtonAdd.setOnClickListener {
            Intent(this,  AddDataActivity::class.java).also {
                startActivity(it)
            }
        }

        showUsers()
    }

    private fun onAddButtonClicked(){
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        binding.apply {
            if(!clicked){
                floatingActionButtonAdd.startAnimation(fromBottomAnimation)
                floatingActionButtonMenu.startAnimation(rotateOpen)

            }else{
                floatingActionButtonAdd.startAnimation(toBottomAnimation)
                floatingActionButtonMenu.startAnimation(rotateClose)

            }
        }
    }

    private fun setVisibility(clicked: Boolean) {
        binding.apply {
            if (!clicked){
                floatingActionButtonAdd.visibility = View.VISIBLE

            }else{
                floatingActionButtonAdd.visibility = View.INVISIBLE
            }
        }
    }

    private fun setClickable(clicked: Boolean){
       binding.apply {
           floatingActionButtonAdd.isClickable = !clicked
       }
    }

    private fun showUsers(){
        binding.apply {
            recyclerViewUsers.setHasFixedSize(true)
            recyclerViewUsers.layoutManager = LinearLayoutManager(this@MainActivity)

            api.getUsers().enqueue(object : Callback<ArrayList<UserResponse>> {
                override fun onResponse(
                    call: Call<ArrayList<UserResponse>>,
                    response: Response<ArrayList<UserResponse>>
                ) {
                    recyclerViewUsers.adapter = adapter
                    response.body()?.let {
                        adapter.differ.submitList(it)
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserResponse>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()

                }
            })

        }
    }

}