package com.example.touristapp

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.FragmentResultListener
import com.example.touristapp.Model.Future_location
import com.example.touristapp.Model.UserMap

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class DisplayMap : Fragment() {

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


        parentFragmentManager.setFragmentResultListener("futureMark",this, FragmentResultListener {
                requestKey, result ->
            val boundsBuilder = LatLngBounds.Builder()
            val input = result.getSerializable("list") as UserMap

            input.places.forEach { s ->

                var latLng = LatLng(s.latitude,s.longitude)
                boundsBuilder.include(latLng)
                googleMap.addMarker(MarkerOptions().position(latLng).title(s.name).snippet(s.description))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(),1000,1000,0))
            }

        })

        parentFragmentManager.setFragmentResultListener("displaySingle",this, FragmentResultListener {
                requestKey, result ->
            val boundsBuilder = LatLngBounds.Builder()
            val input = result.getSerializable("single") as Future_location

            var latLng = LatLng(input.latitude,input.longitude)
            boundsBuilder.include(latLng)
            googleMap.addMarker(MarkerOptions().position(latLng).title(input.name).snippet(input.description))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(),1000,1000,0))

        })
        /**
        var latLng = LatLng(input.latitude,input.longtitiude)
        googleMap.addMarker(MarkerOptions().position(latLng).title(input.name).snippet(input.description))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
         **/
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_display_map, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}