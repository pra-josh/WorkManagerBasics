package com.pra.workmangerdemo

import android.content.Context
import android.content.ContextParams
import android.os.Build
import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.contracts.contract

class UploadWorker(val context: Context, params: WorkerParameters) : Worker(context, params) {

    companion object {
        const val WORKER_TIME = "worker_time"
    }

    override fun doWork(): Result {

        try {
            WriteSavedDataIntoFile(createCSVDeviceLog(getCurrentDateTime()), context)
            for (i in 0 until 100) {
                Log.i("WorkManager", "Uploading $i")
            }
            val uploadWorkRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
                .setInitialDelay(30, TimeUnit.SECONDS).build()
            WorkManager.getInstance(context).enqueue(uploadWorkRequest)

            return Result.success()
        } catch (e: java.lang.Exception) {
            return Result.failure()
        }
    }


    private fun createCSVDeviceLog(dateTime: String): String {
        return "$dateTime\n"
    }


    fun getCurrentDateTime(): String {
        return SimpleDateFormat("dd-MM-yy HH:mm:ss").format(Date())
    }

    private fun WriteSavedDataIntoFile(Value: String, context: Context) {
        try {
            var path: String
            path = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                context.externalMediaDirs[0].toString()
            } else {
                context.getExternalFilesDir(null).toString()
            }
            println("Path=====> $path")
            var file = File(path)
            if (!file.exists()) {
                file.mkdirs()
            }
            println("File Path ==> " + file.absolutePath)
            println("File Path ==> " + file.path)

            path = "$path//TransactionLog.csv";

            file = File(path)
            if (!file.exists()) {
                file.createNewFile()
            }
            println("File with Path ==> " + file.absolutePath)
            println("File with Path ==> " + file.path)
            val data: String = readFileAsString(file)

            try {
                val fileOutputStream = FileOutputStream(file, true)
                val writer = OutputStreamWriter(fileOutputStream)
                if (data == null || data == "") {
                    writer.append(Value)
                } else {
                    writer.append(Value)
                }
                writer.close()
                fileOutputStream.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
    }

    fun readFileAsString(file: File?): String {
        val stringBuilder = StringBuilder()
        var line: String?
        var `in`: BufferedReader? = null
        try {
            `in` = BufferedReader(FileReader(file))
            while (`in`.readLine().also { line = it } != null) stringBuilder.append(line)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }
}