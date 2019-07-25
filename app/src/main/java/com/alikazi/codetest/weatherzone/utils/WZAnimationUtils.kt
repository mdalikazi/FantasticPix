package com.alikazi.codetest.weatherzone.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import androidx.annotation.RequiresApi
import com.alikazi.codetest.weatherzone.R

class WZAnimationUtils {

	companion object {
		@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
		fun circularReveal(context: Context, viewToReveal: View, posFromRight: Int,
		                 containsOverflow: Boolean, isShow: Boolean) {
			var width = viewToReveal.width
			if (posFromRight > 0) {
				width -= posFromRight *
						context.resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) -
						context.resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) /
						2
			}

			if (containsOverflow) {
				width -= context.resources.getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material)
			}

			val cx = width
			val cy = viewToReveal.height / 2

			val anim: Animator = when(isShow) {
				true -> ViewAnimationUtils.createCircularReveal(viewToReveal, cx, cy, 0f, width.toFloat())
				else -> ViewAnimationUtils.createCircularReveal(viewToReveal, cx, cy, width.toFloat(), 0f)
			}

			anim.duration = 250

			// make the view invisible when the animation is finished
			anim.addListener(object : AnimatorListenerAdapter() {
				override fun onAnimationEnd(animation: Animator) {
					if (!isShow) {
						viewToReveal.visibility = View.INVISIBLE
					}
				}
			})

			// make the view visible and start the animation
			if (isShow) {
				viewToReveal.visibility = View.VISIBLE
			}

			anim.start()
		}

		fun valueAnimator(start: Int, end: Int, view: View): ValueAnimator {
			val valueAnimator = ValueAnimator.ofInt(start, end)
			valueAnimator.addUpdateListener {
				// Update Height
				val value = valueAnimator.animatedValue as Int
				val layoutParams = view.layoutParams
				layoutParams.height = value
				view.layoutParams = layoutParams
			}

			return valueAnimator
		}
	}

}