# PluginInstallApkDemo
在APP内下载一个APK进行安装并启动

1.安装的APK在桌面不显示图标（需要在被安装的APK的AnroidMainifest中去掉LAUNCH属性）

2.根据1可知，显式进入安装的apk的方式就是在原App中下载并启动，从而产生一种应用场景：原APP中提供多个业务模块，在需要的时候点击按钮后下载相应的APK
  且安装后该按钮成为进入该功能模块的入口
  
3.与无需安装的插件化有区别
