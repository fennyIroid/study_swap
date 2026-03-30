package com.studyswap.mobile.app.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink

fun Uri.asMultipartPart(context: Context, formField: String): MultipartBody.Part {
    val cr = context.contentResolver
    val fileName = cr.resolveDisplayName(this) ?: "upload.bin"
    val mime = cr.getType(this) ?: "application/octet-stream"
    val body = object : RequestBody() {
        override fun contentType() = mime.toMediaTypeOrNull()

        override fun writeTo(sink: BufferedSink) {
            cr.openInputStream(this@asMultipartPart)?.use { input ->
                val buffer = ByteArray(8192)
                var n: Int
                while (input.read(buffer).also { n = it } != -1) {
                    sink.write(buffer, 0, n)
                }
            } ?: error("Could not read file")
        }
    }
    return MultipartBody.Part.createFormData(formField, fileName, body)
}

private fun ContentResolver.resolveDisplayName(uri: Uri): String? {
    if (uri.scheme == "content") {
        query(uri, null, null, null, null)?.use { c ->
            if (c.moveToFirst()) {
                val i = c.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (i >= 0) return c.getString(i)
            }
        }
    }
    return uri.lastPathSegment
}
