package abdulhannanmayo.app.readingjson.Adapter

import abdulhannanmayo.app.readingjson.Model.PersonModelClass
import abdulhannanmayo.app.readingjson.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(val userList: ArrayList<PersonModelClass>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(userList[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: PersonModelClass) {
            val textViewName = itemView.findViewById(R.id.textViewUsername) as TextView
            val textViewAddress  = itemView.findViewById(R.id.textViewAddress) as TextView
            val textViewid  = itemView.findViewById(R.id.textViewUserid) as TextView
            val textViewSubject  = itemView.findViewById(R.id.textViewUserSubject) as TextView
            textViewid.text = user.userId
            textViewName.text = user.userName
            textViewAddress.text = user.userSurName
            textViewSubject.text = user.userSubject
        }
    }
}