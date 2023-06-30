package com.example.radius.helpers

import androidx.fragment.app.Fragment
import com.example.radius.fragments.HomeFragment

class Constants {
    companion object {
        const val BASE_URL = "https://my-json-server.typicode.com/iranjith4/"
        const val HOME_FRAGMENT = "HOME_FRAGMENT"
        const val ONE = "1"
        const val TWO = "2"
        const val THREE = "3"
        const val FOUR = "4"
        const val SIX = "6"
        const val SEVEN = "7"
        const val TEN = "10"
        const val ELEVEN = "11"
        const val TWELVE = "12"
        fun getFragment(id: String): Class<Fragment> {
            return when (id) {
                HOME_FRAGMENT -> HomeFragment::class.java as Class<Fragment>
                else -> HomeFragment::class.java as Class<Fragment>
            }
        }
    }
}