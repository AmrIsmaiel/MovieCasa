package com.som3a.base

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.som3a.common.Constants

abstract class EndlessRecyclerOnScrollListener(
    private val linearLayoutManager: LinearLayoutManager,
) : RecyclerView.OnScrollListener() {
    private var mPreviousTotal = 0
    private var mLoading = true


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = recyclerView.childCount
        val totalItemCount = linearLayoutManager.itemCount
        val firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()
        if (mLoading) {
            if (totalItemCount > mPreviousTotal) {
                mLoading = false
                mPreviousTotal = totalItemCount
            }
        }
        if (!mLoading && (firstVisibleItem + visibleItemCount >= totalItemCount) && totalItemCount >= Constants.PAGE_Size) {
            // End has been reached
            onLoadMore()
            mLoading = true
        }
    }

    fun refresh() {
        mPreviousTotal = 0
        mLoading = true
    }

    abstract fun onLoadMore()
}