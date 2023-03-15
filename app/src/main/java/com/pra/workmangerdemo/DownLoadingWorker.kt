package com.pra.workmangerdemo

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*

class DownLoadingWorker(val context: Context, params: WorkerParameters) : Worker(context, params) {


    override fun doWork(): Result {
        try {
            for (i in 0 .. 3000) {
                Log.i("MYTAG", "Downloading $i")
            }
            Toast.makeText(context,"Work Manager",Toast.LENGTH_SHORT).show()
            return Result.success()
        } catch (e: java.lang.Exception) {
            return Result.failure()
        }
    }
}