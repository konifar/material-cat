package com.konifar.materialcat.views.fragments

import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import com.konifar.materialcat.PhotoDetailActivity
import com.konifar.materialcat.R
import com.konifar.materialcat.databinding.FragmentCatsGridBinding
import com.konifar.materialcat.events.PhotoSearchCallbackEvent
import com.konifar.materialcat.models.PhotoModel
import com.konifar.materialcat.views.ListLoadingView
import com.konifar.materialcat.views.adapters.PhotosArrayAdapter
import com.konifar.materialcat.views.listeners.OnLoadMoreScrollListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class CatsGridFragment : Fragment() {

    lateinit var binding: FragmentCatsGridBinding

    private var adapter: PhotosArrayAdapter? = null
    private var loadingFooter: ListLoadingView? = null
    private var sort: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arguments = arguments
        if (arguments.containsKey(ARG_KEY_SORT)) {
            sort = arguments.getString(ARG_KEY_SORT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentCatsGridBinding>(inflater, R.layout.fragment_cats_grid, container, false)

        initGridView()
        initSwipeRefresh()
        showList(1)

        return binding.root
    }

    private fun initSwipeRefresh() {
        binding.swipeRefresh.setColorSchemeResources(R.color.theme500)
        binding.swipeRefresh.setOnRefreshListener { showList(1) }
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    private fun initGridView() {
        adapter = PhotosArrayAdapter(activity)
        loadingFooter = ListLoadingView(activity)
        binding.gridView.addFooterView(loadingFooter)
        binding.gridView.adapter = adapter
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.gridView.isNestedScrollingEnabled = true
        }

        binding.gridView.setOnItemClickListener { parent, view, position, id -> onItemClick(parent, view, position, id) }
    }

    internal fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val photo = adapter!!.getItem(position)
        PhotoDetailActivity.start(activity, view, photo)
    }

    private fun showList(page: Int) {
        if (page > 1) loadingFooter!!.switchVisible(true)
        PhotoModel.getInstance(activity).getCatPhotos(page, sort!!)
    }

    private fun initListViewScrollListener() {
        val scrollListener = activity as AbsListView.OnScrollListener
        binding.gridView.setOnScrollListener(object : OnLoadMoreScrollListener(scrollListener) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                showList(page)
            }
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: PhotoSearchCallbackEvent) {
        if (sort != event.sort) return

        if (binding.swipeRefresh.isRefreshing) {
            binding.swipeRefresh.isRefreshing = false
            adapter!!.clear()
        }

        if (event.isSuccess) {
            adapter!!.addAll(event.photos)
        }

        if (binding.loading.visibility == View.VISIBLE) {
            initListViewScrollListener()
            binding.loading.visibility = View.GONE
        }

        loadingFooter!!.switchVisible(false)
    }

    companion object {

        private val ARG_KEY_SORT = "arg_key_sort"

        fun newInstance(sort: String): CatsGridFragment {
            val fragment = CatsGridFragment()
            val bundle = Bundle()
            bundle.putString(ARG_KEY_SORT, sort)
            fragment.arguments = bundle
            return fragment
        }
    }

}