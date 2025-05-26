package com.example.healplus.managers

import android.content.Context
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File

suspend fun uploadImageToServer(imageUri: Uri, context: Context): String? {
    return withContext(Dispatchers.IO) {
        try {
            val filePath = getPathFromUri(context, imageUri)
            Log.d("UploadImage", "File path: $filePath")
            if (filePath == null) {
                Log.e("UploadImage", "Failed to get file path from URI")
                return@withContext null
            }
            val file = File(filePath)
            Log.d("UploadImage", "File: ${file.name}, Size: ${file.length()} bytes")

            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                    "image",
                    file.name,
                    RequestBody.create("image/*".toMediaTypeOrNull(), file)
                )
                .build()

            val request = Request.Builder()
                .url("https://laulu.io.vn/healplus/php/upload_images.php")
                .post(requestBody)
                .build()

            val client = OkHttpClient()
            Log.d("UploadImage", "Sending upload request to server...")
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            Log.d("UploadImage", "Response body: $responseBody")

            return@withContext responseBody?.let {
                try {
                    val json = JSONObject(it)
                    if (json.getString("status") == "success") {
                        val imageUrl = json.getString("image_url")
                        Log.d("UploadImage", "Image uploaded successfully: $imageUrl")
                        return@withContext imageUrl
                    } else {
                        Log.e("UploadImage", "Image upload failed: ${json.getString("message")}")
                        return@withContext null
                    }
                } catch (e: Exception) {
                    Log.e("UploadImage", "Error parsing JSON response: ${e.message}")
                    return@withContext null
                }
            }
        } catch (e: Exception) {
            Log.e("UploadImage", "Error uploading image: ${e.message}")
            e.printStackTrace()
            null
        }
    }
}
fun getPathFromUri(context: Context, uri: Uri): String? {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        it.moveToFirst()
        val index = it.getColumnIndex("_data")
        return it.getString(index)
    }
    return null
}