package com.alikazi.codetest.weatherzone.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alikazi.codetest.weatherzone.R
import com.alikazi.codetest.weatherzone.models.Photo
import com.alikazi.codetest.weatherzone.utils.ViewUtils

class PhotosAdapter(context: Context?) : ListAdapter<Photo, PhotosAdapter.PhotoItemViewHolder>(ITEM_COMPARATOR) {

	companion object {
		val ITEM_COMPARATOR = object: DiffUtil.ItemCallback<Photo>() {
			override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
				oldItem.hashCode() == newItem.hashCode()

			override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
				oldItem.id == newItem.id
		}
	}

	private val inflater = LayoutInflater.from(context)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemViewHolder {
		val view = inflater.inflate(R.layout.item_photo, parent, false)
		return PhotoItemViewHolder(view)
	}

	override fun onBindViewHolder(holder: PhotoItemViewHolder, position: Int) {
		val photo = getItem(position)
		ViewUtils.loadImageWithGlide(holder.itemView.context, photo.src.medium, photo.src.tiny,
			holder.photoImageView, holder.photoProgressBar)
	}

	class PhotoItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val photoImageView: ImageView = view.findViewById(R.id.itemPhotoImageView)
		val photoProgressBar: ProgressBar = view.findViewById(R.id.itemPhotoProgressBar)
	}

}