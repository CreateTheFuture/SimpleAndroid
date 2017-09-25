# SimpleAndroid
自己封装的开发包

依赖方式如下：
1. 在项目的根build.gradle文件中添加maven库

	allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
  
  
2.在项目build.gradle中添加依赖

  	dependencies {
	        compile 'com.github.CreateTheFuture:SimpleAndroid:1.0.0'
	}
