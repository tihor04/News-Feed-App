package com.example.newsfeed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.w3c.dom.Text

class newsAdapter(private val listner : NewsItemClicked) : RecyclerView.Adapter<newsviewholder>() {

   private  val items: ArrayList<News> = ArrayList()

    //the onCreateViewHolder function returns a newsviewholder which uses the view that we created for indivisual view of our recycler view//
    //and also detect if the news item is clicked//

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsviewholder {
       val view= LayoutInflater.from(parent.context).inflate(R.layout.itemnews,parent,false)
       val viewholder =newsviewholder(view)

        view.setOnClickListener{
       listner.onItemClicked(items[viewholder.adapterPosition])
        }
        return viewholder
    }

    //this function helps us to map a perticular data to its corresponding view//

    override fun onBindViewHolder(holder: newsviewholder, position: Int) {
  val currentitem=items[position]
        holder.titleview.text=currentitem.title
        holder.date.text=currentitem.time
        Glide.with(holder.itemView.context).load(currentitem.imageurl).into(holder.image)
    }


    //this function just returns the number of items present in the data arraylist//
    override fun getItemCount(): Int {
        return items.size
    }

    //this function helps us to update the items arrayList//
    fun updatenews( updateditems: ArrayList<News>){
        items.clear()
        items.addAll(updateditems)

        //the below function tells the adapter to start the three overridden function again...
        notifyDataSetChanged()
    }
}

//it is like a wrapping arround the data which contains the data abt the individual items//
class newsviewholder(itemView: View): RecyclerView.ViewHolder(itemView) {
  val titleview:TextView=itemView.findViewById(R.id.title)
    val image:ImageView=itemView.findViewById(R.id.newsimage)
    val date:TextView=itemView.findViewById(R.id.date)
}

//this interface will inform the activity about the click that is performed in the adataper class

interface NewsItemClicked{
    fun onItemClicked(Item:News)
}