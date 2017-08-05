package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_guide

import android.databinding.BindingAdapter
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentPagerAdapter
import android.widget.LinearLayout
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import net.mizucoffee.hatsuyuki_chinachu.model.ScheduleModel
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection
import net.mizucoffee.hatsuyuki_chinachu.tools.ChinachuModel
import net.mizucoffee.hatsuyuki_chinachu.tools.DataModel
import java.util.*

class ChannelViewModel internal constructor(fragment: GuideFragment) {

    val adapter = ObservableField<FragmentPagerAdapter>()
    val list = ObservableField<GuideRecyclerAdapter>()
    val column = ObservableInt()

    //    private GuideRecyclerAdapter mAdapter;

    private val mChannelList = ArrayList<ScheduleModel>()

    var currentSc: ServerConnection? = null

    init {
        subscribe()
//        adapter.set(FragmentPagerAdapter(fragment.fragmentManager!!))
        DataModel.instance.getCurrentServerConnection()
        if(currentSc != null)
        ChinachuModel.getAllPrograms(currentSc?.address)
    }

    private fun subscribe() {
        DataModel.instance.getCurrentServerConnection.subscribe(object : Observer<ServerConnection?> {
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(serverConnection: ServerConnection?) {
                //                ChinachuModel.INSTANCE.getAllPrograms(serverConnection.getAddress());
                currentSc = serverConnection
            }

            override fun onError(e: Throwable) {}

            override fun onComplete() {}
        })
//        ChinachuModel.programIds.subscribe { list ->
//            column.set(list.size)
//            for (id in list) {
//                mProgramLists.put(id, ArrayList<ProgramItem>())
//            }
//        }

        ChinachuModel.allPrograms.doOnError({ it.printStackTrace() }).subscribe ({
            mChannelList.add(it)
        },{
            it.printStackTrace()
        },{
//            mProgramLists.values.forEach { it.forEach {  } }
//            mProgramLists.forEach { s, arrayList ->  }
//            adapter.set()//mProgramListsを元にFragmentを生成

        })
    }

    //    private void setAdapterToRecyclerView(List<ProgramItem> programList){
    //        mAdapter.setRecorded(programList);
    //
    //        mAdapter.notifyDataSetChanged();
    //        list.set(mAdapter);
    //    }

    internal fun reload() {
        DataModel.instance.getCurrentServerConnection()
    }

    companion object {

        //=================================
        // DataBinding Custom Listener
        //=================================

        @BindingAdapter("recyclerColumn")
        fun setRecyclerColumn(ll: LinearLayout, num: Int) {
            //        for (int i = 0; i < num; i++) {
            //            RecyclerView r = new RecyclerView(ll.getContext());
            //            r.setLayoutManager(new GridLayoutManager(ll.getContext(), 1));
            //            r.setLayoutParams(new LinearLayoutCompat.LayoutParams(100, ViewGroup.LayoutParams.WRAP_CONTENT));
            //            r.setBackgroundColor(Color.BLUE);
            //            ll.addView(r);
            //            sRecyclerViews.add(sRecyclerViews.get(i));
            //            GuideRecyclerAdapter a = new GuideRecyclerAdapter(ll.getContext());
            //            sAdapters.add(a);
            //            r.setAdapter(sAdapters.get(i));
            //        }
        }

        @BindingAdapter("recyclerColumn")
        fun setRecyclerColumn(tl: TabLayout, tab: ArrayList<TabLayout.Tab>) {
            for (t in tab) {
                tl.addTab(t)
                //            tl.new
                //            TabLayout.Tab t = new TabLayout.Tab();
            }
        }
    }
}