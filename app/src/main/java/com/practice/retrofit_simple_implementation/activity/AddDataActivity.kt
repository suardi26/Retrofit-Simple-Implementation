package com.practice.retrofit_simple_implementation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.practice.retrofit_simple_implementation.api.RetrofitClient
import com.practice.retrofit_simple_implementation.databinding.ActivityAddDataBinding
import com.practice.retrofit_simple_implementation.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddDataActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            buttonAddData.setOnClickListener {

                val userId = textInputEditTextUserId.text.toString()
                val title: String = textInputEditTextTitle.text.toString()
                val text: String = textInputEditTextBody.text.toString()

                RetrofitClient.instance.createUser(
                    userId = userId.toInt(),
                    title = title,
                    text = text
                ).enqueue(object : Callback<UserResponse> {
                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        val message = """
                            response code = ${response.code()}
                            id            = ${response.body()?.id}
                            userId        = ${response.body()?.userId}  
                            title         = ${response.body()?.title}  
                            text          = ${response.body()?.text}  
                        """.trimIndent()
                        Toast.makeText(this@AddDataActivity, message, Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Toast.makeText(this@AddDataActivity, t.message, Toast.LENGTH_LONG).show()
                    }

                })
            }
        }
    }
}