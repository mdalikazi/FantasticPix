package com.alikazi.codetest.weatherzone.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

object ViewUtils {

	fun loadImageWithGlide(context: Context, url: String, thumbnailUrl: String?,
	                       target: ImageView, progressBar: ProgressBar?) {
		Glide.with(context)
			.load(url)
			.thumbnail(Glide.with(context).load(thumbnailUrl).centerCrop())
			.transition(DrawableTransitionOptions().crossFade())
			.centerCrop()
			.listener(object: RequestListener<Drawable> {
				override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?,
				                          isFirstResource: Boolean): Boolean {
					progressBar?.visibility = View.GONE
					return false
				}

				override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
				                             dataSource: DataSource?, isFirstResource: Boolean): Boolean {
					progressBar?.visibility = View.GONE
					return false
				}
			})
			.into(target)
	}
}