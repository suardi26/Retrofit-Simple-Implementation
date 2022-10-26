package com.practice.retrofit_simple_implementation.adapter

import android.content.Intent
import android.view.*
import android.widget.PopupMenu
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.practice.retrofit_simple_implementation.R
import com.practice.retrofit_simple_implementation.activity.DeleteDataActivity
import com.practice.retrofit_simple_implementation.activity.EditDataActivity
import com.practice.retrofit_simple_implementation.databinding.ItemListBinding
import com.practice.retrofit_simple_implementation.model.UserResponse

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<UserResponse>() {
        override fun areItemsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
            return oldItem == newItem
        }

    }

    val differ: AsyncListDiffer<UserResponse> = AsyncListDiffer(this, differCallback)

    inner class UserViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userResponse: UserResponse) {
            with(binding) {

                val resources = this.root.resources
                val defaultValue = "Empty"

                val userId = userResponse.userId.toString()
                val id = userResponse.id.toString()
                val title = userResponse.title ?: defaultValue
                val body = userResponse.text ?: defaultValue

                textViewUserId.text = resources.getString(R.string.value_user_id, userId)
                textViewId.text = resources.getString(R.string.value_id, id)
                textViewTitle.text = resources.getString(R.string.value_title, title)
                textViewBody.text = resources.getString(R.string.value_body, body)

                binding.root.setOnClickListener {
                    val popUpMenu = PopupMenu(binding.root.context, binding.root)
                    popUpMenu.inflate(R.menu.option)
                    popUpMenu.show()

                    popUpMenu.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.optionEdit -> {
                                Intent(
                                    binding.root.context,
                                    EditDataActivity::class.java
                                ).also { intent ->
                                    intent.putExtra(EditDataActivity.KEY_POSITION, userResponse)
                                    binding.root.context.startActivity(intent)
                                }
                                true
                            }
                            R.id.optionDelete -> {
                                Intent(
                                    binding.root.context,
                                    DeleteDataActivity::class.java
                                ).also { intent ->
                                    intent.putExtra(DeleteDataActivity.KEY_POSITION, userResponse)
                                    binding.root.context.startActivity(intent)
                                }
                                true
                            }
                            else -> false
                        }
                    }
                }

            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = differ.currentList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = differ.currentList.size

}