package com.trexis.test

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trexis.test.databinding.LayoutRvItemBinding
import com.trexis.test.ui.activities.TransactionsActivity
import com.google.gson.Gson

class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {

    var users = mutableListOf<UserResp>()

    fun setDataList(users: List<UserResp>) {
        this.users = users.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = LayoutRvItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val user = users[position]
        user.name.let {
            holder.binding.userName.text = "Name: $it"
        }
        user.title.let {
            holder.binding.userName.text = "Name: $it"
        }
        holder.binding.userId.text = """ID: ${user.id}"""
        holder.binding.userBalance.text = """Balance: ${user.balance}"""
        holder.binding.root.apply {
            setOnClickListener {
                val intent = Intent(context, TransactionsActivity::class.java)
                intent.putExtra("data", Gson().toJson(user))
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }
}

class MainViewHolder(val binding: LayoutRvItemBinding) : RecyclerView.ViewHolder(binding.root) {

}