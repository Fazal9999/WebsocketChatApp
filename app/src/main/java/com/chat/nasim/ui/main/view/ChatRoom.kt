package com.chat.nasim.ui.main.view

import android.annotation.SuppressLint
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.chat.nasim.R

import com.chat.nasim.databinding.ActivityChatRoomBinding
import com.chat.nasim.ui.main.adapter.ChatListAdapter
import okhttp3.*
import org.json.JSONObject

class ChatRoom : AppCompatActivity() {

    lateinit var binding: ActivityChatRoomBinding
    lateinit var server: String
    lateinit var name: String
    private var msgState: Boolean = false
    private var okHttpClient = OkHttpClient()
    lateinit var chatListAdapter: ChatListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)





        server = intent.getStringExtra("server").toString()
        name = intent.getStringExtra("name").toString()
        chatListAdapter = ChatListAdapter(this, name)

        binding.recyclerView.adapter = chatListAdapter

        val request = Request.Builder().url("ws://$server").build()
        val socket = okHttpClient.newWebSocket(request, SocketListener())

        binding.editChatMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count != 0) {
                    binding.buttonSend.setImageDrawable(getDrawable(R.drawable.ic_send))
                    msgState = true

                } else {
                    binding.buttonSend.setImageDrawable(getDrawable(R.drawable.ic_thumb))
                    msgState = false
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.buttonSend.setOnClickListener {
            var messageBody = JSONObject()
            if (msgState) {
                messageBody.put("message", binding.editChatMessage.text.toString())
                messageBody.put("name", name)
                binding.editChatMessage.text = null
            } else {
                messageBody.put("message", "\uD83D\uDC4D")
                messageBody.put("name", name)
            }
            socket.send(messageBody.toString())
            chatListAdapter.addItem(messageBody)
            binding.recyclerView.smoothScrollToPosition(chatListAdapter.getItemCount() - 1);
        }


    }
//    {"message":"👍","name":"Alamin"}


    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Are you sure you want to exit form chat room?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ -> finish() })
            .setNegativeButton("No", DialogInterface.OnClickListener { _, _ -> })
            .show()
    }

    inner class SocketListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            runOnUiThread {
                Toast.makeText(this@ChatRoom, "connected", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            runOnUiThread {
                val json = JSONObject(text)
                chatListAdapter.addItem(json)
                binding.recyclerView.smoothScrollToPosition(chatListAdapter.getItemCount() - 1);

            }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            runOnUiThread {
                Toast.makeText(this@ChatRoom, "Connection Error", Toast.LENGTH_SHORT).show()

            }
        }

    }

}