package study.singlelife.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import study.singlelife.R
import study.singlelife.databinding.ActivityBoardInsideBinding
import study.singlelife.utils.FBRef

class BoardInsideActivity : AppCompatActivity() {

    private val TAG = BoardInsideActivity::class.java.simpleName

    private lateinit var binding :ActivityBoardInsideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)

        //첫번째 방법
//        val title = intent.getStringExtra("title").toString()
//        val content = intent.getStringExtra("content").toString()
//        val time = intent.getStringExtra("time").toString()
//
//        binding.titleArea.text = title
//        binding.contentArea.text = content
//        binding.timeArea.text = time

        //두번째 방법

        val key = intent.getStringExtra("key").toString()
        //Toast.makeText(this, key, Toast.LENGTH_SHORT).show()
        getBoardData(key)
    }

    private fun getBoardData(key : String){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d(TAG, dataSnapshot.toString())

                val dataModel = dataSnapshot.getValue(BoardModel::class.java)

                Log.d(TAG, dataModel?.title.toString())

                binding.titleArea.text = dataModel?.title
                binding.contentArea.text = dataModel?.content
                binding.timeArea.text = dataModel?.time

            }

            override fun onCancelled(databaseError: DatabaseError) {

                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }
}