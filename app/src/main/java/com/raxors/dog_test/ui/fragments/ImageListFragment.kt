package com.raxors.dog_test.ui.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.raxors.dog_test.DogApplication
import com.raxors.dog_test.ui.activities.MainActivity
import com.raxors.dog_test.R
import com.raxors.dog_test.data.entity.FavouriteMap
import com.raxors.dog_test.domain.use_cases.FavouriteUseCases
import com.raxors.dog_test.ui.adapters.ImageListAdapter
import com.raxors.dog_test.ui.view_models.DogImageViewModel
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class ImageListFragment : Fragment() {

    private val ARG_PARAM1 = "title"
    private val ARG_PARAM2 = "breedName"

    @Inject
    lateinit var favouriteUseCases: FavouriteUseCases

    private var title: String? = null
    private var breedName: String? = null

    private lateinit var favourites: FavouriteMap

    private var listUrl: List<String> = listOf()
    var viewPager: ViewPager2? = null
    private lateinit var adapter: ImageListAdapter

    private lateinit var dogImageViewModel: DogImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DogApplication.component.inject(this)
        setHasOptionsMenu(true)
        (activity as MainActivity).navView?.visibility = View.GONE
        (activity as MainActivity).progressBar?.visibility = View.GONE
        arguments?.let {
            title = it.getString(ARG_PARAM1)
            breedName = it.getString(ARG_PARAM2)
        }
    }

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

    override fun onResume() {
        super.onResume()
        favourites = favouriteUseCases.favourites
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).progressBar?.visibility = View.VISIBLE
        var name = ""
        name = if (breedName != null) "$breedName/$title" else title.toString()
        dogImageViewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return DogImageViewModel(name) as T
            }
        })[DogImageViewModel::class.java]
        favourites = favouriteUseCases.favourites
        dogImageViewModel.error.observe(viewLifecycleOwner, Observer {
            val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
            builder.setMessage(it)
                .setTitle(R.string.dialog_error_title)
            builder.setPositiveButton(
                R.string.ok
            ) { dialog, id ->
                (activity as MainActivity).progressBar?.visibility = View.GONE
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        })
        dogImageViewModel.images.observe(viewLifecycleOwner, Observer {
            (activity as MainActivity).progressBar?.visibility = View.GONE
            it?.let {listUrl = it}
            adapter = ImageListAdapter(context, it as ArrayList<String>, getIsFavourite(favourites.favouriteList, it))
            adapter.setFavouritesClickListener(object : ImageListAdapter.FavouritesClickListener {
                override fun onClick(position: Int, view: View?) {
                    val favouriteImage = it[position]
                            favouritesAdder(favourites.favouriteList, favouriteImage)
                }
            })
            viewPager?.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            viewPager?.adapter = adapter
        })
    }

    private fun favouritesAdder(favourites: LinkedHashMap<String, LinkedList<String>>, favouriteImage: String) {
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

    override fun onPause() {
        super.onPause()
        favouriteUseCases.favourites = favourites
    }

    private fun getIsFavourite(favourites: LinkedHashMap<String, LinkedList<String>>, favouriteImageList: List<String>): Set<Int> {
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
}