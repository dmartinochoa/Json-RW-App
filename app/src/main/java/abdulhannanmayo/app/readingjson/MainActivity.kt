package abdulhannanmayo.app.readingjson

import abdulhannanmayo.app.readingjson.Adapter.CustomAdapter
import abdulhannanmayo.app.readingjson.Adapter.CustomAdapterStudent
import abdulhannanmayo.app.readingjson.Handler.DatabaseHandler
import abdulhannanmayo.app.readingjson.Handler.DatabaseHandlerStudent
import abdulhannanmayo.app.readingjson.Model.PersonModelClass
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity(), CustomAdapterStudent.OnItemClickListener {

    var subjects = "all"
    var IdUser = "4"
    var NameUser = "4"
    var SurNameUser = "sss"
    var SubjectUser = "BBDD"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerr()

    }

    fun spinnerr(){
        var spinner: Spinner
        val paths = arrayOf("Subject", "BBDD", "programacion")

        spinner = findViewById(R.id.spinner) as Spinner
        val adapter = ArrayAdapter(this@MainActivity,
            android.R.layout.simple_spinner_item, paths)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(adapter)

        spinner.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?,
                                        position: Int, id: Long) {
                Log.v("item", (parent.getItemAtPosition(position) as String)!!)
                when (position) {
                    0 -> {
//                        Toast.makeText(applicationContext, "item1", Toast.LENGTH_LONG).show()

                        val databaseHandlerStudent: DatabaseHandlerStudent = DatabaseHandlerStudent(applicationContext)
                        val emp: List<PersonModelClass> = databaseHandlerStudent.viewEmployee()
                        subjects = "all"
                        if (emp.size > 1){
                            viewRecord("gg")
                            viewStudentRecord("gg")

                        }else{
                            professor()
                            student()
                            viewRecord("gg")
                            viewStudentRecord("gg")
                        }

                    }
                    1 -> {
//                        Toast.makeText(applicationContext, "BBDD", Toast.LENGTH_LONG).show()
                        viewRecord("BBDD")
                        viewStudentRecord("BBDD")
                        subjects = "BBDD"
                    }
                    2 -> {
//                        Toast.makeText(applicationContext, "programacion", Toast.LENGTH_LONG).show()
                        viewRecord("programación")
                        viewStudentRecord("programacion")
                        subjects = "programacion"
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })
    }

    fun getJsonDataFromAsset(context: Context, fileName: String): String? {

        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }

        return jsonString
    }

    fun student() {

        val obj = JSONObject(getJsonDataFromAsset(applicationContext, "datos.json"))
        val m_jArry: JSONArray = obj.getJSONArray("alumnos")

        for (i in 0 until m_jArry.length()) {

            val jo_inside = m_jArry.getJSONObject(i)

            val program = jo_inside.getString("Asignaturas")
            val program1 = program.replace("[", "")
            val program2 = program1.replace("]", "")
            val program3 = program2.replace("\"", "")
            Log.d("Details-->2", program3)
            saveStudentsRecord("" + jo_inside.getString("codigo"), "" + jo_inside.getString("nombre"), "" + jo_inside.getString("apellido"), "" + program3)

        }

    }

    fun professor() {

        val obj = JSONObject(getJsonDataFromAsset(applicationContext, "datos.json"))
        val m_jArry: JSONArray = obj.getJSONArray("profesores")

        for (i in 0 until m_jArry.length()) {
            val jo_inside = m_jArry.getJSONObject(i)

            saveRecord("" + jo_inside.getString("codigo"), "" + jo_inside.getString("nombre"), "" + jo_inside.getString("apellido"), "" + jo_inside.getString("asignatura"))
        }

    }

    //method for saving records in database
    fun saveRecord(id: String, name: String, surname: String, subject: String){

        val id = id
        val name = name
        val surName = surname
        val subject = subject

        val databaseHandler: DatabaseHandler= DatabaseHandler(this)
        if(id.trim()!="" && name.trim()!="" && surName.trim()!=""){
            val status = databaseHandler.addEmployee(PersonModelClass(id, name, surName, subject))
            if(status > -1){
//                Toast.makeText(applicationContext, "record save", Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(applicationContext, "Something...", Toast.LENGTH_LONG).show()
        }
    }

    //method for saving records in database
    fun saveStudentsRecord(id: String, name: String, surname: String, subject: String){

        val id = id
        val name = name
        val surName = surname
        val subject = subject

        val databaseHandlerStudent: DatabaseHandlerStudent = DatabaseHandlerStudent(this)
        if(id.trim()!="" && name.trim()!="" && surName.trim()!=""){
            val status = databaseHandlerStudent.addEmployee(PersonModelClass(id, name, surName, subject))
        }else{
            Toast.makeText(applicationContext, "Something...", Toast.LENGTH_LONG).show()
        }
    }

    //method for read records from database in ListView
    fun viewRecord(sub: String){
        //creating the instance of DatabaseHandler class
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val emp: List<PersonModelClass> = databaseHandler.viewEmployee()
        val users = ArrayList<PersonModelClass>()

        for(e in emp) {
            if (e.userSubject.equals(sub)) {
                users.add(PersonModelClass("" + e.userId, "" + e.userName, "" + e.userSurName, "" + e.userSubject))
            }else if(sub.equals("gg")){
                users.add(PersonModelClass("" + e.userId, "" + e.userName, "" + e.userSurName, "" + e.userSubject))
            }
        }
        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CustomAdapter(users)
        recyclerView.adapter = adapter
    }

    //method for read records from database in ListView
    fun viewStudentRecord(sub: String){
        //creating the instance of DatabaseHandler class
        val databaseHandlerStudent: DatabaseHandlerStudent = DatabaseHandlerStudent(this)
        val emp: List<PersonModelClass> = databaseHandlerStudent.viewEmployee()
        val users = ArrayList<PersonModelClass>()

        for(e in emp) {

            if (e.userSubject.equals(sub)) {
                IdUser = e.userId
                NameUser = e.userName
                SurNameUser = e.userSurName
                SubjectUser = e.userSubject


                users.add(PersonModelClass("" + e.userId, "" + e.userName, "" + e.userSurName, "" + e.userSubject))

            }else if(sub.equals("gg")){
                users.add(PersonModelClass("" + e.userId, "" + e.userName, "" + e.userSurName, "" + e.userSubject))
            }
        }

        val recyclerView = findViewById(R.id.recyclerView2) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CustomAdapterStudent(users, this)
        recyclerView.adapter = adapter
    }


    // clicked on item
    override fun onItemClick(position: Int) {

        val databaseHandlerStudent: DatabaseHandlerStudent = DatabaseHandlerStudent(this)
        val emp: List<PersonModelClass> = databaseHandlerStudent.viewEmployee()
        val orientation =  getOrientation()
        val clikedItem = emp[position]

//        Toast.makeText(this, "" + position + "" + clikedItem, Toast.LENGTH_SHORT).show()

        if (orientation == 2){
            val intent = Intent(applicationContext, StudentDetail::class.java)

            if (subjects.equals("all")) {
                intent.putExtra("Id", "" + clikedItem.userId)
                intent.putExtra("Name", "" + clikedItem.userName)
                intent.putExtra("SurName", "" + clikedItem.userSurName)
                intent.putExtra("Subject", "" + clikedItem.userSubject)
                startActivity(intent)
            }else{
                intent.putExtra("Id", "" + IdUser)
                intent.putExtra("Name", "" + NameUser)
                intent.putExtra("SurName", "" + SurNameUser)
                intent.putExtra("Subject", "" + SubjectUser)
                startActivity(intent)

            }

        }else if (orientation == 1){

            val userId = findViewById<TextView>(R.id.textViewUserid1)
            val userName = findViewById<TextView>(R.id.textViewUsername1)
            val userSurName = findViewById<TextView>(R.id.textViewAddress1)
            val userSubject = findViewById<TextView>(R.id.textViewUserSubject1)

            if (subjects.equals("all")) {
                userId.setText("" + clikedItem.userId)
                userName.setText("" + clikedItem.userName)
                userSurName.setText("" + clikedItem.userSurName)
                userSubject.setText("" + clikedItem.userSubject)
            }else{
                userId.setText("" + IdUser)
                userName.setText("" + NameUser)
                userSurName.setText("" + SurNameUser)
                userSubject.setText("" + SubjectUser)
            }
        }
    }


    // this function telling us screens orientation
    fun getOrientation(): Int {
        return if (resources.displayMetrics.widthPixels > resources.displayMetrics.heightPixels) {
            val t = Toast.makeText(this, "LANDSCAPE", Toast.LENGTH_SHORT)
//            t.show()
            1
        } else {
            val t = Toast.makeText(this, "PORTRAIT", Toast.LENGTH_SHORT)
//            t.show()
            2
        }
    }

}
