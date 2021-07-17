package study.singlelife.utils

import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

// Firebase 에서 uid값 받아오는 함수를 정의하고 필요한 곳에서 함수를 호출하여 사용한다.
class FBAuth {

    companion object {
        private lateinit var auth: FirebaseAuth

        fun getUid() : String {

            auth = FirebaseAuth.getInstance()

            return auth.currentUser?.uid.toString()
        }

        fun getTime() : String {

            val currentDataTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA).format(currentDataTime)

            return dateFormat
        }
    }
}