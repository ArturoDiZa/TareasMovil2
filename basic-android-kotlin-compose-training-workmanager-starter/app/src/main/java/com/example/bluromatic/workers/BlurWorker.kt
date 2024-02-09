package com.example.bluromatic.workers

import androidx.work.WorkerParameters
import androidx.work.CoroutineWorker
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import com.example.bluromatic.DELAY_TIME_MILLIS
import com.example.bluromatic.KEY_BLUR_LEVEL
import com.example.bluromatic.KEY_IMAGE_URI
import com.example.bluromatic.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private const val TAG = "BlurWorker"
class BlurWorker (ctx: Context, params : WorkerParameters) : CoroutineWorker(ctx, params){
    override suspend fun doWork(): Result {
        val resourceUri = inputData.getString(KEY_IMAGE_URI)
        val blurLevel = inputData.getInt(KEY_BLUR_LEVEL, 1)
        makeStatusNotification(applicationContext.resources.getString(R.string.blurring_image),applicationContext)
        return withContext(Dispatchers.IO){
            delay(DELAY_TIME_MILLIS)
            return@withContext try {
                val picture = BitmapFactory.decodeResource(applicationContext.resources,R.drawable.android_cupcake)
                val output = blurBitmap(picture,1)
                val outputUri = writeBitmapToFile(applicationContext, output)

                makeStatusNotification("Output is $outputUri", applicationContext)
                Result.success()
            }catch (throwable: Throwable){
                Log.e(
                    TAG,applicationContext.resources.getString(R.string.error_applying_blur),throwable
                )
                Result.failure()
            }
        }

    }
}
