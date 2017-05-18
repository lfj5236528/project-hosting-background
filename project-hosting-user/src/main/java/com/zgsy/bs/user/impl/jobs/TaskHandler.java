package com.zgsy.bs.user.impl.jobs;

import com.zgsy.bs.user.enums.TaskStatus;
import com.zgsy.bs.user.impl.dao.NotifyArticleDao;
import com.zgsy.bs.user.impl.dao.ProjectTaskDao;
import com.zgsy.bs.user.model.NotifyArticle;
import com.zgsy.bs.user.model.ProjectTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Java on 2017/5/18.
 */
@Component
public class TaskHandler {

    @Autowired
    private ProjectTaskDao projectTaskDao;

    @Autowired
    private NotifyArticleDao notifyArticleDao ;


    @Scheduled(cron = "0 */5 * * * ?")
    public void init(){
        System.out.println("任务脚步开始执行,开始时间:"+new Date());
        List<ProjectTask> list = projectTaskDao.findAll();
        List<ProjectTask> soonList = new ArrayList<ProjectTask>();
        List<ProjectTask> outList = new ArrayList<ProjectTask>();
        for (ProjectTask projectTask : list){
            if (projectTask.getStatus()== TaskStatus.TASK_NORMAL.index()){
                Date now = new Date();
                Date end = projectTask.getEndedAt();
                if (end!=null&&end!=null){
                    int days =daysOfTwo(end,now);
                    if (days<0){
                        if(days>=-3){
                            Boolean tag = true;
                            NotifyArticle notifyArticle = new NotifyArticle();
                            notifyArticle.setTheme("你的任务即将到达截止任务时间，请及时处理，任务名称："+projectTask.getName());
                            notifyArticle.setNotifyMembersList(projectTask.getUserIdsList());
                            notifyArticle.setContent(projectTask.getId().toString());
                            List<NotifyArticle> notifyArticles = notifyArticleDao.findAll();
                            Long articleId=0l;
                            for (NotifyArticle notify: notifyArticles){
                                if (notify.getContent().equals(projectTask.getId().toString())){
                                    tag =false;
                                    articleId =notify.getId();
                                    break;
                                }
                            }
                            if (tag){
                                notifyArticleDao.create(notifyArticle);
                            }else {
                                notifyArticle.setId(articleId);
                                notifyArticleDao.update(notifyArticle);
                            }

                        }
                    }else {
                        Boolean tag = true;
                    projectTask.setStatus(TaskStatus.TASK_OVERDUE.index());
                        Map<String,String> maps =projectTask.getExtras();
                        maps.put("outDays",String.valueOf(days));
                        projectTask.setExtras(maps);
                        projectTaskDao.update(projectTask);
                        NotifyArticle notifyArticle = new NotifyArticle();
                        notifyArticle.setTheme("你的任务已经逾期，请及时处理，任务名称："+projectTask.getName());
                        notifyArticle.setNotifyMembersList(projectTask.getUserIdsList());
                        Long articleId=0l;
                        List<NotifyArticle> notifyArticles = notifyArticleDao.findAll();
                        for (NotifyArticle notify: notifyArticles){
                            if (notify.getContent().equals(projectTask.getId().toString())){
                                tag =false;
                                articleId =notify.getId();
                                break;
                            }
                        }
                        if (tag){
                            notifyArticleDao.create(notifyArticle);
                        }else {
                            notifyArticle.setId(articleId);
                            notifyArticleDao.update(notifyArticle);
                        }

                    }
                }
            }
        }
    }

    public static int daysOfTwo(Date fDate, Date oDate) {

        Calendar aCalendar = Calendar.getInstance();

        aCalendar.setTime(fDate);

        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

        aCalendar.setTime(oDate);

        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

        return day2 - day1;

    }

}
