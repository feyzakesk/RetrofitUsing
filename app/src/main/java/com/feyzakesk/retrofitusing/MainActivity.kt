package com.feyzakesk.retrofitusing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.feyzakesk.retrofitusing.databinding.ActivityMainBinding
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofitService = RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)

        val responseLiveData: LiveData<Response<Albums>> =
            liveData {
                val response = retrofitService.getAlbums()
                emit(response)
            }
        responseLiveData.observe(this,Observer{
            val albumList = it.body()?.listIterator()
            if (albumList != null ){
                while (albumList.hasNext()){
                    val albumItem = albumList.next()

                    val albumTitle = "Album Title : ${albumItem.title}\n"
                    binding.titleTextView.append(albumTitle)
                }
            }
        })
    }
}