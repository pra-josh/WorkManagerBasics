package com.pra.workmangerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.work.*
import com.pra.workmangerdemo.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {


    companion object {
        const val KEY_COUNT_VALUE = "key_count_value"
    }

    lateinit var mBinding: ActivityMainBinding

    lateinit var workManager: WorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val constraints = Constraints.Builder().setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED).build()


        val data = Data.Builder().putInt(KEY_COUNT_VALUE, 12500).build()

        workManager = WorkManager.getInstance(applicationContext)

        val uploadWorkRequest = PeriodicWorkRequest.Builder(
            UploadWorker::class.java, 20, TimeUnit.MINUTES
        ).build()

        var id = uploadWorkRequest.id;
        mBinding.btnOneTimeWorkRequest.setOnClickListener {
            workManager.enqueueUniquePeriodicWork(
                "Unique", ExistingPeriodicWorkPolicy.KEEP, uploadWorkRequest
            )
        }

        /* workManager.getWorkInfoByIdLiveData(id).observe(this, Observer {
             if (it.state.isFinished) {

             }
             mBinding.tvStatus.text = it.state.name
         })*/

    }


}