package com.zgsy.bs.user.impl.dao;

import com.zgsy.bs.common.dao.MyBatisDao;
import com.zgsy.bs.user.model.NotifyArticle;
import org.springframework.stereotype.Repository;

/**
 * Created by liangfujie on 16/12/21
 */
@Repository
public class NotifyArticleDao extends MyBatisDao<NotifyArticle>{
    public NotifyArticleDao() {
        super("notify.article");
    }
}
