package com.pra.workmangerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.pra.workmangerdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        var workManager: WorkManager = WorkManager.getInstance(applicationContext)

        val uploadWorkRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .build()

        mBinding.btnOneTimeWorkRequest.setOnClickListener {
            workManager.enqueue(uploadWorkRequest)
        }



    }


}