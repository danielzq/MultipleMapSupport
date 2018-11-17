# MultipleMapSupport
[ ![Download](https://api.bintray.com/packages/zq26zhangqi/maven/MultipleMapSupport/images/download.svg) ](https://bintray.com/zq26zhangqi/maven/MultipleMapSupport/_latestVersion)

Using one API to support both GoogleMap and HereMap API.

**How to use:**
1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```
2. Add below codes in the your app build.gradle file
```
dependencies {
	implementation 'com.github.danielzq:MultipleMapSupport:1.0.5'
}
```
