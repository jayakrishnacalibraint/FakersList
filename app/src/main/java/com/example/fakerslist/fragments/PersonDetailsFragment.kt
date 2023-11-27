package com.example.fakerslist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.fakerslist.BitmapHelper
import com.example.fakerslist.R
import com.example.fakerslist.databinding.FragmentPersonDetailsBinding
import com.example.fakerslist.model.PersonData


class PersonDetailsFragment : Fragment() {

    private lateinit var personDetailsBinding: FragmentPersonDetailsBinding
    private lateinit var personDetailsName: TextView
    private lateinit var personDetailEmail: TextView
    private lateinit var personDetailPhone: TextView
    private lateinit var personDetailBirthday: TextView
    private lateinit var personDetailGender: TextView
    private lateinit var personDetailAddress: TextView
    private lateinit var personDetailStreet: TextView
    private lateinit var personDetailStreetName: TextView
    private lateinit var personDetailBuildingNumber: TextView
    private lateinit var personDetailCity: TextView
    private lateinit var personDetailZipCode: TextView
    private lateinit var personDetailCountry: TextView
    private lateinit var personDetailCountryCode: TextView
    private lateinit var personDetailLatitude: TextView
    private lateinit var personDetailLongitude: TextView
    private lateinit var personDetailWebsite: TextView
    private lateinit var personDetailImage: ImageView
    private lateinit var toolbar: Toolbar

    private val personDetailsFragmentArgs: PersonDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        personDetailsBinding = FragmentPersonDetailsBinding.inflate(inflater, container, false)
        initializeViews()

        val personData = personDetailsFragmentArgs.personData

        val position = personDetailsFragmentArgs.position

        setPersonData(personData, position)

        return personDetailsBinding.root
    }

    private fun setPersonData(personData: PersonData, position: Int) {
        val name = "${personData.firstname}${personData.lastname}"
        val bitmap =
            BitmapHelper.generateInitialsImage(personData.firstname, personData.lastname, position)

        personDetailImage.setImageBitmap(bitmap)
        personDetailsName.text = getString(R.string.person_name, name)
        personDetailEmail.text = getString(R.string.person_email, personData.email)
        personDetailPhone.text = getString(R.string.person_phone, personData)
        personDetailBirthday.text = getString(R.string.person_birthday, personData)
        personDetailGender.text = getString(R.string.person_gender, personData)
        personDetailStreet.text = getString(R.string.person_street, personData)
        personDetailStreetName.text = getString(R.string.sperson_streetname, personData)
        personDetailBuildingNumber.text = getString(R.string.person_buildingnumber, personData)
        personDetailCity.text = getString(R.string.person_city, personData)
        personDetailZipCode.text = getString(R.string.person_zipcode, personData)
        personDetailCountry.text = getString(R.string.person_country, personData)
        personDetailCountryCode.text = getString(R.string.person_county_code, personData)
        personDetailLatitude.text = getString(R.string.person_latitude, personData)
        personDetailLongitude.text = getString(R.string.person_longitude, personData)
        personDetailWebsite.text = getString(R.string.website_website, personData)
    }

    private fun initializeViews() {
        toolbar = personDetailsBinding.toolbar
        setToolbar()

        personDetailsName = personDetailsBinding.personDetailName
        personDetailEmail = personDetailsBinding.personDetailEmail
        personDetailPhone = personDetailsBinding.personDetailPhone
        personDetailBirthday = personDetailsBinding.personDetailBirthday
        personDetailGender = personDetailsBinding.personDetailGender
        personDetailAddress = personDetailsBinding.personDetailAddress
        personDetailStreet = personDetailsBinding.personDetailStreet
        personDetailStreetName = personDetailsBinding.personDetailStreetName
        personDetailBuildingNumber = personDetailsBinding.personDetailBuildingNumber
        personDetailCity = personDetailsBinding.personDetailCity
        personDetailZipCode = personDetailsBinding.personDetailZipCode
        personDetailCountry = personDetailsBinding.personDetailCountry
        personDetailCountryCode = personDetailsBinding.personDetailCountryCode
        personDetailLatitude = personDetailsBinding.personDetailLatitude
        personDetailLongitude = personDetailsBinding.personDetailLongitude
        personDetailWebsite = personDetailsBinding.personDetailWebsite
        personDetailImage = personDetailsBinding.personDetailImage
    }

    private fun setToolbar() {
        val fragmentActivity: AppCompatActivity = requireActivity() as AppCompatActivity
        fragmentActivity.setSupportActionBar(toolbar)
        fragmentActivity.supportActionBar?.apply {
            title = getString(R.string.persons_details)
        }
    }


}