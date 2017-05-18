package com.zgsy.bs.user.service;

import com.zgsy.bs.user.model.NotifyArticle;

import java.util.List;

/**
 * Created by liangfujie on 16/12/21
 */
public interface NotifyArticleReadService {

    public NotifyArticle findById(Long notifyArticleId);

    public List<NotifyArticle> findAll();

}
