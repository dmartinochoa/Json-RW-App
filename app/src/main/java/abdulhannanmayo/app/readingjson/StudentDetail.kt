package abdulhannanmayo.app.readingjson

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StudentDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)

        val userId = findViewById<TextView>(R.id.textViewUserid)
        val userName = findViewById<TextView>(R.id.textViewUsername)
        val userSurName = findViewById<TextView>(R.id.textViewAddress)
        val userSubject = findViewById<TextView>(R.id.textViewUserSubject)

        val intent = intent
        val id = intent.getStringExtra("Id")
        userId.setText(id)

        val name = intent.getStringExtra("Name")
        userName.setText(name)

        val surName = intent.getStringExtra("SurName")
        userSurName.setText(surName)

        val subject = intent.getStringExtra("Subject")
        userSubject.setText(subject)

    }
}