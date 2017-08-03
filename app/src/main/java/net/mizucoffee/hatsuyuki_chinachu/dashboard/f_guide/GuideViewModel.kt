package net.mizucoffee.hatsuyuki_chinachu.dashboard.f_guide

import android.databinding.BindingAdapter
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import net.mizucoffee.hatsuyuki_chinachu.model.ScheduleModel
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection
import net.mizucoffee.hatsuyuki_chinachu.tools.ChinachuModel
import net.mizucoffee.hatsuyuki_chinachu.tools.DataModel
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki
import java.util.*

class GuideViewModel internal constructor(val fragment: GuideFragment) { // クラス変数はアカン...

    val adapter = ObservableField<FragmentPagerAdapter>()
    val list = ObservableField<GuideRecyclerAdapter>()
    val column = ObservableInt()

    //    private GuideRecyclerAdapter mAdapter;

    private val mChannelList = ArrayList<ScheduleModel>()

    lateinit var currentSc: ServerConnection

    init {
        subscribe()
        DataModel.instance.getCurrentServerConnection()
        Shirayuki.log("START")
    }

    private fun subscribe() {
        DataModel.instance.getCurrentServerConnection.subscribe(object : Observer<ServerConnection> {
            override fun onSubscribe(d: Disposable) {}
            override fun onNext(serverConnection: ServerConnection) {
                currentSc = serverConnection
                Shirayuki.log("GET SERVER CON")
                ChinachuModel.getAllPrograms(currentSc.address)
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
            Shirayuki.log("CHANNEL ADD")
        },{
            it.printStackTrace()
        },{
//            mProgramLists.values.forEach { it.forEach {  } }
//            mProgramLists.forEach { s, arrayList ->  }
//            adapter.set()//mProgramListsを元にFragmentを生成
            Shirayuki.log("Comp")
            adapter.set(ChannelVPageAdapter(fragment.fragmentManager,mChannelList))
        })
    }

    //    private void setAdapterToRecyclerView(List<ProgramItem> programList){
    //        mAdapter.setRecorded(programList);
    //
    //        mAdapter.notifyDataSetChanged();
    //        list.set(mAdapter);
    //    }

    fun reload() {
        DataModel.instance.getCurrentServerConnection()
    }



    companion object {

        //=================================
        // DataBinding Custom Listener
        //=================================

        @JvmStatic
        @BindingAdapter("adapter")
        fun setViewPagerAdapter(vp: ViewPager, a: ChannelVPageAdapter) {
            Shirayuki.log("SET ADAPTER")
            vp.adapter = a
        }

    }
}
