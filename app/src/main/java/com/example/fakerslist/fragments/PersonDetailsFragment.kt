package com.example.fakerslist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.fakerslist.R
import com.example.fakerslist.databinding.FragmentPersonDetailsBinding
import com.example.fakerslist.helpers.BitmapHelper
import com.example.fakerslist.viewmodel.PersonDetailsViewModel


class PersonDetailsFragment : Fragment() {

    private lateinit var personDetailsBinding: FragmentPersonDetailsBinding
    private lateinit var toolbar: Toolbar
    private lateinit var imageView: ImageView

    private val personDetailsFragmentArgs: PersonDetailsFragmentArgs by navArgs()
    private val personDetailsViewModel: PersonDetailsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        personDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_person_details, container, false)
        initializeViews()

        val personData = personDetailsFragmentArgs.personData

        val position = personDetailsFragmentArgs.position
        val bitmap =
            BitmapHelper.generateInitialsImage(personData.firstname, personData.lastname, position)
        imageView.setImageBitmap(bitmap)
        personDetailsViewModel.setPersonDetails(personData)
        personDetailsViewModel.personDetails.observe(viewLifecycleOwner) {
            personDetailsBinding.personDetails = personData
            personDetailsBinding.executePendingBindings()
        }
        return personDetailsBinding.root
    }

    private fun initializeViews() {
        toolbar = personDetailsBinding.toolbar
        imageView = personDetailsBinding.personDetailImage
        setToolbar()

    }


    private fun setToolbar() {
        val fragmentActivity: AppCompatActivity = requireActivity() as AppCompatActivity
        fragmentActivity.setSupportActionBar(toolbar)
        fragmentActivity.supportActionBar?.apply {
            title = getString(R.string.persons_details)
        }
    }


}