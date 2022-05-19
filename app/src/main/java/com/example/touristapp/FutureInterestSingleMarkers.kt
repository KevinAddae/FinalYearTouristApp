package com.example.touristapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import androidx.fragment.app.FragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.touristapp.Model.Future_location
import com.example.touristapp.Model.MapsAdapter
import com.example.touristapp.Model.SingleMarkerAdapter
import com.example.touristapp.Model.UserMap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class FutureInterestSingleMarkers : Fragment(R.layout.fragment_future_interest_single_markers) {
    lateinit var mapAdapter: SingleMarkerAdapter
    lateinit var singleMarks: ArrayList<Future_location>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Single Markers"
        singleMarks = ArrayList()
//        parentFragmentManager.setFragmentResultListener("singleMark",this, FragmentResultListener {
//                requestKey, result ->
//
//            val input = result.getSerializable("MarkList") as UserMap
//            input.places.forEach { s ->
//                singleMarks.add(s)
//            }
//        })

        val recycler = view?.findViewById<RecyclerView>(R.id.maps_singleMark_recycler)
        recycler.layoutManager = LinearLayoutManager(activity)
        mapAdapter = SingleMarkerAdapter(requireActivity(), singleMarks, object: SingleMarkerAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                var bundle = Bundle()
                bundle.putSerializable("single", singleMarks[position])
                val fragment = DisplayMap()
                fragment.arguments = bundle
                //parentFragmentManager.setFragmentResult("displaySingle",bundle)

                var fragmentManager = activity!!.supportFragmentManager
                var fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frameLayout, fragment)
                fragmentTransaction.addToBackStack("Tourist_Future_Interest")
                fragmentTransaction.commit()
            }
        })
        recycler.adapter = mapAdapter
    }
}