package study.singlelife.utils

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class FBRef {

    companion object {
        private val database = Firebase.database

        val bookmarkRef = database.getReference("bookmark_list")
    }
}