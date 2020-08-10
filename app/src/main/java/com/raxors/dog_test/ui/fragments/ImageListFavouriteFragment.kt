package com.raxors.dog_test.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.raxors.dog_test.DogApplication
import com.raxors.dog_test.ui.activities.MainActivity
import com.raxors.dog_test.R
import com.raxors.dog_test.data.entity.FavouriteMap
import com.raxors.dog_test.domain.use_cases.FavouriteUseCases
import com.raxors.dog_test.ui.adapters.ImageListAdapter
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class ImageListFavouriteFragment : Fragment() {

    private val ARG_PARAM1 = "title"
    private val ARG_PARAM2 = "imageList"

    @Inject
    lateinit var favouriteUseCases: FavouriteUseCases

    private var favouriteList: List<String>? = null

    private var title: String? = null

    private lateinit var favourites: FavouriteMap

    private var listUrl: List<String> = listOf()
    var viewPager: ViewPager2? = null
    private lateinit var adapter: ImageListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_image_list, container, false)
        viewPager = root.findViewById(R.id.viewPagerDogImages)
        return root
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as MainActivity).title = title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DogApplication.component.inject(this)
        setHasOptionsMenu(true)
        (activity as MainActivity).navView?.visibility = View.GONE
        arguments?.let {
            favouriteList = it.getStringArrayList(ARG_PARAM2)
            title = it.getString(ARG_PARAM1)
        }
    }

    override fun onResume() {
        super.onResume()
        favourites = favouriteUseCases.favourites
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        favourites = favouriteUseCases.favourites
        favouriteList?.let { listUrl = it}
            adapter = ImageListAdapter(context, favouriteList as ArrayList<String>, getIsFavourite(favourites.favouriteList,
                favouriteList as ArrayList<String>
            ))
            adapter.setFavouritesClickListener(object : ImageListAdapter.FavouritesClickListener {
                override fun onClick(position: Int, view: View?) {
                    val favouriteImage = (favouriteList as ArrayList<String>)[position]
                    favouritesAdder(favourites.favouriteList, favouriteImage)
                }
            })
            viewPager?.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            viewPager?.adapter = adapter
    }

    private fun favouritesAdder(favourites: java.util.LinkedHashMap<String, LinkedList<String>>, favouriteImage: String) {
        if (favourites.containsKey(title.toString())) {
            if (favourites[title.toString()]!!.contains(favouriteImage)) {
                favourites[title.toString()]!!.remove(favouriteImage)
                if (favourites[title.toString()]!!.isEmpty()) {
                    favourites.remove(title.toString())
                }
            } else {
                favourites[title.toString()]!!.add(favouriteImage)
            }
        } else {
            favourites[title.toString()] = LinkedList(
                listOf(favouriteImage))
        }
    }

    private fun getIsFavourite(favourites: java.util.LinkedHashMap<String, LinkedList<String>>, favouriteImageList: List<String>): Set<Int> {
        val listIndexFavourites = mutableSetOf<Int>()
        if (favourites.containsKey(title.toString())) {
            for ((i, e) in favouriteImageList.withIndex()) {
                if (favourites[title.toString()]!!.contains(e)) listIndexFavourites.add(i)
            }
        }
        return listIndexFavourites
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater)
        menuInflater.inflate(R.menu.menu_main, menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> if (listUrl.isNotEmpty()) shareImage(listUrl[viewPager?.currentItem!!]) else return true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun shareImage(url: String): Boolean {
        val share = Intent(Intent.ACTION_SEND)
        share.type = "text/plain"
        share.putExtra(Intent.EXTRA_SUBJECT, "DogImage")
        share.putExtra(Intent.EXTRA_TEXT, url)
        startActivity(Intent.createChooser(share, "Share this dog image"))
        return true
    }

    override fun onPause() {
        super.onPause()
        favouriteUseCases.favourites = favourites
    }
}