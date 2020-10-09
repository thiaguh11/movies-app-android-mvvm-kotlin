package com.example.moviesapp.ui.adapters.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviesapp.R
import com.example.moviesapp.data.model.Genre

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let { imageUrl ->
        val url = "https://image.tmdb.org/t/p/w500$imageUrl"
        Glide.with(imgView.context)
            .load(url)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("listToString")
fun listToString(textView: TextView, list: List<Genre>?) {
    list?.let {
        textView.text = list.joinToString(",")
    }
}