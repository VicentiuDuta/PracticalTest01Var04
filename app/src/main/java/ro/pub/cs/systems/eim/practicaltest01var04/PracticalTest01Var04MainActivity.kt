package ro.pub.cs.systems.eim.practicaltest01var04

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PracticalTest01Var04MainActivity : AppCompatActivity() {
    private lateinit var secondaryActivityButton : Button
    private lateinit var checkbox1 : CheckBox
    private lateinit var editText1 : EditText
    private lateinit var checkbox2 : CheckBox
    private lateinit var editText2 : EditText
    private lateinit var displayButton : Button
    private lateinit var displayTextView : TextView
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private var edit1completed = false
    private var edit2completed = false

    private lateinit var broadcastReceiver : PracticalTest01Var04BroadcastReceiver

    inner class ButtonListener : View.OnClickListener {
        override fun onClick(v: View?) {
            if (v?.id == R.id.displayButton) {
                val bool1 = checkbox1.isChecked
                val bool2 = checkbox2.isChecked
                var text = ""
                if (checkbox1.isChecked) {
                    if (editText1.getText().toString() == "")
                        Toast.makeText(this@PracticalTest01Var04MainActivity, "First editText empty", Toast.LENGTH_SHORT).show()
                    else {
                        displayTextView.append(editText1.getText().toString())
                        edit1completed = true
                    }
                }
                if (checkbox2.isChecked) {
                    if (editText2.getText().toString() == "")
                        Toast.makeText(this@PracticalTest01Var04MainActivity, "Second editText empty", Toast.LENGTH_SHORT).show()
                    else {
                        displayTextView.append(editText2.getText().toString())
                        edit2completed = true
                    }
                }

            }

            if (edit1completed == true && edit2completed == true) {
                if(displayTextView.getText().toString() != "") {
                    val intent = Intent(this@PracticalTest01Var04MainActivity,
                        PracticalTest01Var04Service::class.java).apply {
                        putExtra("EDIT_TEXT_1", editText1.getText().toString())
                        putExtra("EDIT_TEXT_2", editText2.getText().toString())
                    }

                    startService(intent)
                }
            }

            if (v?.id == R.id.navigateToSecondaryButton) {
                val intent = Intent(this@PracticalTest01Var04MainActivity,
                    PracticalTest01Var04SecondaryActivity::class.java).apply {
                        putExtra("EDIT_TEXT_1", editText1.getText().toString())
                        putExtra("EDIT_TEXT_2", editText2.getText().toString())

                }
                startForResult.launch(intent)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_var04_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        secondaryActivityButton = findViewById(R.id.navigateToSecondaryButton)
        checkbox1 = findViewById(R.id.checkBox1)
        editText1 = findViewById(R.id.editText1)
        checkbox2 = findViewById(R.id.checkBox2)
        editText2 = findViewById(R.id.editText2)
        displayButton = findViewById(R.id.displayButton)
        displayTextView = findViewById(R.id.displayTextView)

        secondaryActivityButton.setOnClickListener(ButtonListener())
        displayButton.setOnClickListener(ButtonListener())

        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
                if (result.resultCode == Activity.RESULT_OK) {
//                    Log.d("RETURN FROM SEC", "RESULT OK")
                    Toast.makeText(this@PracticalTest01Var04MainActivity, "RETURN OK", Toast.LENGTH_LONG).show()
                }

            else {
//                    Log.d("RETURN FROM SEC", "RESULT CANCELLED")
                    Toast.makeText(this@PracticalTest01Var04MainActivity, "RETURN CANCELLED", Toast.LENGTH_LONG).show()

                }
        }

        broadcastReceiver = PracticalTest01Var04BroadcastReceiver()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("FIRST_EDIT_TEXT", editText1.getText().toString())
        outState.putString("SECOND_EDIT_TEXT", editText2.getText().toString())
        outState.putString("TEXT_VIEW_TEXT", displayTextView.getText().toString())

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        editText1.setText(savedInstanceState.getString("FIRST_EDIT_TEXT"))
        editText2.setText(savedInstanceState.getString("SECOND_EDIT_TEXT"))
        displayTextView.setText(savedInstanceState.getString("TEXT_VIEW_TEXT"))
    }

    override fun onDestroy() {
        super.onDestroy()
        val intent = Intent(this@PracticalTest01Var04MainActivity, PracticalTest01Var04Service::class.java)
        stopService(intent)
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter().apply {
            addAction(Constants.ACTION1)
            addAction(Constants.ACTION2)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(broadcastReceiver, intentFilter,
                Context.RECEIVER_NOT_EXPORTED)
        } else {
            registerReceiver(broadcastReceiver, intentFilter)
        }
        Log.d("[MAIN]", "BroadcastReceiver registered!")
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(broadcastReceiver)
    }
}