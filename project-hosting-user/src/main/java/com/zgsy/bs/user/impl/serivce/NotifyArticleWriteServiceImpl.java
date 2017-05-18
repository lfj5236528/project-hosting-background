package com.zgsy.bs.user.impl.serivce;

import com.alibaba.dubbo.config.annotation.Service;
import com.zgsy.bs.user.impl.dao.NotifyArticleDao;
import com.zgsy.bs.user.model.NotifyArticle;
import com.zgsy.bs.user.service.NotifyArticleWriteService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liangfujie on 16/12/21
 */
@Service(version = "1.0.0")
public class NotifyArticleWriteServiceImpl implements NotifyArticleWriteService{

    @Autowired
    private NotifyArticleDao notifyArticleDao;

    @Override
    public Boolean create(NotifyArticle notifyArticle) {
        return notifyArticleDao.create(notifyArticle);
    }

    @Override
    public Boolean update(NotifyArticle notifyArticle) {
        return notifyArticleDao.update(notifyArticle);
    }

    @Override
    public Boolean delete(Long id) {
        return notifyArticleDao.delete(id);
    }
}
