package com.example.touristapp

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentResultListener
import com.example.touristapp.Model.Future_location
import com.example.touristapp.Model.UserMap

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class CreateMap : Fragment() {
    private val TAG = "CreateMap"

    private lateinit var gMap: GoogleMap
    private var markers: MutableList<Marker> = mutableListOf()
    private var localInfo: MutableList<MutableList<String>> = mutableListOf()
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        gMap = googleMap
        gMap.setOnInfoWindowClickListener { markerToDelete ->
            markers.remove(markerToDelete)
            markerToDelete.remove()
        }

        gMap.setOnMapLongClickListener { latLng ->
            showAlertDialog(latLng)

        }
    }

    /**
     * Creates a dialog where user input data based on the Tourist_Future class
     */

    private fun showAlertDialog(latLng: LatLng) {
        val placeUserInfo = LayoutInflater.from(activity).inflate(R.layout.dialog_create_marker,null)
        val dialog = AlertDialog.Builder(activity)
            .setTitle("Create marker")
            .setView(placeUserInfo)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("OK", null).show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            val title = placeUserInfo.findViewById<EditText>(R.id.marker_etTitle)?.text.toString()
            val description = placeUserInfo.findViewById<EditText>(R.id.marker_etDescription)?.text.toString()
            val country = placeUserInfo.findViewById<EditText>(R.id.marker_etCountry)?.text.toString()
            val type = placeUserInfo.findViewById<EditText>(R.id.marker_etTypeOfLocation)?.text.toString()
            val townCity = placeUserInfo.findViewById<EditText>(R.id.etMarker_TownCity)?.text.toString()
            val booked = placeUserInfo.findViewById<CheckBox>(R.id.marker_cbBooked)

            if (title.trim().isEmpty() || description.trim().isEmpty() || country.trim().isEmpty()
                || townCity.trim().isEmpty() || type.trim().isEmpty()) {
                Toast.makeText(
                    activity,
                    "All info must must be entered",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            val choice: MutableList<String> = mutableListOf()
            choice.add(country)
            choice.add(townCity)
            if (booked.isChecked)
                choice.add("true")
            else
                choice.add("false")
            localInfo.add(choice)
            val marker = gMap.addMarker(MarkerOptions().position(latLng).title(title).snippet(description))
            markers.add(marker)
            dialog.dismiss()

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_map, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * check create a check/get current user id in database handle, and futurelocationId
     */


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        activity?.findViewById<FloatingActionButton>(R.id.cm_Save)?.setOnClickListener {
            if (markers.isEmpty()){
                Toast.makeText(activity,"There must be a minimum of one marker",Toast.LENGTH_LONG).show()
            }
            else {
                val places: ArrayList<Future_location> = arrayListOf<Future_location>()

                for ((i, m) in markers.withIndex()){

                    val mark = Future_location(m.title,localInfo.get(i).get(0),
                        localInfo.get(i).get(1),m.snippet,
                        localInfo.get(i).get(2),1,2,
                        m.position.latitude,m.position.longitude)

                    places.add(mark)

                }
//                val places = markers.map { marker ->
//                    Future_location(
//                        marker.title,
//                        localInfo.get(i).get(0),
//                        localInfo.get(i).get(1),
//                        localInfo.get(i).get(2),
//                        marker.snippet,
//                        localInfo.get(i).get(3).toBoolean(),
//                        1,
//                        2,
//                        marker.position.latitude,
//                        marker.position.longitude
//                    )
//                    i++
//                } as List<Future_location>

                Log.i(TAG,"gotten $places")
                var userM = UserMap("awd", places)
                parentFragmentManager.setFragmentResultListener("userMapTitle",this, FragmentResultListener {
                        requestKey, result ->
                    userM.title = result.getString("title").toString()
                })

                var bundle = Bundle()
                bundle.putSerializable("choice", userM)
                val fragment = Tourist_Future_Interest()
                fragment.arguments = bundle
                parentFragmentManager.setFragmentResult("saveMark", bundle)


                var fragmentManager = requireActivity().supportFragmentManager
                var fragmentTrasaction = fragmentManager.beginTransaction()
                fragmentTrasaction.replace(R.id.frameLayout, Tourist_Future_Interest())
                fragmentTrasaction.addToBackStack("CreateMap")
                fragmentTrasaction.commit()
            }
        }
        if (mapFragment != null) {
            mapFragment.view?.let {
                Snackbar.make(it,"Long press on a location to add a marker!",Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK",{})
                    .setActionTextColor(ContextCompat.getColor(requireActivity(),android.R.color.white))
                    .show()
            }
        }
    }
}