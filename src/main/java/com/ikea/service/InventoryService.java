package com.ikea.service;

import com.ikea.model.Article;
import com.ikea.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private ArticleRepository articleRepository;

    public InventoryService() {
    }

    public void save(List<Article> articles) {
        articleRepository.saveAll(articles);
    }

    public void save(Article article) {
        articleRepository.save(article);
    }
}
