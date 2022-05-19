package com.example.touristapp

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.touristapp.Model.Memories
import com.example.touristapp.Model.MemoryAdapter
import com.example.touristapp.Model.TouristDatabase
import java.io.ByteArrayOutputStream


class Tourist_Memories : Fragment(R.layout.fragment_tourist__memories) {
    lateinit var memoryAdapter: MemoryAdapter
    lateinit var memory: MutableList<Memories>
    private val TAG = "TouristMemories"
    lateinit var db: TouristDatabase
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var db = activity?.let { TouristDatabase(it) }

        memory = ArrayList()
        memory.add(db!!.getMemory(1))

        val recycleView = view?.findViewById<RecyclerView>(R.id.memories_recycleV)
        recycleView.layoutManager = LinearLayoutManager(activity)
        recycleView.setHasFixedSize(true)
        memoryAdapter = MemoryAdapter(requireActivity(),memory, object: MemoryAdapter.OnClickListener{
            override fun onItemClick(position: Int) {
                var bundle = Bundle()
                bundle.putSerializable("memory",memory[position])
                val fragment = TouristMemoriesView()
                fragment.arguments = bundle
                parentFragmentManager.setFragmentResult("showMemory",bundle)

                //This section will send the user to display_review() fragment
                var fragmentManager = activity!!.supportFragmentManager
                var fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frameLayout,fragment)
                fragmentTransaction.addToBackStack("Tourist_Memory")
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
        val addBtn = view?.findViewById<Button>(R.id.memories_addImgBtn)
        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedImage = data?.data
                // converts the image type from Uri to bitmap
                val bitmapImage = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, selectedImage)
                // converts bitmap to ByteArray
                val stream = ByteArrayOutputStream()
                bitmapImage.compress(Bitmap.CompressFormat.PNG,90,stream)
                val image = stream.toByteArray()
                // creates pop up that will create memories object and adds it to the list and update the adapter
                showAlertDialog(image)

            }
        }

        addBtn.setOnClickListener {
            Log.i(TAG, "btn press")
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.type = "image/*"
            //returns the data selected
            resultLauncher.launch(galleryIntent)
            Log.i(TAG, "Go to Gallery")
        }
    }

    /**
     * Creates a pop up to add information about the memory
     */
    private fun showAlertDialog(data: ByteArray) {
        val placeUserInfo =
            LayoutInflater.from(activity).inflate(R.layout.dialog_create_memory, null)
        val dialog = AlertDialog.Builder(activity)
            .setTitle("Create Memory")
            .setView(placeUserInfo)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("OK", null).show()

        // adds listener when the positive btn (OK) is pressed on the dialog
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            val name = placeUserInfo.findViewById<EditText>(R.id.dialog_memories_name)?.text.toString()
            val location = placeUserInfo.findViewById<EditText>(R.id.dialog_memories_location)?.text.toString()

            if (name.trim().isEmpty() || location.trim().isEmpty()) {
                Toast.makeText(activity, "Make sure all the info is added", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
            val m = Memories( location, data, 1, 1)
            // adds object to list of memories
            memory.add(m)
            db.addMemory(m)
            // notifies the adapter that the list has been updated
            memoryAdapter.notifyItemInserted(memory.size - 1)
            // ends pop up
            dialog.dismiss()

        }
    }

    private fun generateMemory():List<Memories> {
        return listOf(
            Memories("London,England", byteArrayOf(),1,1),
            Memories("Paris,France", byteArrayOf(),2,1),
            Memories("London,England", byteArrayOf(),1,1),
            Memories("Paris,France", byteArrayOf(),2,1))
    }
}