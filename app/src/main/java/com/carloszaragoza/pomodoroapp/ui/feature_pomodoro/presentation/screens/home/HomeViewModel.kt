package com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.presentation.screens.home

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.domain.model.Pomodoro
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.domain.use_cases.PomodoroUseCases
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.presentation.screens.home.util.HomeEvent
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.presentation.screens.home.util.HomeState
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.presentation.screens.home.util.State
import com.carloszaragoza.pomodoroapp.ui.feature_pomodoro.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: PomodoroUseCases
):ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init{
        getPomodoros()
    }
    private fun getPomodoros() {
        _state.value.isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            delay(200)
            useCase.getPomodoro.invoke().let { pomodoro ->
                if (pomodoro != null) {
                    _state.value.pomodoro.value = pomodoro
                } else {
                    _state.value.pomodoro.value = Pomodoro(
                        id = 1,
                        color = Color(0xfffe706f).toArgb(),
                        time = 15,
                        longBreak = 15,
                        shortBreak = 5,)
                    useCase.insertPomodoro(_state.value.pomodoro.value!!)
                }
                updateStatePomodoro()
            }
        }.invokeOnCompletion { _state.value.isLoading.value = false }
    }
    fun onEvent(event: HomeEvent){
        when(event){
            is HomeEvent.ChangeOption -> {
                _state.value.currentOption.value = event.option
            }
            is HomeEvent.OpenOptions -> {
                _state.value.isOpen.value = !_state.value.isOpen.value
                _state.value.currentState.value =  State.RUNNING_POMODORO.name
            }
            is HomeEvent.PlusMinutePomodoro ->{
              if(_state.value.optionsPomodoroMinutes.value!!<60)
                  _state.value.optionsPomodoroMinutes.value = _state.value.optionsPomodoroMinutes.value?.plus(
                      1
                  )
            }
            is HomeEvent.MinusMinutePomodoro -> {
                if(_state.value.optionsPomodoroMinutes.value!!>0)
                _state.value.optionsPomodoroMinutes.value = _state.value.optionsPomodoroMinutes.value?.minus(
                    1
                )
            }
            is HomeEvent.PlusMinuteShortBreak ->{
                if(_state.value.optionsShortMinutes.value!! in 1..9)
                    _state.value.optionsShortMinutes.value = _state.value.optionsShortMinutes.value?.plus(
                        1
                    )
            }
            is HomeEvent.MinusMinuteShortBreak -> {
                if(_state.value.optionsShortMinutes.value!! in 2..10)
                    _state.value.optionsShortMinutes.value = _state.value.optionsShortMinutes.value?.minus(
                        1
                    )
            }
            is HomeEvent.PlusMinuteLongBreak ->{
                if(_state.value.optionsLongMinutes.value!! in 10..59)
                    _state.value.optionsLongMinutes.value = _state.value.optionsLongMinutes.value?.plus(
                        1
                    )
            }
            is HomeEvent.MinusMinuteLongBreak -> {
                if(_state.value.optionsLongMinutes.value!! in 11..60)
                    _state.value.optionsLongMinutes.value = _state.value.optionsLongMinutes.value?.minus(
                        1)
            }
            is HomeEvent.ChangeColor -> {
                _state.value.colorSelected.value = event.color
            }
            is HomeEvent.OnChange ->{
                insertPomodoro()
            }
            is HomeEvent.InitPomodoro-> {
                when(_state.value.currentState.value){
                     State.INITIAL_STATE.name -> {
                         _state.value.currentState.value= State.PAUSE_POMODORO.name
                     }
                    State.PAUSE_POMODORO.name -> {
                        _state.value.currentState.value =  State.RUNNING_POMODORO.name
                    }
                    State.RUNNING_POMODORO.name -> {
                        _state.value.currentState.value =  State.PAUSE_POMODORO.name
                    }
                    State.PAUSE_LONG_BREAK.name  -> {
                        sendUiEvent(UiEvent.ShowSnackBar("First You need to complete the break"))
                    }
                    State.RUNNING_LONG_BREAK.name -> {
                        sendUiEvent(UiEvent.ShowSnackBar("First You need to complete the break"))
                    }
                    State.PAUSE_SHORT_BREAK.name  -> {
                        sendUiEvent(UiEvent.ShowSnackBar("First You need to complete the break"))
                    }
                    State.RUNNING_SHORT_BREAK.name -> {
                        sendUiEvent(UiEvent.ShowSnackBar("First You need to complete the break"))
                    }
                }
                initPomodoro()
            }
            is HomeEvent.InitShortBreak-> {
                when(_state.value.currentState.value){
                    State.RUNNING_POMODORO.name -> {
                        if(_state.value.currentMin.value == 0 && _state.value.currentSec.value == 0)
                            _state.value.currentState.value= State.PAUSE_SHORT_BREAK.name
                        else
                            sendUiEvent(UiEvent.ShowSnackBar(
                                "You already have one pomodoro initiated!!!"))
                    }
                    State.PAUSE_POMODORO.name -> {
                        sendUiEvent(UiEvent.ShowSnackBar(
                            "You already have one pomodoro initiated!!!"))
                    }
                    State.PAUSE_SHORT_BREAK.name -> {
                        _state.value.currentState.value =  State.RUNNING_SHORT_BREAK.name
                    }
                    State.RUNNING_SHORT_BREAK.name -> {
                        _state.value.currentState.value =  State.PAUSE_SHORT_BREAK.name
                    }
                    State.INITIAL_STATE.name -> {
                        sendUiEvent(UiEvent.ShowSnackBar(
                                            message="First you need to complete a pomodoro"))
                    }
                }
                initShortBreak()
            }
            is HomeEvent.InitLongBreak-> {
                when(_state.value.currentState.value){
                    State.RUNNING_POMODORO.name -> {
                        if(_state.value.currentMin.value == 0 && _state.value.currentSec.value == 0)
                            _state.value.currentState.value= State.PAUSE_LONG_BREAK.name
                        else
                            sendUiEvent(UiEvent.ShowSnackBar(
                                "You already have one pomodoro initiated!!!"))
                    }
                    State.PAUSE_POMODORO.name -> {
                            sendUiEvent(UiEvent.ShowSnackBar(
                                "You already have one pomodoro initiated!!!"))
                    }
                    State.PAUSE_LONG_BREAK.name -> {
                        _state.value.currentState.value =  State.RUNNING_LONG_BREAK.name
                    }
                    State.RUNNING_LONG_BREAK.name -> {
                        _state.value.currentState.value =  State.PAUSE_LONG_BREAK.name
                    }
                    State.INITIAL_STATE.name -> {
                        sendUiEvent(UiEvent.ShowSnackBar(
                            message="First you need to complete a pomodoro"))
                    }
                }
                initLongBreak()
            }
        }
    }

    private fun initShortBreak(){
        viewModelScope.launch {
            if(_state.value.currentMin.value == 0 && _state.value.currentSec.value ==0){
                when(_state.value.currentState.value) {
                    State.PAUSE_SHORT_BREAK.name -> {
                        while (_state.value.currentShortMin.value!! >= 0) {
                            delay(1000)
                            if (_state.value.isOpen.value) break
                            if(_state.value.currentState.value == State.RUNNING_SHORT_BREAK.name)
                                break
                            if (_state.value.currentShortSec.value == 0) {
                                if (_state.value.currentShortMin.value == 0) {
                                    break
                                }
                                _state.value.currentShortMin.value =
                                    _state.value.currentShortMin.value!!.minus(1)
                                _state.value.currentShortSec.value = 60
                            }
                            _state.value.currentWidthShortBreak.value += calculateIncrement(
                                (_state.value.pomodoro.value?.shortBreak!! * 60).toDouble()
                            )
                            _state.value.currentShortSec.value -= 1
                        }
                    }
                }
                if(_state.value.currentShortSec.value == 0 &&
                    _state.value.currentShortMin.value ==0){
                    sendUiEvent(UiEvent.ShowSnackBar("Short Break completed!!!"))
                    _state.value.currentState.value = State.INITIAL_STATE.name
                }
            }
        }
    }
    private fun initLongBreak(){
        viewModelScope.launch {
            if(_state.value.currentMin.value == 0 && _state.value.currentSec.value ==0){
                when(_state.value.currentState.value) {
                    State.PAUSE_LONG_BREAK.name -> {
                        while(_state.value.currentLongMin.value!! >=0){
                            delay(1000)
                            if(_state.value.isOpen.value) break
                            if(_state.value.currentState.value == State.RUNNING_LONG_BREAK.name)
                                break
                            if(_state.value.currentLongSec.value ==0){
                                if(_state.value.currentLongMin.value ==0)break
                                _state.value.currentLongMin.value =
                                    _state.value.currentLongMin.value!!.minus(1)

                                _state.value.currentLongSec.value = 60
                            }
                            _state.value.currentWidthLongBreak.value += calculateIncrement(
                                (_state.value.pomodoro.value?.longBreak!! * 60).toDouble())
                            _state.value.currentLongSec.value -=1
                        }
                    }
                }
                if(_state.value.currentLongSec.value == 0 &&
                        _state.value.currentLongMin.value ==0){
                    sendUiEvent(UiEvent.ShowSnackBar("Long Break completed!!!"))
                    _state.value.currentState.value = State.INITIAL_STATE.name
                }
            }
        }
    }
    private fun initPomodoro() {
        viewModelScope.launch {
            if (_state.value.currentState.value == State.PAUSE_POMODORO.name) {
                if(state.value.currentMin.value == 0 && _state.value.currentSec.value == 0){
                    updateStatePomodoro()
                }
                while (_state.value.currentMin.value!! >= 0) {
                    delay(1000)
                    if (_state.value.currentState.value == State.RUNNING_POMODORO.name)
                        break
                    if (_state.value.currentSec.value == 0) {
                        if (_state.value.currentMin.value == 0) {
                            break
                        }
                        _state.value.currentMin.value = _state.value.currentMin.value!!.minus(1)
                        _state.value.currentSec.value = 60
                    }
                    _state.value.currentWidthPomodoro.value += calculateIncrement(
                        (_state.value.pomodoro.value?.time!! * 60).toDouble()
                    )
                    _state.value.currentSec.value -= 1
                }
                if (_state.value.currentMin.value == 0 && _state.value.currentSec.value == 0) {
                    sendUiEvent(UiEvent.ShowSnackBar("Pomodoro completed!!!"))
                    _state.value.currentState.value = State.RUNNING_POMODORO.name
                }
            }
        }
    }
    private fun insertPomodoro(){
        viewModelScope.launch (Dispatchers.IO){
            useCase.insertPomodoro.invoke(
                pomodoro = Pomodoro(
                    id =_state.value.pomodoro.value?.id,
                    time = _state.value.optionsPomodoroMinutes.value!!,
                    shortBreak = _state.value.optionsShortMinutes.value!!,
                    longBreak = _state.value.optionsLongMinutes.value!!,
                    color = _state.value.colorSelected.value!!))

            useCase.getPomodoro.invoke().let { pomodoro ->
                _state.value.pomodoro.value = pomodoro
            }
            resetStatePomodoro()
            updateStatePomodoro()
            _state.value.isOpen.value = !_state.value.isOpen.value

        }
    }
    private fun calculateIncrement(time:Double):Float{
            return (360/time).toFloat()
    }
    private fun resetStatePomodoro(){
        _state.value.currentSec.value = 0
        _state.value.currentLongSec.value = 0
        _state.value.currentShortSec.value = 0
    }
    private fun updateStatePomodoro(){
        _state.value.currentMin.value = _state.value.pomodoro.value!!.time
        _state.value.currentLongMin.value = _state.value.pomodoro.value!!.longBreak
        _state.value.currentShortMin.value = _state.value.pomodoro.value!!.shortBreak
        _state.value.optionsPomodoroMinutes.value = _state.value.pomodoro.value!!.time
        _state.value.optionsLongMinutes.value = _state.value.pomodoro.value!!.longBreak
        _state.value.optionsShortMinutes.value = _state.value.pomodoro.value!!.shortBreak
        _state.value.colorSelected.value = _state.value.pomodoro.value!!.color
        _state.value.currentWidthPomodoro.value = 0f
        _state.value.currentWidthLongBreak.value = 0f
        _state.value.currentWidthShortBreak.value = 0f
    }
    private fun sendUiEvent(event: UiEvent)= viewModelScope.launch(){
        _uiEvent.send(event)
    }
}