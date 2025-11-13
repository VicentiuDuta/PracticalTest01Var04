package ro.pub.cs.systems.eim.practicaltest01var04

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
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

    inner class ButtonListener : View.OnClickListener {
        override fun onClick(v: View?) {
            if (v?.id == R.id.displayButton) {
                val bool1 = checkbox1.isChecked
                val bool2 = checkbox2.isChecked
                var text = ""
                if (checkbox1.isChecked) {
                    if (editText1.getText().toString() == "")
                        Toast.makeText(this@PracticalTest01Var04MainActivity, "First editText empty", Toast.LENGTH_SHORT).show()
                    else
                        displayTextView.append(editText1.getText().toString())
                }
                if (checkbox2.isChecked) {
                    if (editText2.getText().toString() == "")
                        Toast.makeText(this@PracticalTest01Var04MainActivity, "Second editText empty", Toast.LENGTH_SHORT).show()
                    else
                        displayTextView.append(editText2.getText().toString())
                }


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

    }
}