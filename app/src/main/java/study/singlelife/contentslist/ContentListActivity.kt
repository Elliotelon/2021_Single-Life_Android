package study.singlelife.contentslist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import study.singlelife.R
import study.singlelife.utils.FBAuth
import study.singlelife.utils.FBRef

class ContentListActivity : AppCompatActivity() {

    lateinit var myRef : DatabaseReference

    val bookmarkIdList = mutableListOf<String>()

    lateinit var rvAdapter: ContentRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_list)

        val items = ArrayList<ContentModel>()
        val itemKeyList = ArrayList<String>()
        rvAdapter = ContentRVAdapter(baseContext, items, itemKeyList, bookmarkIdList)

        val database = Firebase.database

        val category = intent.getStringExtra("category")

        if(category == "category1"){
            myRef = database.getReference("contents")
        } else if (category == "category2"){
            myRef = database.getReference("contents2")
        }

        //firebase 데이터 가져오기
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for(dataModel in dataSnapshot.children){
                    Log.d("ContentListActivity", dataModel.toString())
                    Log.d("ContentListActivity", dataModel.key.toString())
                    val item = dataModel.getValue(ContentModel::class.java)

                    items.add(item!!)
                    itemKeyList.add(dataModel.key.toString())
                }
                rvAdapter.notifyDataSetChanged()
                Log.d("ContentListActivity", items.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {

                Log.w("ContentListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
      myRef.addValueEventListener(postListener)


        //firebase에 데이터 집어넣기
//        myRef2.push().setValue(
//            ContentModel("title5","https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FblYPPY%2Fbtq66v0S4wu%2FRmuhpkXUO4FOcrlOmVG4G1%2Fimg.png","https://philosopher-chan.tistory.com/1235?category=941578")
//        )
//
//        myRef2.push().setValue(
//            ContentModel("title6","https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FznKK4%2Fbtq665AUWem%2FRUawPn5Wwb4cQ8BetEwN40%2Fimg.png", "https://philosopher-chan.tistory.com/1236?category=941578")
//        )
//
//        myRef2.push().setValue(
//            ContentModel("title7","https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fbtig9C%2Fbtq65UGxyWI%2FPRBIGUKJ4rjMkI7KTGrxtK%2Fimg.png","https://philosopher-chan.tistory.com/1237?category=941578")
//        )
//
//        myRef2.push().setValue(
//            ContentModel("title8","https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcOYyBM%2Fbtq67Or43WW%2F17lZ3tKajnNwGPSCLtfnE1%2Fimg.png","https://philosopher-chan.tistory.com/1238?category=941578")
//        )

        val rv : RecyclerView = findViewById(R.id.rv)

//        items.add(ContentModel("title1","https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FblYPPY%2Fbtq66v0S4wu%2FRmuhpkXUO4FOcrlOmVG4G1%2Fimg.png","https://philosopher-chan.tistory.com/1235?category=941578"))
//        items.add(ContentModel("title2","https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FznKK4%2Fbtq665AUWem%2FRUawPn5Wwb4cQ8BetEwN40%2Fimg.png", "https://philosopher-chan.tistory.com/1236?category=941578"))
//        items.add(ContentModel("title3","https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fbtig9C%2Fbtq65UGxyWI%2FPRBIGUKJ4rjMkI7KTGrxtK%2Fimg.png","https://philosopher-chan.tistory.com/1237?category=941578"))
//        items.add(ContentModel("title4","https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcOYyBM%2Fbtq67Or43WW%2F17lZ3tKajnNwGPSCLtfnE1%2Fimg.png","https://philosopher-chan.tistory.com/1238?category=941578"))
//        items.add(ContentModel("title5", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fekn5wI%2Fbtq66UlN4bC%2F8NEzlyot7HT4PcjbdYAINk%2Fimg.png","https://philosopher-chan.tistory.com/1239?category=941578"))
//        items.add(ContentModel("title6", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F123LP%2Fbtq65qy4hAd%2F6dgpC13wgrdsnHigepoVT1%2Fimg.png","https://philosopher-chan.tistory.com/1240?category=941578"))
//        items.add(ContentModel("title7", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F123LP%2Fbtq65qy4hAd%2F6dgpC13wgrdsnHigepoVT1%2Fimg.png","https://philosopher-chan.tistory.com/1241?category=941578"))
//        items.add(ContentModel("title8", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FmBh5u%2Fbtq651yYxop%2FX3idRXeJ0VQEoT1d6Hln30%2Fimg.png","https://philosopher-chan.tistory.com/1242?category=941578"))
//        items.add(ContentModel("title9", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FlOnja%2Fbtq69Tmp7X4%2FoUvdIEteFbq4Z0ZtgCd4p0%2Fimg.png","https://philosopher-chan.tistory.com/1243?category=941578"))
//        items.add(ContentModel("title10", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FNNrYR%2Fbtq64wsW5VN%2FqIaAsfmFtcvh4Bketug9m0%2Fimg.png","https://philosopher-chan.tistory.com/1244?category=941578"))
//        items.add(ContentModel("title11", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FK917N%2Fbtq64SP5gxj%2FNzsfNAykamW7qv1hdusp1K%2Fimg.png","https://philosopher-chan.tistory.com/1245?category=941578"))
//        items.add(ContentModel("title12", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FeEO4sy%2Fbtq69SgK8L3%2FttCUxYHx9aPNebNwkPcI21%2Fimg.png","https://philosopher-chan.tistory.com/1246?category=941578"))
//        items.add(ContentModel("title13", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbdIKDG%2Fbtq64M96JFa%2FKcJiYgKuwKuP3fIyviXm90%2Fimg.png","https://philosopher-chan.tistory.com/1247?category=941578"))
//        items.add(ContentModel("title14", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FFtY3t%2Fbtq65q6P4Zr%2FWe64GM8KzHAlGE3xQ2nDjk%2Fimg.png","https://philosopher-chan.tistory.com/1248?category=941578"))
//        items.add(ContentModel("title15", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FOtaMq%2Fbtq67OMpk4W%2FH1cd0mda3n2wNWgVL9Dqy0%2Fimg.png","https://philosopher-chan.tistory.com/1249?category=941578"))

        rv.adapter = rvAdapter

        rv.layoutManager = GridLayoutManager(this, 2)

        getBookmarkData()
    }

    private fun getBookmarkData(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                bookmarkIdList.clear()

                for(dataModel in dataSnapshot.children){

                    Log.d("getBookmarkData", dataModel.key.toString())
                    Log.d("getBookmarkData", dataModel.toString())

                    bookmarkIdList.add(dataModel.key.toString())
                }
                Log.d("ContentListActivity1", bookmarkIdList.toString())
                rvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {

                Log.w("ContentListActivity", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.bookmarkRef.child(FBAuth.getUid()).addValueEventListener(postListener)
    }
}