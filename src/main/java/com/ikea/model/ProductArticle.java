package com.ikea.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class ProductArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JsonProperty("art_id")
    private Article article;

    @JsonProperty("amount_of")
    private int amountOf;

    public ProductArticle() {
    }

    public ProductArticle(Article article, int amountOf) {
        this.article = article;
        this.amountOf = amountOf;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getAmountOf() {
        return amountOf;
    }

    public void setAmountOf(int amountOf) {
        this.amountOf = amountOf;
    }

}
