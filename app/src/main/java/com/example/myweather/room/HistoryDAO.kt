package com.example.myweather.room

import android.database.Cursor
import androidx.room.*

@Dao
interface HistoryDAO {

    @Query("SELECT * FROM HistoryEntity")
    fun all():List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE name LIKE :name")
    fun getDataByWord(name :String): List<HistoryEntity>

    @Query("DELETE FROM HistoryEntity WHERE id=:id")
    fun deleteID(id: Long)

    @Delete
    fun delete(entity: HistoryEntity)

    @Query("DELETE FROM HistoryEntity")
    fun deleteAll()

    @Update
    fun update(entity: HistoryEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: HistoryEntity)

    @Query("SELECT `id`,`name`,`temp` FROM HistoryEntity")
    fun getHistoryCursor():Cursor

    @Query("SELECT `id`,`name`,`temp` FROM HistoryEntity WHERE id=:id")
    fun getHistoryCursor(id:Long):Cursor

}