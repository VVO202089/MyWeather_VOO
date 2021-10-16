package com.example.myweather.room

import androidx.room.*


interface HistiryDAO {

    @Query("SELECT * FROM HistoryEntity")
    fun all():List<HistoryEntity>

    @Query("SELECT * HistoryEntity WHERE name LIKE :name")
    fun getDataByWord(name:String):List<HistoryEntity>

    @Query("DELETE FROM HistoryEntity WHERE id =:idForDelete")
    fun deleteQ(idForDelete : Long)

    //@Delete
    //fun delete(entity:HistoryEntity)

    //@Update
    //fun delete(entity:HistoryEntity)

    //@Insert(onConflict = OnConflictStrategy.IGNORE)
    //fun delete(entity:HistoryEntity)
}