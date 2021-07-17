package study.singlelife.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import study.singlelife.R
import study.singlelife.databinding.ActivityBoardEditBinding
import study.singlelife.utils.FBAuth
import study.singlelife.utils.FBRef

class BoardEditActivity : AppCompatActivity() {

    private val TAG = BoardEditActivity::class.java.simpleName

    private lateinit var key : String

    private lateinit var binding : ActivityBoardEditBinding

    private lateinit var writerUid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_edit)

        key = intent.getStringExtra("key").toString()
        getBoardData(key)
        getImageData(key)

        binding.editBtn.setOnClickListener {

            editBoardData(key)

        }
    }

    private fun editBoardData(key : String){
        FBRef.boardRef
            .child(key)
            .setValue(BoardModel(binding.titleArea.text.toString(),
                binding.contentArea.text.toString(), writerUid, FBAuth.getTime())
            )
        Toast.makeText(this, "수정완료", Toast.LENGTH_SHORT).show()

        finish()
    }

    private fun getImageData(key: String) {
        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child("$key.png")

        // ImageView in your Activity
        val imageView = binding.imageArea

        storageReference.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Glide.with(this)
                    .load(task.result)
                    .into(imageView)
            } else {

            }
        }
    }

    private fun getBoardData(key : String){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d(TAG, dataSnapshot.toString())

                val dataModel = dataSnapshot.getValue(BoardModel::class.java)


                Log.d(TAG, dataModel?.title.toString())

                binding.titleArea.setText(dataModel?.title.toString())
                binding.contentArea.setText(dataModel?.content.toString())
                writerUid = dataModel?.uid.toString()

            }

            override fun onCancelled(databaseError: DatabaseError) {

                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }
}