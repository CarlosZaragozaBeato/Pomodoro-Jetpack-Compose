package com.carloszaragoza.pomodoroapp.ui.di

import android.app.Application
import androidx.room.Room
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.data.data_source.PomodoroDataBase
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.data.repository.PomodoroRepositoryImp
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.domain.repository.PomodoroRepository
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.domain.use_cases.GetPomodoroUseCase
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.domain.use_cases.InsertPomodoroUseCase
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.domain.use_cases.PomodoroUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePomodoroDatabase(app:Application):PomodoroDataBase{
        return Room.databaseBuilder(
            app,
            PomodoroDataBase::class.java,
            PomodoroDataBase.database_name
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePomodoroRepository(db: PomodoroDataBase):PomodoroRepository{
        return PomodoroRepositoryImp(db.dao)
    }

    @Singleton
    @Provides
    fun providePomodoroUseCases(repository: PomodoroRepository):PomodoroUseCases{
        return PomodoroUseCases(
            insertPomodoro = InsertPomodoroUseCase(repository),
            getPomodoro = GetPomodoroUseCase(repository))
    }

}