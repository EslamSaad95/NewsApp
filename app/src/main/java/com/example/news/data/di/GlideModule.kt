package com.example.news.data.di

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlideModule : AppGlideModule() {

    // Disable manifest parsing to avoid adding similar modules twice.
    override fun isManifestParsingEnabled() = false


}