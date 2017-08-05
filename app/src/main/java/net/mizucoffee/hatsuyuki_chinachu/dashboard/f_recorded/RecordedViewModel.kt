package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_recorded

import android.databinding.BindingAdapter
import android.databinding.ObservableField
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import net.mizucoffee.hatsuyuki_chinachu.R
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardActivity
import net.mizucoffee.hatsuyuki_chinachu.enumerate.ListType
import net.mizucoffee.hatsuyuki_chinachu.enumerate.SortType
import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection
import net.mizucoffee.hatsuyuki_chinachu.tools.*
import java.util.*

class RecordedViewModel internal constructor(fragment: RecordedFragment) {

    val list = ObservableField<RecordedCardRecyclerAdapter>()
    val column = ObservableField<Int>()

    private var mListType = ListType.CARD_COLUMN1
    private var mSortType = SortType.DATE_DES
    private val mAdapter: RecordedCardRecyclerAdapter
    private lateinit var mProgramList: List<ProgramItem>

    private var currentSc: ServerConnection? = null

    init {
        subscribe()
        DataModel.instance.getCurrentServerConnection()
        mAdapter = RecordedCardRecyclerAdapter(fragment.activity as DashboardActivity, currentSc?.address)
        column.set(1) //TODO: デフォルト変更
    }

    private fun subscribe() {
        ChinachuModel.programItems.subscribe ({
            Shirayuki.log("success")
            mProgramList = it
            setAdapterToRecyclerView(mProgramList)
        })
        DataModel.instance.getCurrentServerConnection.subscribe ({
            currentSc = it
            ChinachuModel.getRecordedList(it.address)
        })
    }

    private fun setAdapterToRecyclerView(programList: List<ProgramItem>) {
        val programItems = sort(programList)
        mAdapter.setRecorded(programItems)
        mAdapter.listType = mListType

        mAdapter.notifyDataSetChanged()
        list.set(mAdapter)
        column.set(if (mAdapter.listType == ListType.CARD_COLUMN2) 2 else 1)
    }

    fun reload() {
        DataModel.instance.getCurrentServerConnection()
    }

    private fun sort(items: List<ProgramItem>): List<ProgramItem> {
        when (mSortType) {
            SortType.DATE_ASC -> Collections.sort(items, DateComparator())
            SortType.DATE_DES -> {
                Collections.sort(items, DateComparator())
                Collections.reverse(items)
            }
            SortType.TITLE_ASC -> Collections.sort(items, TitleComparator())
            SortType.TITLE_DES -> {
                Collections.sort(items, TitleComparator())
                Collections.reverse(items)
            }
            SortType.CATEGORY_ASC -> Collections.sort(items, CategoryComparator())
            SortType.CATEGORY_DES -> {
                Collections.sort(items, CategoryComparator())
                Collections.reverse(items)
            }
        }
        return items
    }

    var sortMenuListener:PopupMenu.OnMenuItemClickListener = PopupMenu.OnMenuItemClickListener { menu ->
        mSortType = SortType.DATE_DES
        when (menu.itemId) {
            R.id.dateasc -> mSortType = SortType.DATE_ASC
            R.id.datedes -> mSortType = SortType.DATE_DES
            R.id.titleasc -> mSortType = SortType.TITLE_ASC
            R.id.titledes -> mSortType = SortType.TITLE_DES
            R.id.catasc -> mSortType = SortType.CATEGORY_ASC
            R.id.catdes -> mSortType = SortType.CATEGORY_DES
        }
        setAdapterToRecyclerView(mProgramList)
        false
    }

    var listMenuListener: PopupMenu.OnMenuItemClickListener = PopupMenu.OnMenuItemClickListener { item ->
        mListType = if (item.itemId == R.id.column1) ListType.CARD_COLUMN1 else if (item.itemId == R.id.column2) ListType.CARD_COLUMN2 else ListType.LIST
        setAdapterToRecyclerView(mProgramList)
        false
    }

    var onSearch: SearchView.OnQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            return false
        }

        override fun onQueryTextChange(word: String): Boolean {
            val list = ArrayList<ProgramItem>()
            mProgramList.filter { it.title.contains(word) }.forEach { list.add(it) }
            setAdapterToRecyclerView(list)
            return false
        }
    }

    companion object {

        //=================================
        // DataBinding Custom Listener
        //=================================

        @BindingAdapter("adapterList")
        @JvmStatic
        fun setAdapterList(rv: RecyclerView, adapter: RecordedCardRecyclerAdapter?) {
            if(adapter != null) rv.adapter = adapter
        }

        @BindingAdapter("gridColumn")
        @JvmStatic
        fun setGridColumn(rv: RecyclerView, i: Int) {
            rv.layoutManager = GridLayoutManager(rv.context, i)
        }
    }
}