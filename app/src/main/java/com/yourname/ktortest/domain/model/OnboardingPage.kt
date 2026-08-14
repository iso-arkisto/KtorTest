package com.yourname.ktortest.domain.model

import androidx.annotation.DrawableRes
import com.yourname.ktortest.R

sealed class OnboardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First: OnboardingPage(
        image = R.drawable.explore_image,
        title = "Explore",
        description = "Dive into detailed profiles of programming languages. Learn their history and info in just one tap."
    )

    object Second: OnboardingPage(
        image = R.drawable.create_image,
        title = "Create",
        description = "Developing your own language or want to add a rare dialect? Easily contribute new objects to the database, fill in the details, and share your knowledge."
    )

    object Third: OnboardingPage(
        image = R.drawable.find_image,
        title = "Find",
        description = "Use the smart search to find language by name, or filter them by inception year. The right tool is always at your fingertips."
    )
}