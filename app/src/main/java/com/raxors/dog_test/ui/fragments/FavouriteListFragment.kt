package com.raxors.dog_test.ui.fragments

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
import com.raxors.dog_test.data.entity.Favourite
import com.raxors.dog_test.data.entity.FavouriteMap
import com.raxors.dog_test.databinding.FragmentFavouritesBinding
import com.raxors.dog_test.ui.adapters.FavouriteListAdapter
import com.raxors.dog_test.ui.view_models.FavouriteViewModel
import java.util.ArrayList
import java.util.function.Consumer

class FavouriteListFragment : Fragment() {

    private lateinit var favouriteViewModel: FavouriteViewModel

    lateinit var binding: FragmentFavouritesBinding

    var adapter: FavouriteListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favourites,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        adapter?.notifyDataSetChanged()
        (activity as MainActivity).progressBar?.visibility = View.GONE
        super.onActivityCreated(savedInstanceState)
        favouriteViewModel = ViewModelProvider(this).get(FavouriteViewModel::class.java)
        favouriteViewModel.favourites.observe(viewLifecycleOwner, Observer {
            (activity as MainActivity).progressBar?.visibility = View.GONE
            val favouriteList = it.toFavouriteList()
            adapter = FavouriteListAdapter(favouriteList)
            adapter?.notifyDataSetChanged()
            adapter?.setItemClickListener(object :
                FavouriteListAdapter.ItemClickListener {
                override fun onClick(position: Int, view: View?) {
                    openImageListFragment(favouriteList[position])
                    adapter?.notifyDataSetChanged()
                }
            })
            binding.favouriteList.layoutManager = LinearLayoutManager(context)
            binding.favouriteList.adapter = adapter
            binding.favouriteList.addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
        })
    }

    fun FavouriteMap.toFavouriteList(): List<Favourite> {
        val keyList: ArrayList<String> = ArrayList(this.favouriteList.keys)
        val favouriteList: ArrayList<Favourite> = ArrayList()
        keyList.forEach(Consumer { favouriteName: String ->
            this.favouriteList[favouriteName]?.let { Favourite(favouriteName, it) }
                ?.let { favouriteList.add(it) }
        })
        return favouriteList
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).navView?.visibility = View.VISIBLE
    }

    fun openImageListFragment(favourite: Favourite) {
        val imageList = ArrayList<String>(favourite.images)
        val bundle = Bundle()
        bundle.putStringArrayList("imageList", imageList)
        bundle.putString("title", favourite.name)
        findNavController().navigate(
            R.id.action_navigation_favourites_to_imageListFavouriteFragment,
            bundle
        )
    }
}