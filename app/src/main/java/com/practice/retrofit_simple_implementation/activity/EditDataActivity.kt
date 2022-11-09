package com.practice.retrofit_simple_implementation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.practice.retrofit_simple_implementation.api.Api
import com.practice.retrofit_simple_implementation.databinding.ActivityEditDataBinding
import com.practice.retrofit_simple_implementation.model.UserResponse
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class EditDataActivity : AppCompatActivity() {
    companion object{
        const val KEY_POSITION = "KEY_TO_EDIT"
    }

    @Inject
    lateinit var api: Api
    private lateinit var binding: ActivityEditDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityEditDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userResponse = intent.getParcelableExtra<UserResponse>(KEY_POSITION) as UserResponse
        Log.v("Edit", userResponse.toString())

        binding.apply {
            textInputEditTextId.setText(userResponse.id.toString())
            textInputEditTextUserId.setText(userResponse.userId.toString())
            textInputEditTextTitle.setText(userResponse.title.toString())
            textInputEditTextBody.setText(userResponse.text.toString())

            buttonEditData.setOnClickListener {
                val success = 200
                var messageSuccess = ""
                api.putUser(
                    textInputEditTextId.text.toString().toInt(),
                    textInputEditTextUserId.text.toString().toInt(),
                    textInputEditTextTitle.text.toString(),
                    textInputEditTextBody.text.toString()

                )
                    .enqueue(object: Callback<UserResponse>{
                        override fun onResponse(
                            call: Call<UserResponse>,
                            response: Response<UserResponse>
                        ) {
                            if (response.code() == success){
                                messageSuccess ="""
                                    Response Code : $success
                                    Id            : ${response.body()?.id} 
                                    UserId        : ${response.body()?.userId}  
                                    Title         : ${response.body()?.title}
                                    Body          : ${response.body()?.text}
                                """.trimIndent()
                            }
                            Toast.makeText(this@EditDataActivity, messageSuccess, Toast.LENGTH_LONG).show()
                        }

                        override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                            Toast.makeText(this@EditDataActivity, t.message, Toast.LENGTH_LONG).show()
                        }

                    })
                Intent(this@EditDataActivity, MainActivity::class.java).also {
                   startActivity(it)
                   finish()
                }
            }
        }


    }
}