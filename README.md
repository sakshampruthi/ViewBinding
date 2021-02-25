# View Binding Library

[![Version](https://jitpack.io/v/sakshampruthi/ViewBinding.svg)](https://jitpack.io/#sakshampruthi/ViewBinding)
----------------------------------------------------------

A library to use [View Binding](https://developer.android.com/topic/libraries/view-binding) using a single line by delegation in Kotlin

### Adding dependencies
Add this to your build.gradle (Project level):
```groovy
allprojects {
	repositories {
		....
		maven { url 'https://jitpack.io' }
	}
}
```

enable viewbinding in your app/build.gradle (Module level):
```groovy
android {
    ....
    buildFeatures {
        viewBinding true
    }
}
```

Add the dependencies in your app/build.gradle:
```groovy
dependencies {
    ....
    implementation 'com.github.sakshampruthi:viewbinding:1.0.0'
}
```

### How to use the library

If you use findViewById or kotlin syntheics(depreciated) follow this process: 

****findViewById:****
```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);    //replace this with view binding as shown below
          
        findViewById(R.id.textView).text = getString(R.string.view_bind)

        findViewById(R.id.clickMe).setOnClickListener {
            toast("View Binding Works")
        }
        ....
    }
}
```

Use it in your **Activity**, just extend the binding variable (which extends yours generated binding view) and add ***by viewBinding()*** at the end:
```kotlin
class MainActivity : AppCompatActivity() {

     private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.textView.text = getString(R.string.view_bind)  //replacing findViewById by binding

        binding.clickMe.setOnClickListener {
            toast("View Binding Works")
        }
        ....
    }
}
```
For Fragment the delgation is shown below
```kotlin
class MainFragment : Fragment(R.layout.fragment_main) {

     private val binding: FragmentMainBinding by viewBinding()
     
     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
	
         binding.textView.text = getString(R.string.view_bind)  //replacing findViewById by binding

        binding.clickMe.setOnClickListener {
            context?.toast("View Binding Works")
        }
        ....
    }
}
```


If you using proguard, add this line to your proguard-rules.pro.
```
#ViewBinding
-keep class * implements androidx.viewbinding.ViewBinding {
    public static *** bind(android.view.View);
    public static *** inflate(android.view.LayoutInflater);
}
```
