import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object ArticleRepository {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val articleAdapter = moshi.adapter(ArticlesResponse::class.java)

    suspend fun fetchArticleTitles(): List<String> = withContext(Dispatchers.IO) {
        val titlesList = mutableListOf<String>()
        try {
            val url = URL("https://newsapi.org/v2/top-headlines?country=us&apiKey=YourAPIKey&category=sports")
            (url.openConnection() as? HttpURLConnection)?.run {
                requestMethod = "GET"
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = inputStream.bufferedReader().use { it.readText() }
                    val articlesResponse = articleAdapter.fromJson(response)
                    articlesResponse?.articles?.forEach { article ->
                        titlesList.add(article.title)
                    }
                }
                disconnect()
            }
        } catch (e: Exception) {
            Log.e("ArticleRepository", "Error fetching articles", e)
        }
        titlesList
    }
}