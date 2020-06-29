package com.example.android_project

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.plugin.common.MethodChannel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG_FLUTTER_FRAGMENT = "flutter_fragment"
        //
        private val METHOD_CHANNEL = "samples.flutter.dev/launchfragment"
        private lateinit var channel: MethodChannel
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar: ActionBar? = supportActionBar
        actionBar!!.title = "MainActivity"
        //
        btn_default.setOnClickListener{
            startActivity(FlutterActivity.createDefaultIntent(this))
        }
        //
        btn_custom_route.setOnClickListener{
            startActivity(
                FlutterActivity
                    .withCachedEngine("activityEngineId")
                    .build(this)
            )
        }
        initLaunchActivity()
        //
        btn_launch_fragment.setOnClickListener {
            attachFlutterFragment()
        }
    }

    private fun initLaunchActivity(){
        channel = MethodChannel(
            FlutterEngineCache
                .getInstance()
                .get("activityEngineId")!!.dartExecutor, METHOD_CHANNEL)

        channel.setMethodCallHandler { call, result ->
            when (call.method) {
                "launchFragment" -> {
                    startActivity(Intent(this, LaunchFromFlutterActivity::class.java))
                    result.success("OK")
                }
            }
        }
    }

    private fun attachFlutterFragment() {
        var flutterFragment = supportFragmentManager.findFragmentByTag(TAG_FLUTTER_FRAGMENT) as FlutterFragment?
        if (null == flutterFragment) {
            flutterFragment =
                FlutterFragment.withCachedEngine("fragmentEngineId")
                    .shouldAttachEngineToActivity(true)
                    .build() as FlutterFragment
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container,
                flutterFragment,
                TAG_FLUTTER_FRAGMENT
            )
            .commit()
    }
}
