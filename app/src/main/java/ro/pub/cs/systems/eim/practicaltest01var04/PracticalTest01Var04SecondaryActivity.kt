package ro.pub.cs.systems.eim.practicaltest01var04

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PracticalTest01Var04SecondaryActivity : AppCompatActivity() {
    private lateinit var firstEditText : EditText
    private lateinit var secondEditText : EditText
    private lateinit var okButton : Button
    private lateinit var cancelButton : Button

    inner class ButtonListener : View.OnClickListener {
        override fun onClick(v: View?) {
            if (v?.id == R.id.okButton) {
                setResult(RESULT_OK)
            }
            else {
                setResult(RESULT_CANCELED)
            }

            this@PracticalTest01Var04SecondaryActivity.finish()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_var04_secondary)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        firstEditText = findViewById(R.id.firstEditText)
        secondEditText = findViewById(R.id.secondEditText)

        val intentFromParent = intent
        val data = intent.getExtras()
        if (data != null) {
            firstEditText.setText(data.getString("EDIT_TEXT_1"))
            secondEditText.setText(data.getString("EDIT_TEXT_2"))
        }

        okButton = findViewById(R.id.okButton)
        cancelButton = findViewById(R.id.cancelButton)
        okButton.setOnClickListener(ButtonListener())
        cancelButton.setOnClickListener(ButtonListener())
    }
}