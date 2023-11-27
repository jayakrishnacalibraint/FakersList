package com.example.fakerslist.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fakerslist.BitmapHelper
import com.example.fakerslist.databinding.SinglePersonItemBinding
import com.example.fakerslist.fragments.PersonsListFragmentDirections
import com.example.fakerslist.model.PersonData
import dagger.hilt.android.qualifiers.ActivityContext

class PersonsAdapter(@ActivityContext private val context: Context) :
    ListAdapter<PersonData, PersonsAdapter.PersonViewHolder>(PersonCallback()) {

    private val colors = arrayOf("#FF977D", "#7DFF9A", "#9A7DFF", "#7DBEFF", "#FF7DDF")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val singlePersonItemBinding =
            SinglePersonItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return PersonViewHolder(singlePersonItemBinding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {

        val item: PersonData = getItem(position)

        //set the random color for each item
        val randomColor = colors[position % colors.size]
        holder.singlePersonItemBinding.root.setCardBackgroundColor(Color.parseColor(randomColor))

        val personProfilePic =BitmapHelper. generateInitialsImage(item.firstname, item.lastname, position)
        val personName = "${item.firstname}${item.lastname}"
        val personEmail = item.email

        //set the respective data for each view in the layout
        holder.singlePersonItemBinding.personNameText.text = personName
        holder.singlePersonItemBinding.personEmailText.text = personEmail
        holder.singlePersonItemBinding.personImageView.setImageBitmap(personProfilePic)

        val directions =
            PersonsListFragmentDirections.actionPersonsListFragmentToPersonDetailsFragment(
                personData = item,position=position
            )

        holder.singlePersonItemBinding.root.setOnClickListener {
            it.findNavController().navigate(directions)
        }


    }




    class PersonViewHolder(val singlePersonItemBinding: SinglePersonItemBinding) :
        RecyclerView.ViewHolder(singlePersonItemBinding.root)
}

class PersonCallback : DiffUtil.ItemCallback<PersonData>() {
    override fun areItemsTheSame(oldItem: PersonData, newItem: PersonData): Boolean {
        val oldName = "${oldItem.firstname}${oldItem.lastname}"
        val newName = "${newItem.firstname}${newItem.lastname}"
        return oldName == newName
    }

    override fun areContentsTheSame(oldItem: PersonData, newItem: PersonData): Boolean {
        return oldItem == newItem
    }

}