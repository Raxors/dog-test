package com.raxors.dog_test.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.raxors.dog_test.ui.activities.MainActivity
import com.raxors.dog_test.R
import com.raxors.dog_test.databinding.FragmentSubbreedListBinding
import com.raxors.dog_test.ui.adapters.SubbreedListAdapter

class SubbreedsFragment : Fragment() {

    private val ARG_PARAM1 = "subbreedList"
    private val ARG_PARAM2 = "title"

    private var subbreedList: List<String>? = null
    private var title: String = ""
    lateinit var adapter: SubbreedListAdapter
    lateinit var binding: FragmentSubbreedListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).navView?.visibility = View.GONE
        arguments?.let {
            subbreedList = it.getStringArrayList(ARG_PARAM1)
            title = it.getString(ARG_PARAM2).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_subbreed_list,
            container,
            false
        )
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as MainActivity).title = title
        adapter = SubbreedListAdapter(subbreedList!!)
        adapter.setItemClickListener(object :
            SubbreedListAdapter.ItemClickListener {
            override fun onClick(position: Int, view: View?) {
                openImageListFragment(subbreedList!![position], title)
            }
        })
        binding.subbreedList.layoutManager = LinearLayoutManager(context)
        binding.subbreedList.adapter = adapter
        binding.subbreedList.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    fun openImageListFragment(name: String, breedName: String) {
        val bundle = Bundle()
        bundle.putString("title", name)
        bundle.putString("breedName", breedName)
        findNavController().navigate(
            R.id.action_navigation_subbreed_list_to_navigation_image_list,
            bundle
        )
    }
}