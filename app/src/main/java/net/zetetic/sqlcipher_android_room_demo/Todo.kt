package net.zetetic.sqlcipher_android_room_demo

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity
public class Todo {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "task")
    var task: String = ""

    constructor(task: String) {
        this.task = task
    }
}

@Dao
interface TodoDao {
    @get:Query("SELECT * FROM todo")
    val all: List<Todo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todo: Todo): Long

    @Delete
    fun delete(todo: Todo)

    @Query("DELETE FROM todo;")
    fun deleteAll()
}

@Database(entities = [Todo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao?
}