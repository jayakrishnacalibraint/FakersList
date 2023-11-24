package com.example.fakerslist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fakerslist.databinding.SinglePersonItemBinding
import com.example.fakerslist.model.Person
import dagger.hilt.android.qualifiers.ActivityContext

class PersonsAdapter(@ActivityContext private val context: Context) :
    ListAdapter<Person, PersonsAdapter.PersonViewHolder>(PersonCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val singlePersonItemBinding =
            SinglePersonItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return PersonViewHolder(singlePersonItemBinding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {

        val item: Person = getItem(position)


        holder.singlePersonItemBinding.personNameText.text = item.id.toString()
        holder.singlePersonItemBinding.personEmailText.text = item.email
        Glide.with(context).load(item.image).into(holder.singlePersonItemBinding.personImageView)

    }


    class PersonViewHolder(val singlePersonItemBinding: SinglePersonItemBinding) :
        RecyclerView.ViewHolder(singlePersonItemBinding.root) {

    }
}

class PersonCallback() : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }

}