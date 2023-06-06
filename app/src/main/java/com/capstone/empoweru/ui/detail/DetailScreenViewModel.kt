package com.capstone.empoweru.ui.detail

import androidx.lifecycle.*
import com.capstone.empoweru.data.dummy.Comment
import com.capstone.empoweru.data.repository.ListCommentRepository
import com.capstone.empoweru.data.response.CommentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailScreenViewModel(
    private val listCommentRepository: ListCommentRepository,
    private val gmapsId: String
) : ViewModel() {

    private val _comments = MutableStateFlow<List<CommentList>>(emptyList())
    val comments: StateFlow<List<CommentList>> = _comments

    init {
        fetchComments()
    }

    private fun fetchComments() {
        viewModelScope.launch {
            try {
                val comments = listCommentRepository.getListOfComments(gmapsId)
                _comments.value = comments
            } catch (e: Exception) {
                // Handle error case
            }
        }
    }
}

class DetailScreenViewModelFactory(
    private val listCommentRepository: ListCommentRepository,
    private val gmapsId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailScreenViewModel(listCommentRepository, gmapsId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



