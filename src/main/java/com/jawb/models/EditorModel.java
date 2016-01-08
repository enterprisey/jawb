package com.jawb.models;

import net.sourceforge.jwbf.core.contentRep.Article;

/**
 * Contains article state.
 */
public class EditorModel {
    private Article currentArticle;

    public Article getCurrentArticle() {
        return currentArticle;
    }

    public void setCurrentArticle( Article currentArticle ) {
        this.currentArticle = currentArticle;
    }
}
