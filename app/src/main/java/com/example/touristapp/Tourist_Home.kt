package com.example.touristapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.touristapp.Model.*


class Tourist_Home : Fragment(R.layout.fragment_tourist__home) {

    lateinit var reviews: MutableList<Review>
    lateinit var reviewAdapter: ReviewAdapter
    lateinit var memoryAdapter: MemoryAdapter
    lateinit var memory: ArrayList<Memories>

    /**
     * The things that will happen before the screen fully loads
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /**
         * The top half will set up the recycler view for the review adapters
         */
        var db = activity?.let { TouristDatabase(it) }
        if (db != null) {
            reviews = db.getTextReview()
        }
        var reviewList = getView()?.findViewById<RecyclerView>(R.id.tourist_home_recycler_review)
        //this will ensure the recycler view will be presented horizontal
        reviewList?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        // snap variable aims to ensure the items snap to each item of the recycler view,
        // not showing the gaps between when swiping
        val snap = LinearSnapHelper()
        snap.attachToRecyclerView(reviewList)

        reviewAdapter = ReviewAdapter(requireActivity(), reviews, object: ReviewAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                /**
                 * bundle is used to allow for data to be communicated with other fragments
                 * in this particular example data is send to the display_review() fragment
                 */

                var bundle = Bundle()
                bundle.putSerializable("review",reviews[position])
                val fragment = display_review()
                fragment.arguments = bundle
                //parentFragmentManager.setFragmentResult("showRev",bundle)

                //This section will send the user to display_review() fragment
                var fragmentManager = activity!!.supportFragmentManager
                var fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frameLayout,display_review())
                fragmentTransaction.addToBackStack("Tourist_Home")
                fragmentTransaction.commit()
            }
        })
        reviewList?.adapter = reviewAdapter
        val showMoreBtn = view?.findViewById<Button>(R.id.tourist_home_reviewBtn)

        showMoreBtn?.setOnClickListener {
            var fragmentManager = requireActivity().supportFragmentManager
            var fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frameLayout,tourist_review_home())
            fragmentTransaction.addToBackStack("Tourist_Home")
            fragmentTransaction.commit()
        }

        /**
         * Sets up the memory adapter the memory recycler view. Works similar to the example above
         */
        memory = ArrayList()
        memory.add(db!!.getMemory(1))

        val recycleView = view?.findViewById<RecyclerView>(R.id.tourist_home_recycler_memories)
        snap.attachToRecyclerView(recycleView)
        recycleView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        recycleView.setHasFixedSize(true)
        memoryAdapter = MemoryAdapter(requireActivity(),memory, object: MemoryAdapter.OnClickListener{
            override fun onItemClick(position: Int) {
                var bundle = Bundle()
                bundle.putSerializable("memory",memory[position])
                val fragment = display_review()
                fragment.arguments = bundle
                //parentFragmentManager.setFragmentResult("showMemory",bundle)

                //This section will send the user to display_review() fragment
                var fragmentManager = activity!!.supportFragmentManager
                var fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frameLayout,fragment)
                fragmentTransaction.addToBackStack("Tourist_Home")
                fragmentTransaction.commit()
            }
        }, object : MemoryAdapter.OnItemLongClickListener {
            //Adds long click listener that removes the element
            override fun onItemLongClicked(position: Int) {
                //Log.i(TAG,"held on position $position")
                memory.removeAt(position)
                memoryAdapter.notifyItemRemoved(position)
            }

        })
        recycleView.adapter = memoryAdapter
        val seeAllbtn = view?.findViewById<Button>(R.id.tourist_home_seeAllbtn)

        seeAllbtn.setOnClickListener {
            var fragmentManager = requireActivity().supportFragmentManager
            var fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frameLayout,Tourist_Memories())
            fragmentTransaction.addToBackStack("Tourist_Home")
            fragmentTransaction.commit()
        }
    }

    private fun generateReview():List<Review> {
        return listOf(
            Review("Bond","I Don't Like london","I went there and the feeling was not as great as I thought",
                2.0f,"London", byteArrayOf(),1,1),
            Review("Bond","I Like london","I went there and the feeling was not as great as I thought",
                2.0f,"London", byteArrayOf(),1,1)
        )
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