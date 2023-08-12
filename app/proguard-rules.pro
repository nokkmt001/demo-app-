# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# For Butterknife:
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**

# Version 7
-keep class **$$ViewBinder { *; }
# Version 8
-keep class **_ViewBinding { *; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keep class com.sun.tools.** { *; }
-keep class com.sun.source.** { *; }
-keep class android.content.pm.** { *; }
-keep class javax.annotation.** { *; }
-keep class javax.annotation.processing.ProcessingEnvironment { *; }
-keep class butterknife.compiler.ButterKnifeProcessor { *; }
-keep class com.google.gson.** { *; }
-keep class com.google.common** { *; }
-keep class android.app.** { *; }
-keep class com.google.gson.annotations.** { *; }
-keep class com.facebook.stetho.** { *; }
-keep class es.dmoral.toasty.** { *; }
-keep class com.android.** { *; }
-keep class android.** { *; }
-keep class mq.com.chuohapps.customview.** { *; }
-keep class android.support.annotation.** { *; }
-keep class com.transitionseverywhere.** { *; }
-keep class org.greenrobot.eventbus.** { *; }
-keep class okhttp3.** { *; }
-keep class com.bumptech.glide.** { *; }
-keep class retrofit2.** { *; }
-keep class com.squareup.retrofit2.** { *; }
-keep class com.google.common.** { *; }
-keep class com.fasterxml.jackson.** { *; }
-keep class org.w3c.** { *; }
-keep class com.google.j2objc.annotations.** { *; }
-keep class com.google.android.gms.** { *; }
-keep class com.google.gms.** { *; }

-dontwarn org.w3c.**
-dontwarn butterknife.Views$InjectViewProcessor
-dontwarn javax.inject.**
-dontwarn sun.misc.Unsafe
-dontwarn javax.xml.**
-dontwarn okio.**
-dontwarn retrofit2.Platform$Java8
-dontwarn java.nio.**
-dontwarn javax.annotation.**
-dontwarn javax.lang.**
-dontwarn javax.tools.**
-dontwarn com.squareup.javapoet.**
-dontwarn android.app.**
-dontwarn android.content.pm.**
-dontwarn com.google.**
-dontwarn javax.annotation.proccessing.**
-dontwarn org.conscrypt.**

#for event bus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
-keepclassmembers class ** {
    public void onEvent*(**);
}