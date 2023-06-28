package iy.panneerdas.batterylevelnotification.platform

import android.content.res.Resources
import javax.inject.Inject

interface I18nStringProvider {
    fun getString(int: Int): String
}

class I18nStringProviderImpl @Inject constructor(
    private val resources: Resources
) : I18nStringProvider {

    override fun getString(int: Int): String {
        return resources.getString(int)
    }
}