package com.contrast.Contrast.utils




import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object util {


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

}
