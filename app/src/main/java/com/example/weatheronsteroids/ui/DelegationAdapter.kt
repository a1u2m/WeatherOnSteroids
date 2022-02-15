package com.example.weatheronsteroids.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class DelegationAdapter<T>(
    protected val delegatesManager: AdapterDelegatesManager<T>,
    protected var items: List<T>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatesManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegatesManager.onBindViewHolder(items, position, holder)
    }

    override fun getItemViewType(position: Int): Int {
        return delegatesManager.getItemViewType(items, position)
    }

}