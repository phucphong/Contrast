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
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern


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

    fun initRetrofit(url: String,context: Context?): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .connectTimeout(100, TimeUnit.SECONDS)
            .build()
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }
    fun updateImageSrc(content: String, domain: String): String {
        // Biểu thức chính quy tìm các thẻ <img> với src không chứa 'http'
        val regex = "<img [^>]*src=['\"](?!http)([^'\"]+)"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(content)

        // Sử dụng StringBuffer để lưu trữ kết quả thay thế
        val updatedContent = StringBuffer()
        while (matcher.find()) {
            // Thay thế giá trị src bằng cách thêm baseURL vào đầu
            matcher.appendReplacement(updatedContent, "<img src='$domain${matcher.group(1)}'")
        }
        matcher.appendTail(updatedContent)
        return updatedContent.toString()
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
