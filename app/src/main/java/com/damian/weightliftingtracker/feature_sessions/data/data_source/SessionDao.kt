package com.damian.weightliftingtracker.feature_sessions.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.damian.weightliftingtracker.feature_sessions.domain.model.Session
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Insert
    suspend fun insert(session: Session)

    @Update
    suspend fun update(session: Session)

    @Query("DELETE FROM _session WHERE id=:sessionId")
    suspend fun deleteById(sessionId: Int)

    @Query("SELECT * FROM _session WHERE planId=:planId")
    fun getSessions(planId: Int): Flow<List<Session>>

    @Query("SELECT * FROM _session WHERE id=:id")
    suspend fun getSingleSessionById(id: Int): Session?
}