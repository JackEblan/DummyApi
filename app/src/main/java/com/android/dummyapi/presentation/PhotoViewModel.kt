package com.android.dummyapi.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.android.dummyapi.data.local.PhotoEntity
import com.android.dummyapi.data.mappers.toPhotoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(pager: Pager<Int, PhotoEntity>) : ViewModel() {
    val flow =
        pager.flow.map { pagingData -> pagingData.map { it.toPhotoItem() } }.cachedIn(viewModelScope)
}