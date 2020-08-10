package com.raxors.dog_test.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.raxors.dog_test.ui.activities.MainActivity
import com.raxors.dog_test.R
import com.raxors.dog_test.data.entity.Dog
import com.raxors.dog_test.databinding.FragmentDogListBinding
import com.raxors.dog_test.ui.adapters.DogListAdapter
import com.raxors.dog_test.ui.view_models.DogViewModel

class DogListFragment : Fragment() {

    private lateinit var dogViewModel: DogViewModel

    lateinit var binding: FragmentDogListBinding

    lateinit var adapter: DogListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dog_list,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).progressBar?.visibility = View.VISIBLE
        dogViewModel = ViewModelProvider(this).get(DogViewModel::class.java)
        dogViewModel.error.observe(viewLifecycleOwner, Observer {
            val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
            builder.setMessage(it)
                .setTitle(R.string.dialog_error_title)
            builder.setPositiveButton(R.string.ok
            ) { dialog, id ->
                (activity as MainActivity).progressBar?.visibility = View.GONE
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        })
        dogViewModel.dogs.observe(viewLifecycleOwner, Observer {
            (activity as MainActivity).progressBar?.visibility = View.GONE
            adapter = DogListAdapter(it)
            adapter.setItemClickListener(object :
                DogListAdapter.ItemClickListener {
                override fun onClick(position: Int, view: View?) {
                    if (it[position].subDogs.isNotEmpty()) {
                        openSubbreedsFragment(it[position])
                    } else {
                        openImageListFragment(it[position])
                    }
                }
            })
            binding.dogList.layoutManager = LinearLayoutManager(context)
            binding.dogList.adapter = adapter
            binding.dogList.addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
        })
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).navView?.visibility = View.VISIBLE
    }

    fun openSubbreedsFragment(dog: Dog){
        val subbreedList = dog.subDogs as ArrayList<String>
        val bundle = Bundle()
        bundle.putStringArrayList("subbreedList", subbreedList)
        bundle.putString("title", dog.name)
        findNavController().navigate(
            R.id.action_navigation_list_to_navigation_subbreed_list,
            bundle
        )
    }

    fun openImageListFragment(dog: Dog) {
        val bundle = Bundle()
        bundle.putString("title", dog.name)
        findNavController().navigate(
            R.id.action_navigation_list_to_navigation_image_list,
            bundle
        )
    }
}