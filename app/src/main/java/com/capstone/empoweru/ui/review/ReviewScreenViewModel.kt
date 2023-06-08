package com.capstone.empoweru.ui.review

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.capstone.empoweru.data.dummy.Rating
import com.capstone.empoweru.data.remote.ApiConfig.apiService
import com.capstone.empoweru.data.repository.CommentRepository
import com.capstone.empoweru.data.request.CommentRequest
import com.capstone.empoweru.data.response.Location
import com.capstone.empoweru.utils.CommentEvent
import com.capstone.empoweru.utils.CommentEventBus
import com.capstone.empoweru.utils.UserPreferences
import kotlinx.coroutines.launch

class ReviewScreenViewModel(
    private val context: Context,
    private val commentRepository: CommentRepository
    ) : ViewModel() {

    fun addComment(location: Location, rating: Rating?, comment: String, callback: (Boolean) -> Unit) {
        val userPreferences = UserPreferences.getInstance(context)
        val userID = userPreferences.id

        if (location.GMapsID.isNullOrEmpty() || userID.isNullOrEmpty() || rating == null || comment.isEmpty()) {
            // Handle the error case where some fields are missing
            Toast.makeText(context, "Silahkan isi semua data terlebih dahulu", Toast.LENGTH_SHORT).show()
            callback(false)
            return
        }

        viewModelScope.launch {
            try {
                val request = CommentRequest(location.GMapsID, userID, rating.value, comment)
                val response = commentRepository.createComment(request)

                CommentEventBus.postEvent(CommentEvent.CommentAdded)

                Toast.makeText(context, "Komentarmu berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                callback(true)

            } catch (e: Exception) {

                Toast.makeText(context, "Terjadi kesalahan saat mengunggah Komentar", Toast.LENGTH_SHORT).show()
                callback(false)
            }
        }
    }
}

class ReviewScreenViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewScreenViewModel::class.java)) {
            val commentRepository = CommentRepository(apiService)
            return ReviewScreenViewModel(context, commentRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

