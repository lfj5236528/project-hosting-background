package com.zgsy.bs.user.service;

import com.zgsy.bs.user.model.NotifyArticle;

/**
 * Created by liangfujie on 16/12/21
 */
public interface NotifyArticleWriteService {

    public Boolean create(NotifyArticle notifyArticle);

    public Boolean update (NotifyArticle notifyArticle);

    public Boolean delete (Long id);
}
