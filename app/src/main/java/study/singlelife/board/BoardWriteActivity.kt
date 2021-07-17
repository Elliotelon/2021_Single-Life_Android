package study.singlelife.board

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import study.singlelife.R
import study.singlelife.contentslist.BookmarkModel
import study.singlelife.databinding.ActivityBoardWriteBinding
import study.singlelife.utils.FBAuth
import study.singlelife.utils.FBRef
import java.io.ByteArrayOutputStream

class BoardWriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardWriteBinding

    private val TAG = BoardWriteActivity::class.java.simpleName

    var isImageUpload = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_write)

        binding.writeBtn.setOnClickListener {

            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val uid = FBAuth.getUid()
            val time = FBAuth.getTime()

            Log.d(TAG, title)
            Log.d(TAG, content)

            // board
            //   - key
            //      - boardModel(title, content, uid, time)

            //파이어베이스 storage에 이미지를 저장하고 싶습니다.
            //만약에 내가 게시글을 클릭했을 때, 게시글에 대한 정보를 받아와야 하는데
            //이미지 이름에 대한 정보를 모르기 때문에
            //이미지 이름을 문서의 key값으로 해줘서 이미젱 대한 정보를 찾기 쉽게 해놓음.

            val key = FBRef.boardRef.push().key.toString()

            FBRef.boardRef
                .child(key)
                .setValue(BoardModel(title, content, uid, time))

            Toast.makeText(this, "게시글 입력완료", Toast.LENGTH_SHORT).show()
            if(isImageUpload){
                imageUpload(key)
            }


            finish()
        }

        binding.imageArea.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            isImageUpload = true
            startActivityForResult(gallery, 100)
        }

    }

    private fun imageUpload(key : String) {
        // Get the data from an ImageView as bytes
        val storage = Firebase.storage
        val storageRef = storage.reference
        val mountainsRef = storageRef.child(key+".png")

        val imageView = binding.imageArea
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {

        }.addOnSuccessListener { taskSnapshot ->

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == 100){
            binding.imageArea.setImageURI(data?.data)
        }
    }
}