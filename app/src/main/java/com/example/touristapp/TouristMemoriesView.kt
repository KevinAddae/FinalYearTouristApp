package com.example.touristapp

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
//import androidx.fragment.app.FragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.touristapp.Model.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

private const val TAG = "TouristMemoriesView"

class TouristMemoriesView : Fragment(R.layout.fragment_tourist_memories_view) {
    lateinit var memoryAdapter: MemoryViewItemAdapter
    lateinit var listOfImg: MutableList<Memories>
    lateinit var db: TouristDatabase
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = activity?.let { TouristDatabase(it) }!!

        // Will get the image from the previous fragment and set it as the main image
        val img = view?.findViewById<ImageView>(R.id.memories_view_img)
        var bundle = this.arguments
        val input = bundle?.getSerializable("memory") as Memories
        Log.i(TAG,"sent item ${input.location}")

        val bitmap = BitmapFactory.decodeByteArray(input.image,0,input.image.size)
            img.setImageBitmap(bitmap)
        //})
        val recyclerV = view?.findViewById<RecyclerView>(R.id.memories_view_recycler)
        val snap = LinearSnapHelper()

        snap.attachToRecyclerView(recyclerV)
        recyclerV.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)

        recyclerV.setHasFixedSize(true)
        listOfImg = ArrayList()
        listOfImg.add(db.getMemory(1))
        listOfImg.add(input)
        memoryAdapter = MemoryViewItemAdapter(requireActivity(),listOfImg, object: MemoryViewItemAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                var input = listOfImg[position]
                val bitmap = BitmapFactory.decodeByteArray(input.image,0,input.image.size)
                img.setImageBitmap(bitmap)
            }
        })

        recyclerV.adapter = memoryAdapter

    }

    private fun generateMemory():List<Memories> {
        return listOf(
            Memories("London,England", byteArrayOf(),1,1),
            Memories("Paris,France", byteArrayOf(),2,1),
            Memories("London,England", byteArrayOf(),1,1),
            Memories("Paris,France", byteArrayOf(),2,1)
        )
    }

}