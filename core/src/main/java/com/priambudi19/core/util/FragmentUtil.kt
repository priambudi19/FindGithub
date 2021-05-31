package com.priambudi19.core.util

import android.widget.Toast
import androidx.fragment.app.Fragment

object FragmentUtil {
    fun instanceFragment(className: String): Fragment {
        return Class.forName(className).newInstance() as Fragment
    }
}