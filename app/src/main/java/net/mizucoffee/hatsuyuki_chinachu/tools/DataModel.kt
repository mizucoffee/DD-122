package net.mizucoffee.hatsuyuki_chinachu.tools

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import net.mizucoffee.hatsuyuki_chinachu.model.ProgramItem
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnectionWithIndex

class DataModel private constructor(private val mSharedPreferences: SharedPreferences){

    private val getServerConnectionWithIndexSubject = PublishSubject.create<ServerConnectionWithIndex>()
    @JvmField val getServerConnectionWithIndex = getServerConnectionWithIndexSubject as Observable<ServerConnectionWithIndex>

    private val getAllServerConnectionsSubject = PublishSubject.create<ArrayList<ServerConnection>>()
    @JvmField val getAllServerConnection = getAllServerConnectionsSubject as Observable<ArrayList<ServerConnection>>

    private val getDownloadedListSubject = PublishSubject.create<ArrayList<ProgramItem>>()
    @JvmField val getDownloadedList = getDownloadedListSubject as Observable<ArrayList<ProgramItem>>

    private val getCurrentServerConnectionSubject = PublishSubject.create<ServerConnection>()

    @JvmField
    val getCurrentServerConnection = getCurrentServerConnectionSubject as Observable<ServerConnection?>

    fun addServerConnection(serverConnection: ServerConnection) {
        serverConnection.setId(System.currentTimeMillis())
        val sc = allServerConnections
        sc.add(serverConnection)
        saveServerConnections(sc)
    }

    fun setServerConnection(serverConnection: ServerConnection, index: Int) {
        val sc = allServerConnections
        sc[index] = serverConnection
        saveServerConnections(sc)
    }

    fun getServerConnectionWithIndex(index: Int) {
        val sc = allServerConnections
        if (sc.size < index) getServerConnectionWithIndexSubject.onError(ArrayIndexOutOfBoundsException())
        getServerConnectionWithIndexSubject.onNext(ServerConnectionWithIndex(sc[index],index))
    }

    fun removeServerConnectionWithIndex(index: Int) {
        val sc = allServerConnections
        sc.removeAt(index)
        saveServerConnections(sc)
    }

    fun getCurrentServerConnection(){
        val sc = allServerConnections
        var f = false;
        sc.filter { it.id == mSharedPreferences.getLong("CurrentServerConnection", 0) }
                .forEach {
                    getCurrentServerConnectionSubject.onNext(it)
                    f = true
                }
//        if(!f)getCurrentServerConnectionSubject.onNext(null)
//        if(!f) getCurrentServerConnectionSubject.onError(NullPointerException())
    }

    fun setCurrentServerConnection(sc: ServerConnection){
        mSharedPreferences.edit().putLong("CurrentServerConnection", sc.getId()).apply()
    }

    fun getAllServerConnection() {
        getAllServerConnectionsSubject.onNext(allServerConnections)
    }

    private fun saveServerConnections(sc: ArrayList<ServerConnection>) {
        mSharedPreferences.edit().putString("ServerConnections", Gson().toJson(sc)).apply()
    }

    private val allServerConnections: ArrayList<ServerConnection>
        get() {
            val s = mSharedPreferences.getString("ServerConnections", "")
            if (s == "") return ArrayList()
            return Gson().fromJson<ArrayList<ServerConnection>>(s, object : TypeToken<ArrayList<ServerConnection>>() {}.type)
        }

    fun addDownloadedList(program: ProgramItem) {
        val dl = getAllDownloadedList
        dl.add(program)
        setDownloadedList(dl)
    }

    private fun setDownloadedList(programs: List<ProgramItem>) {
        mSharedPreferences.edit().putString("DownloadedList", Gson().toJson(programs)).apply()
    }

    fun getDownloadedList(){
        getDownloadedListSubject.onNext(getAllDownloadedList)
    }

    private val getAllDownloadedList: ArrayList<ProgramItem>
    get() {val s = mSharedPreferences.getString("DownloadedList", "")
        var list: ArrayList<ProgramItem>? = Gson().fromJson<ArrayList<ProgramItem>>(s, object : TypeToken<ArrayList<ProgramItem>>() {}.type)
        if (list == null) list = ArrayList<ProgramItem>()
        return list;
    }

    companion object {
        private lateinit var i: DataModel

        fun init(s: SharedPreferences) {
            i = DataModel(s);
        }

        val instance: DataModel
            get() = i
    }
}