package com.chat.nasim.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chat.nasim.databinding.ItemMessageReceivedBinding
import com.chat.nasim.databinding.ItemMessageSentBinding
import com.google.gson.JsonObject
import org.json.JSONObject


/* 
 * Created by nasim on 1/20/22  
 */
class ChatListAdapter(val context: Context, val name: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val data = ArrayList<JSONObject>()

    private val TYPE_MESSAGE_SENT = 0
    private val TYPE_MESSAGE_RECEIVED = 1

    override fun getItemViewType(position: Int): Int {

        val message: JSONObject = data.get(position)
        return if (message["name"].equals(name)) {
            TYPE_MESSAGE_SENT
        } else {
            TYPE_MESSAGE_RECEIVED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_MESSAGE_SENT -> {
                return ViewHolderSender(
                    ItemMessageSentBinding.inflate(
                        LayoutInflater.from(context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                return ViewHolderReceiver(
                    ItemMessageReceivedBinding.inflate(
                        LayoutInflater.from(context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = data.get(position)

        if (currentMessage.get("name").toString() == name) {
            val h = holder as ViewHolderSender
            h.binding.sentTxt.text = currentMessage["message"].toString()
        }else{
            val h = holder as ViewHolderReceiver
            h.binding.receivedTxt.text = currentMessage["message"].toString()
            h.binding.nameTxt.text = currentMessage["name"].toString()
        }

    }


    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    public fun addItem(jsonObject: JSONObject) {
        data.add(jsonObject)
        notifyDataSetChanged()
    }

    class ViewHolderSender(val binding: ItemMessageSentBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    class ViewHolderReceiver(val binding: ItemMessageReceivedBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }


}