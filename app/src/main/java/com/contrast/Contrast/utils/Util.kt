package com.contrast.Contrast.utils




import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.widget.TextView
import com.contrast.Contrast.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object Util {


    // Khởi tạo Gson chỉ serialize các field có @Expose
    val gson: Gson = GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    fun objectToJson(obj: Any) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val filteredObj = obj // Chỉ serialize các trường cần thiết (tạo DTO nếu cần)
                val json = gson.toJson(filteredObj)
                withContext(Dispatchers.Main) {
                    Log.d("Account_JSON", json)
                }
            } catch (e: Exception) {
                Log.e("Account_JSON", "Serialization error: ${e.message}")
            }
        }
    }

    fun showDialog(infomation: String?, context: Context) {
        if (context != null) {
            val builder = Dialog(context)
            builder.setCancelable(false)
            builder.setContentView(R.layout.item_alert_dialog)
            val tvInformation = builder.findViewById<TextView>(R.id.tvInformation)
            val tvOk = builder.findViewById<TextView>(R.id.tvOk)
            tvInformation.text = infomation
            builder.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            tvOk.setOnClickListener {
                builder.dismiss() }
            builder.show()
        }
    }

}
