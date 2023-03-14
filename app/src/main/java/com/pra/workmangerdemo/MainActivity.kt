package com.pra.workmangerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.work.*
import com.pra.workmangerdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    companion object {
        const val KEY_COUNT_VALUE = "key_count_value"
    }

    lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val constraints = Constraints.Builder().setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED).build()


        val data = Data.Builder().putInt(KEY_COUNT_VALUE, 12500).build()

        var workManager: WorkManager = WorkManager.getInstance(applicationContext)

        val uploadWorkRequest =
            OneTimeWorkRequest.Builder(UploadWorker::class.java).setConstraints(constraints)
                .setInputData(data).build()

        val filteringRequest =
            OneTimeWorkRequest.Builder(FilteringWorker::class.java).setConstraints(constraints)
                .setInputData(data).build()

        val compressingRequest =
            OneTimeWorkRequest.Builder(CompressingWorker::class.java).setConstraints(constraints)
                .setInputData(data).build()

        mBinding.btnOneTimeWorkRequest.setOnClickListener {
            workManager.beginWith(filteringRequest).then(compressingRequest).then(uploadWorkRequest)
                .enqueue()
        }

        workManager.getWorkInfoByIdLiveData(uploadWorkRequest.id).observe(this, Observer {
            if (it.state.isFinished) {
                val data = it.outputData
                val message = data.getString(UploadWorker.WORKER_TIME)
                Toast.makeText(
                    applicationContext, "" + message, Toast.LENGTH_SHORT
                ).show()
            }
            mBinding.tvStatus.text = it.state.name
        })

    }


}