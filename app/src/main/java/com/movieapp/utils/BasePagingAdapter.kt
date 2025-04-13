package com.movieapp.utils

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BasePagingAdapter<T : Any, VB : ViewBinding>(callback: DiffUtil.ItemCallback<T>) :
    PagingDataAdapter<T, BasePagingAdapter.BaseViewHolder<VB>>(callback) {

    abstract fun getLayoutInflater(parent: ViewGroup): VB

    var listener: ((view: View, item: T, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder<VB>(
        getLayoutInflater(parent)
    )

    open class BaseViewHolder<VB : ViewBinding>(val binding: VB) :
        RecyclerView.ViewHolder(binding.root)
}
