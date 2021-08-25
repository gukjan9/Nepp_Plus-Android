package com.gukjang.pizzaorderapp_210825

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.gukjang.pizzaorderapp_210825.datas.StoreData
// import com.gun0912.tedpermission.PermissionListener
// import com.gun0912.tedpermission.normal.TedPermission
import kotlinx.android.synthetic.main.activity_view_store_detail.*

class ViewStoreDetailActivity : BaseActivity() {

    lateinit var mStoreData : StoreData                             // 밑에랑 형식 안 맞아서 에러 -> as StoreData 라고 형변환

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_store_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        dialBtn.setOnClickListener {

            // 210825 현재 TedPermission 이 이상함
//            val pl = object : PermissionListener {
//                override fun onPermissionGranted() {
                    val myUri = Uri.parse("tel:${mStoreData.phoneNum}")
                    val myIntent = Intent(Intent.ACTION_DIAL, myUri)
                    startActivity(myIntent)
//                }
//
//                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
//                    Toast.makeText(mContext, "권한 획득 실패", Toast.LENGTH_SHORT).show()
//                }
//            }
//            TedPermission.create()
//                .setPermissionListener(pl)
//                .setPermissions(Manifest.permission.CALL_PHONE)
//                .setDeniedMessage("[설정] 에서 숸한을 허용해주셔야 합니다")
//                .check()
        }
    }

    override fun setValues() {
        mStoreData = intent.getSerializableExtra("store") as StoreData           // putExtra 에러 해결

        nameTxt.text = mStoreData.name
        phoneNumTxt.text = mStoreData.phoneNum

        Glide.with(mContext).load(mStoreData.logoURL).into(logoImg)
    }
}