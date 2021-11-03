# mancheng
### 简单漫画网站
环境准备：
```
Android SDK:30
Gradle：6.8.3
SQLite：3.30.0
```
## 一、模块设计

# 登陆注册模块
![image](https://github.com/liuxiaofeii/mancheng/raw/master/picture/login.png)




# 漫画模块
![image](https://github.com/liuxiaofeii/mancheng/raw/master/picture/漫画模块.png)









# 用户模块
![image](https://github.com/liuxiaofeii/mancheng/raw/master/picture/用户模块.png)









## 二、数据库设计
books表
![image](https://github.com/liuxiaofeii/mancheng/raw/master/picture/漫画书表.png)
















contents表
![image](https://github.com/liuxiaofeii/mancheng/raw/master/picture/contents.png)











users表
![image](https://github.com/liuxiaofeii/mancheng/raw/master/picture/用户表.png)


























scs表
![image](https://github.com/liuxiaofeii/mancheng/raw/master/picture/收藏表.png)














historys表
![image](https://github.com/liuxiaofeii/mancheng/raw/master/picture/阅读历史表.png)



















## 二、效果展览
主界面
![image](https://github.com/liuxiaofeii/mancheng/raw/master/picture/首页界面.png)
登录界面
![image](https://github.com/liuxiaofeii/mancheng/raw/master/picture/登录界面.png)
用户注册
![image](https://github.com/liuxiaofeii/mancheng/raw/master/picture/注册界面.png)
关于

## 四、功能实现
#### 1）概述：
本APP用的是java语言实现，整体有登录验证功能，注册功能，漫画浏览功能，收藏漫画功能，阅读漫画功能，历史记录功能；
#### 2）登录验证功能：
用户点击登录按钮就调用UseService方法，查询该用户是否在数据库中，若不在，则提示账号密码错误，登录失败；若在，则跳转到主页面；
#### 3）注册功能：
用户点击注册按钮就调用UseService方法，插入到数据库中；
#### 4）漫画浏览功能：
当点击App会进入MainActivity，若MyApplication中的books为空，则新开个线程加载数据库里面books表的内容；同时MainActivity也会继续运行，跳转到HomeFragment，HomeFragment会获取MyApplication的books，将该Fragment的Layout文件的ListView的adater设置成books，动态加载漫画；
#### 5）收藏漫画功能：
###### -1、当点击App会进入MainActivity，会再加载books的线程里面，也加载关于scs表的内容，并将MyApplication里面的scs设置成该scs表的内容；
###### -2、当点击收藏按钮，会先通过scsId判断scs是否含有该对象，如果有，则不添加，并提示已在收藏列表中；若没有，则添加，并提示已收藏；
###### -3、当点击我的收藏，会进入ScActivity，先取出MyApplication里面的scs，设置BookScAdater为该scs
#### 6）阅读漫画功能：
点击开始阅读按钮或者每个章节按钮，先将某一话的content放入intent，之后会跳转到ChapterActivity，
intent里面有contentBookId参数和contentNO参数，通过该参数，从数据库检索出对应数据封装成集合ContentPath，然后设置ContentPathAdapter为该集合；
#### 7）历史记录功能：
点击开始阅读或者每个章节按钮都会去和myApplication的historys集合比较，若存在并且章节目录不一样，则更新该集合和数据库historys表内容；若不存在直接添加；若一样不更新；
