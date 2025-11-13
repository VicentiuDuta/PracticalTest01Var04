package ro.pub.cs.systems.eim.practicaltest01var04

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log

class ProcessingThread(val context: Context, val nume: String?, val grupa: String?) : Thread() {
    companion object {
        var isRunning = false
    }

    override fun run() {
        while (isRunning) {
            val intent1 = Intent(Constants.ACTION1).apply {
                setPackage(context.packageName)
                putExtra("NUME", nume)
            }
            Log.d("SERVICE:", "MESSAGE SENT $nume")
            context.sendBroadcast(intent1)
            sleepThread()

            val intent = Intent(Constants.ACTION2).apply {
                setPackage(context.packageName)
                putExtra("GRUPA", grupa)
            }
            Log.d("SERVICE:", "MESSAGE SENT $grupa")
            context.sendBroadcast(intent)
            sleepThread()
        }
    }

    fun sleepThread() {
        sleep(5000)
    }

    fun stopThread() {
        isRunning = false
    }
}
class PracticalTest01Var04Service : Service() {

    private lateinit var processingThread : ProcessingThread
    override fun onBind(intent: Intent): IBinder? {
        return null
    }
    override fun onCreate() {
    }
    override fun onStartCommand(intent: Intent?, flags: Int,
                                startId: Int): Int {
        Log.d("SERVICE:", "STARTING SERVICE")
        val data = intent?.getExtras()
        var nume: String? = ""
        var grupa: String? = ""
        if (data != null) {
            nume = data.getString("EDIT_TEXT_1")
            grupa = data.getString("EDIT_TEXT_2")
        }
        processingThread =
            ProcessingThread(this@PracticalTest01Var04Service, nume, grupa)
        if(ProcessingThread.isRunning == false){
            ProcessingThread.isRunning = true
            processingThread.start()
        }
        return START_NOT_STICKY
    }
    override fun onDestroy() {
        Log.d("SERVICE:", "STOPPING THREAD")
        processingThread.stopThread()
    }
}