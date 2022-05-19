package com.example.touristapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.touristapp.Model.Future_location
import com.example.touristapp.Model.MapsAdapter
import com.example.touristapp.Model.TouristDatabase
import com.example.touristapp.Model.UserMap
import com.google.android.material.floatingactionbutton.FloatingActionButton


/**
 * This class interacts with the database handle to get (if it exists) the user location info and show it in a recycle view.
 * Also will allow for the user to interact with the selected element and show it marked on the map.
 * The activity relating to this task is called actitvity_tourist_future_location.xml
 */

private const val TAG = "tourist_future_location"


class Tourist_Future_Interest : Fragment(R.layout.fragment_tourist__future__interest) {
    lateinit var  userMapList1: ArrayList<UserMap>
    lateinit var mapAdapter: MapsAdapter

    /**
     * This method is called after the oncreate, overriding will give access to findviewbyId.
     * This method will also send send the listed items in the recycle view to the Display map fragment.
     * also adds a listener to the floating button which will send the user to create map fragment so more
     * items can be added to the recycle view.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = "Future Location"
        var db = activity?.let { TouristDatabase(it) }


        if (db != null) {
            Toast.makeText(activity,"logged in as ${db.getLastAddedTourist().username}", Toast.LENGTH_LONG).show()
        }



        //var temp:ArrayList<Future_location> = db!!.getFutureLocation(1)
        //var k = UserMap("Test",temp)
//        userMapList1 = ArrayList()
//        if (temp.size != 0) {
//            for (i in temp.indices) {
//                if (i + 1 != temp.size) {
//                    if (userMapList1.contains(temp[i])) {
//                        userMapList1[userMapList1.indexOf(temp[i])].places.addAll(temp[i].places)
//                    } else if (!userMapList1.contains(temp[i]))
//                        userMapList1.add(temp[i])
//                }
//            }
//        }

        //    userMapList1 = generateSample()
        // set the layout manager
        userMapList1 = generateSample()
        //userMapList1.add(db!!.getFutureLocation(1))
//        temp.forEach {
//            userMapList1.add(it)
//        }
        var maps = getView()?.findViewById<RecyclerView>(R.id.rvMaps)

        if (maps != null) {
            registerForContextMenu(maps)
        }

        maps?.layoutManager = LinearLayoutManager(activity)
        // set the layout adapter
        mapAdapter = MapsAdapter(requireActivity(), userMapList1, object: MapsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                Log.i(TAG, "on item click $position")
                Log.i(TAG,"gotten ${userMapList1.get(0)}")

                var bundle = Bundle()
                bundle.putSerializable("list", userMapList1[position])
                val fragment = DisplayMap()
                fragment.arguments = bundle

                var fragmentManager = activity!!.supportFragmentManager
                var fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frameLayout, fragment)
                fragmentTransaction.addToBackStack("Tourist_Future_Interest")
                fragmentTransaction.commit()
            }
        }, object : MapsAdapter.OnItemLongClickListener {
            override fun onItemLongClicked(position: Int, popm: PopupMenu) {
                popm.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.popup_Delete -> {
                            userMapList1.removeAt(position)
                            Log.i(TAG,"menu Tapped on position $position")
                            mapAdapter.notifyItemRemoved(userMapList1.size - 1)
                            return@setOnMenuItemClickListener true
                        }
                        R.id.popup_View -> {
                            var bundle = Bundle()
                            bundle.putSerializable("MarkList", userMapList1[position])
                            val fragment = FutureInterestSingleMarkers()
                            fragment.arguments = bundle
                            //parentFragmentManager.setFragmentResult("singleMark",bundle)

                            var fragmentManager = activity!!.supportFragmentManager
                            var fragmentTransaction = fragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.frameLayout, fragment)
                            fragmentTransaction.addToBackStack("Tourist_Future_Interest")
                            fragmentTransaction.commit()
                            return@setOnMenuItemClickListener true
                        }
                        else -> {return@setOnMenuItemClickListener false}
                    }
                }
            }

        })

        maps?.adapter = mapAdapter

        var createMarkerBtn = getView()?.findViewById<FloatingActionButton>(R.id.CreateMarker)
        createMarkerBtn?.setOnClickListener {
            showAlertDialog()
        }

        Log.i(TAG,"previous fragment ${getCallerFragment()}")
        /***
         * checks the name of the previous fragment as a condition
         * casts the information sent from the create map fragment
         */
        if (getCallerFragment().equals("CreateMap")) {
            var bundle = this.arguments
            val input = bundle?.getSerializable("item") as UserMap
            for(place in input.places) {
                var type = UserMap(input.title, arrayListOf(place))
                //db?.addFutureInterest(type)
            }
            userMapList1.add(input)
            mapAdapter.notifyItemInserted(userMapList1.size - 1)


        }
    }

    /**
     * gets the name of the last fragment
     */
    private fun getCallerFragment(): String? {
        val fm = fragmentManager
        val count = requireFragmentManager().backStackEntryCount
        return fm!!.getBackStackEntryAt(count - 1).name
    }


    private fun showAlertDialog() {
        val mapTitleForm = LayoutInflater.from(activity).inflate(R.layout.dialog_create_map_title,null)
        val dialog = AlertDialog.Builder(activity)
            .setTitle("Create marker")
            .setView(mapTitleForm)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("OK", null).show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            val title = mapTitleForm.findViewById<EditText>(R.id.dialogTitle)?.text.toString()
            if (title.trim().isEmpty()) {
                Toast.makeText(
                    activity,
                    "All info must must be entered",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            var bundle = Bundle()
            bundle.putString("title", title)
            val fragment = CreateMap()
            fragment.arguments = bundle

            var fragmentManager = requireActivity().supportFragmentManager
            var fragmentTrasaction = fragmentManager.beginTransaction()
            fragmentTrasaction.replace(R.id.frameLayout, fragment)
            fragmentTrasaction.addToBackStack("Future Interest")
            fragmentTrasaction.commit()
            dialog.dismiss()

        }
    }


    private fun generateSample(): ArrayList<UserMap>{
        return arrayListOf(
            UserMap(
                "memories of uni",
                arrayListOf(
                    Future_location("Branner Hall","England","Brom",
                        "Just testing","true",1,2, 37.467,-122.173),
                    Future_location("Gates of smth", "Portugal","lisbon",
                        "another test allie","false",2,2,37.430,-122.163),
                    Future_location("Gates of Cn", "America","state",
                        "another test allie","false",3,2,37.444,-122.170)
                )
            ),
            UserMap("January vacation planning!",
                arrayListOf(
                    Future_location("Tokyo","Japan","Tokyo",
                        "Overnight layover","true",4,2, 35.67, 139.65),
                    Future_location("Ranchi","Japan","Tokyo",
                        "Family visit + wedding!","true",5,2, 23.34, 85.31),
                    Future_location("Singapore","Japan","Tokyo",
                        "Inspired by \"Crazy Rich Asians\"","true",4,2, 1.35, 103.82)
                )
            )
        )

    }
}