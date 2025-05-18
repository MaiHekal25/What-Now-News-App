package com.training.whatnow

data class News(
    var articles : ArrayList<Article>
)
data class Article(
    var title : String,
    var url : String,
    var urlToImage : String,
)
