package com.wechat.pojo;

import java.util.List;

/**
 * User: Matt
 * Date: 2017/1/13
 * Time: 15:41
 * Description: description
 */
public class NewsMessage extends BaseMessage{

    private Integer ArticleCount;
    private List<News> Articles;

    public Integer getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(Integer articleCount) {
        ArticleCount = articleCount;
    }

    public List<News> getArticles() {
        return Articles;
    }

    public void setArticles(List<News> articles) {
        Articles = articles;
    }
}
