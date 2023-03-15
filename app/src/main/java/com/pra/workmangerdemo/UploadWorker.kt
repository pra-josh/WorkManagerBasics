package com.pra.workmangerdemo

import android.content.Context
import android.content.ContextParams
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*

class UploadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    companion object {
        const val WORKER_TIME = "worker_time"
    }

    override fun doWork(): Result {

        val data = inputData.getInt(MainActivity.KEY_COUNT_VALUE, 0)
        try {
            for (i in 0 until data) {
                Log.i("WorkManager", "Uploading $i") 
            }
            val time = SimpleDateFormat("dd/M/yyyy hh:mm:SS")
            val currentDate = time.format(Date())

            val outputData = Data.Builder()
                .putString(WORKER_TIME,currentDate).build()

            return Result.success(outputData)
        } catch (e: java.lang.Exception) {
            return Result.failure()
        }
    }
}