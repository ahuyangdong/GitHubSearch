# GitHubSearch

![platform](https://img.shields.io/badge/platform-Android-lightgrey.svg)
![Gradle](https://img.shields.io/badge/Gradle-2.3.3-brightgreen.svg)
![ide](https://img.shields.io/badge/IDE-Android%20Studio-brightgreen.svg)
![progress](http://progressed.io/bar/100?title=completed)
[![last commit](https://img.shields.io/github/last-commit/ahuyangdong/GitHubSearch.svg)](https://github.com/ahuyangdong/GitHubSearch/commits/master)
![repo size](https://img.shields.io/github/repo-size/ahuyangdong/GitHubSearch.svg)
[![Licence](https://img.shields.io/github/license/ahuyangdong/GitHubSearch.svg)](https://github.com/ahuyangdong/GitHubSearch/blob/master/LICENSE)

GitHubSearch, a demo project using github search api for data interface.

基于Android的GitHub仓库搜索小应用，演示效果：

![image](https://github.com/ahuyangdong/GitHubSearch/raw/master/images/demo.gif)
## 功能
1. 头部搜索
2. 下拉刷新
3. 上拉加载更多
4. 本地数据库读写

## 控件
SwipeRecyclerView
> deadline.swiperecyclerview.SwipeRecyclerView

CardView
> android.support.v7.widget.CardView

## 数据接口
- api

https://api.github.com/search/repositories

- api doc

https://developer.github.com/v3/search/#search-repositories

## 相关技术
- SwipeRecyclerView

下拉刷新、上拉加载更多

https://github.com/niniloveyou/SwipeRecyclerView

- Gson

网络json数据解析

[![gson](https://avatars2.githubusercontent.com/u/1342004?s=100&v=4)](https://github.com/google/gson)

- retrofit2

网络请求框架

[![retrofit2](https://avatars0.githubusercontent.com/u/82592?s=100&v=4)](
https://github.com/square/retrofit)

- greenDAO

Android数据库ORM框架

https://github.com/greenrobot/greenDAO

- glide

网络图片加载框架

[![glide](https://github.com/bumptech/glide/blob/master/static/glide_logo.png?raw=true)](
https://github.com/bumptech/glide)

- Butter Knife

View注入、事件绑定

[![butterknife](https://github.com/JakeWharton/butterknife/raw/master/website/static/logo.png)](
https://github.com/JakeWharton/butterknife)
