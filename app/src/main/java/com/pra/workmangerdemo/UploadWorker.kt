package com.pra.workmangerdemo

import android.content.Context
import android.content.ContextParams
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class UploadWorker(context: Context, params: WorkerParameters) :
    Worker(context, params) {


    override fun doWork(): Result {
        try {
            for (i in 0..6000) {
                Log.i("WorkManager","Uploading $i")
            }
            return Result.success()
        } catch (e: java.lang.Exception) {
            return Result.failure()
        }
    }
}