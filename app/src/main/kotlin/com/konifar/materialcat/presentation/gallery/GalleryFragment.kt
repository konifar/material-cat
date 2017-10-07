package com.konifar.materialcat.presentation.gallery

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ArrayAdapter
import android.widget.GridView
import com.konifar.materialcat.R
import com.konifar.materialcat.databinding.FragmentGalleryBinding
import com.konifar.materialcat.databinding.ViewPhotoBinding
import com.konifar.materialcat.domain.model.CatImageId
import com.konifar.materialcat.extension.component
import com.konifar.materialcat.presentation.common.customview.LoadingFooterView
import com.konifar.materialcat.presentation.common.customview.OnLoadMoreScrollListener
import com.konifar.materialcat.presentation.gallery.model.GalleryItemPresentationModel
import com.konifar.materialcat.presentation.photodetail.PhotoDetailActivity
import com.squareup.picasso.Picasso
import javax.inject.Inject

class GalleryFragment : Fragment(), GalleryContract.View, GalleryContract.Navigator, LifecycleRegistryOwner {

    enum class Type {
        POPULAR,
        NEW
    }

    companion object {
        private val ARG_TYPE = "type"

        fun newInstance(type: Type): GalleryFragment {
            return GalleryFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_TYPE, type)
                }
            }
        }
    }

    @Inject
    lateinit var presenter: GalleryContract.Presenter

    private lateinit var binding: FragmentGalleryBinding

    private lateinit var adapter: PhotosArrayAdapter

    private lateinit var loadingFooter: LoadingFooterView

    private lateinit var type: Type

    private val registry = LifecycleRegistry(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component.inject(this)

        type = arguments.getSerializable(ARG_TYPE) as Type

        presenter.setUp(this, this)

        lifecycle.addObserver(presenter)
    }

    override fun onCreateView(inflater: LayoutInflater?,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentGalleryBinding>(inflater, R.layout.fragment_gallery, container, false)

        initGridView()
        initSwipeRefresh()

        requestCatImages(1)

        return binding.root
    }

    private fun initSwipeRefresh() {
        binding.swipeRefresh.run {
            setColorSchemeResources(R.color.theme500)
            setOnRefreshListener { requestCatImages(1, true) }
        }
    }

    private fun initGridView() {
        loadingFooter = LoadingFooterView(activity)
        adapter = PhotosArrayAdapter(activity, ArrayList())

        binding.gridView.run {
            addFooterView(loadingFooter)
            adapter = this@GalleryFragment.adapter
            setOnItemClickListener { _, _, position, _ -> onItemClick(position) }
            setOnScrollListener(object : OnLoadMoreScrollListener(activity as AbsListView.OnScrollListener) {
                override fun onLoadMore(page: Int, totalItemsCount: Int) {
                    requestCatImages(page)
                }
            })
        }
    }

    internal fun onItemClick(position: Int) {
        presenter.onClickItem(adapter.getItem(position).id)
    }

    private fun requestCatImages(page: Int, isRefreshing: Boolean = false) {
        when (type) {
            Type.POPULAR -> presenter.requestGetPopular(page, isRefreshing)
            Type.NEW -> presenter.requestGetNew(page, isRefreshing)
        }
    }

    override fun showProgress(page: Int, isRefreshing: Boolean) {
        if (page == 1) {
            if (isRefreshing) {
                binding.loading.visibility = View.GONE
            } else {
                binding.loading.visibility = View.VISIBLE
            }
        } else {
            loadingFooter.show()
        }
    }

    override fun hideProgress(page: Int) {
        if (page == 1) {
            binding.loading.visibility = View.GONE
        } else {
            loadingFooter.hide()
        }
    }

    override fun showCatImages(presentationModels: List<GalleryItemPresentationModel>, page: Int) {
        if (page == 1) adapter.clear()
        adapter.addAll(presentationModels)
        adapter.notifyDataSetChanged()
        hideProgress(page)
        binding.swipeRefresh.isRefreshing = false
        binding.swipeRefresh.visibility = View.VISIBLE
    }

    override fun getLifecycle(): LifecycleRegistry = registry

    override fun showDetail(id: CatImageId) {
        startActivity(PhotoDetailActivity.createIntent(activity, id))
    }

    internal class ViewHolder(view: View) {
        val binding: ViewPhotoBinding = DataBindingUtil.bind(view)
    }

    private class PhotosArrayAdapter(context: Context, presentationModels: List<GalleryItemPresentationModel>)
        : ArrayAdapter<GalleryItemPresentationModel>(context, R.layout.view_photo, presentationModels) {

        override fun getView(pos: Int, convertView: View?, parent: ViewGroup): View {
            val view: View
            val holder: ViewHolder

            if (convertView?.tag != null) {
                view = convertView
                holder = view.tag as ViewHolder
            } else {
                view = LayoutInflater.from(context).inflate(R.layout.view_photo, parent, false)
                holder = ViewHolder(view)
            }

            view.tag = holder

            bindData(holder, getItem(pos))
            holder.binding.ripple.setOnClickListener { (parent as GridView).performItemClick(view, pos, 0L) }

            return view
        }

        private fun bindData(holder: ViewHolder, presentationModel: GalleryItemPresentationModel) {
            if (holder.binding.imgPreview.tag == null || holder.binding.imgPreview.tag != presentationModel.imageUrl) {
                Picasso.with(context)
                        .load(presentationModel.imageUrl)
                        .placeholder(R.color.theme50)
                        .into(holder.binding.imgPreview)
                holder.binding.imgPreview.tag = presentationModel.imageUrl
            }
        }
    }

}