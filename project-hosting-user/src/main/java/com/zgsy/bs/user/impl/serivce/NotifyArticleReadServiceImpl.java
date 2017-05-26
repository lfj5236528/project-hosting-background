package com.zgsy.bs.user.impl.serivce;

import com.alibaba.dubbo.config.annotation.Service;
import com.zgsy.bs.user.impl.dao.NotifyArticleDao;
import com.zgsy.bs.user.model.NotifyArticle;
import com.zgsy.bs.user.service.NotifyArticleReadService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by liangfujie on 16/12/21
 */
@Service(version = "1.0.0")
public class NotifyArticleReadServiceImpl implements NotifyArticleReadService {

    @Autowired
    private NotifyArticleDao notifyArticleDao;

    @Override
    public NotifyArticle findById(Long notifyArticleId) {
        return notifyArticleDao.findById(notifyArticleId);
    }

    @Override
    public List<NotifyArticle> findAll() {
        return notifyArticleDao.findAll();
    }
}
