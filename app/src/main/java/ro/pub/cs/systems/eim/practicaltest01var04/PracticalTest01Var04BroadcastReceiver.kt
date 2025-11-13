package ro.pub.cs.systems.eim.practicaltest01var04

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class PracticalTest01Var04BroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val action = intent.action

        if (action == Constants.ACTION1) {
            val nume = intent.getExtras()?.getString("NUME")
            Log.d("BROADCAST RECEIVER", "RECEIVED $nume")
        }

        else if (action == Constants.ACTION2){
            val grupa = intent.getExtras()?.getString("GRUPA")
            Log.d("BROADCAST RECEIVER", "RECEIVED $grupa")
        }
    }
}