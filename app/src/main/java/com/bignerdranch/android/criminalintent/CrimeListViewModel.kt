package com.bignerdranch.android.criminalintent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CrimeListViewModel : ViewModel() {

    val crimes = mutableListOf<Crime>()

    init {
        fetchArticles()
    }

    private fun fetchArticles() {
        viewModelScope.launch {
            try {
                val titles = ArticleRepository.fetchArticleTitles()
                populateCrimesWithTitles(titles)
            } catch (e: Exception) {
                Log.e("CrimeListViewModel", "Failed to fetch articles", e)
            }
        }
    }

    private fun populateCrimesWithTitles(titles: List<String>) {
        titles.forEachIndexed { index, title ->
            val crime = Crime(
                id = UUID.randomUUID(),
                title = title,
                date = Date(),
                isSolved = index % 2 == 0,
                requiresPolice = index % 2 == 0
            )
            crimes.add(crime)
        }
        // Log or handle the populated crimes as needed
    }
}
