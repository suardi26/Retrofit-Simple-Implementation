package com.practice.retrofit_simple_implementation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.practice.retrofit_simple_implementation.api.Api
import com.practice.retrofit_simple_implementation.databinding.ActivityDeleteDataBinding
import com.practice.retrofit_simple_implementation.model.UserResponse
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class DeleteDataActivity : AppCompatActivity() {
    companion object {
        const val KEY_POSITION = "KEY_TO_DELETE"
    }

    @Inject
    lateinit var api: Api
    private lateinit var binding: ActivityDeleteDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userResponse: UserResponse =
            intent.getParcelableExtra<UserResponse>(KEY_POSITION) as UserResponse
        Log.v("Delete", userResponse.toString())

        binding.apply {
            textInputEditTextId.setText(userResponse.id.toString())
            textInputEditTextUserId.setText(userResponse.userId.toString())
            textInputEditTextTitle.setText(userResponse.title.toString())
            textInputEditTextBody.setText(userResponse.text.toString())

            buttonEditData.setOnClickListener {
                val success = 200
                var messageSuccess = ""
                api.deletePost(textInputEditTextId.text.toString().toInt())
                    .enqueue(object : retrofit2.Callback<Unit> {
                        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                            if (success == response.code()) {
                                messageSuccess = """
                                    resultCode = $success,
                                    Data Berhasil di Hapus !!!
                                """.trimIndent()
                            }
                            Toast.makeText(
                                this@DeleteDataActivity,
                                messageSuccess,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        override fun onFailure(call: Call<Unit>, t: Throwable) {
                            Toast.makeText(
                                this@DeleteDataActivity,
                                t.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })

                Intent(this@DeleteDataActivity, MainActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }
    }
}