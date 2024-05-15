package net.zetetic.sqlcipher_android_room_demo

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.room.Room.databaseBuilder
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

class SQLCipherTask(private val context: Context) : AsyncTask<Unit, Unit, Unit>() {

    companion object {
        val TAG = SQLCipherTask::class.simpleName
    }

    override fun doInBackground(vararg p0: Unit) {
        System.loadLibrary("sqlcipher")
        val db = databaseBuilder(
            context,
            AppDatabase::class.java, "todo.db")
            .fallbackToDestructiveMigration()
            .setAutoCloseTimeout(60, TimeUnit.SECONDS)
            .openHelperFactory(
                SupportOpenHelperFactory(
                    "Password1!".toByteArray(StandardCharsets.UTF_8))
            ).build()
        val dao = db.todoDao()
        dao?.insert(Todo("Testing SQLCipher for Android"))
        val todos = dao?.all
        for (todo: Todo in todos!!.iterator()) {
            Log.i(TAG, "Todo item: ${todo.task}")
        }
        dao.deleteAll()
    }
}