package abdulhannanmayo.app.readingjson.Adapter

import abdulhannanmayo.app.readingjson.Model.PersonModelClass
import abdulhannanmayo.app.readingjson.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CustomAdapterStudent(val userList: ArrayList<PersonModelClass>,
private  val listener: OnItemClickListener
) : RecyclerView.Adapter<CustomAdapterStudent.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_layout2, parent, false)
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
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener{

        fun bindItems(user: PersonModelClass) {

            val textViewName = itemView.findViewById(R.id.textViewUserName) as TextView
            textViewName.text = user.userName

        }

        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}