package com.zgsy.bs.web.controller.article;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zgsy.bs.common.model.Response;
import com.zgsy.bs.common.utils.Arguments;
import com.zgsy.bs.user.model.BasicUser;
import com.zgsy.bs.user.model.NotifyArticle;
import com.zgsy.bs.user.service.NotifyArticleReadService;
import com.zgsy.bs.user.service.NotifyArticleWriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangfujie on 16/12/21
 */
@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/api/web/notify/article")
public class NotifyArticles {

    @Reference(version = "1.0.0")
    private NotifyArticleReadService notifyArticleReadService;

    @Reference(version = "1.0.0")
    private NotifyArticleWriteService notifyArticleWriteService;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Response<Boolean> create(@RequestBody NotifyArticle notifyArticle, HttpServletRequest httpServletRequest) {
        Response<Boolean> response = new Response<Boolean>();
        HttpSession httpSession = httpServletRequest.getSession();
        BasicUser basicUser = (BasicUser) httpSession.getAttribute("loginUser");
        if (Arguments.isNull(basicUser)) {
            response.setMessage("用户未登陆!");
            response.setResult(Boolean.FALSE);
            return response;
        }
        notifyArticle.setUserName(basicUser.getName());
        notifyArticle.setUserId(basicUser.getId());
        notifyArticle.setStatus(1);
        if (!notifyArticleWriteService.create(notifyArticle)) {
            response.setMessage("创建公告失败!");
            response.setResult(Boolean.FALSE);
            return response;

        }
        response.setMessage("创建公告成功!");
        response.setResult(Boolean.TRUE);
        return response;

    }


    @RequestMapping(value = "/detail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<NotifyArticle> detail(Long notifyArticleId) {
        Response<NotifyArticle> response = new Response<NotifyArticle>();

        NotifyArticle notifyArticle = notifyArticleReadService.findById(notifyArticleId);

        if (Arguments.isNull(notifyArticle)) {
            response.setMessage("对应公告不存在");
            response.setResult(null);
            return response;

        }
        response.setMessage("获取公告信息成功!");
        response.setResult(notifyArticle);
        return response;

    }

    @RequestMapping(value = "/find-all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<NotifyArticle>> findAll() {
        Response<List<NotifyArticle>> response = new Response<List<NotifyArticle>>();
        List<NotifyArticle> list = notifyArticleReadService.findAll();
        response.setMessage("获取全部通知信息成功!");
        response.setResult(list);
        return response;

    }

    /**
     * 获得用户的公告
     *
     * @param userId 用户ID
     * @return 公告信息
     */

    @RequestMapping(value = "/find-by-userId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<NotifyArticle>> findNotifyArticlesByUserId(Long userId) {

        Response<List<NotifyArticle>> response = new Response<List<NotifyArticle>>();
        List<NotifyArticle> list = notifyArticleReadService.findAll();
        List<NotifyArticle> resultList = new ArrayList<NotifyArticle>();


        for (NotifyArticle notifyArticle : list) {
            List<String> members = notifyArticle.getNotifyMembersList();
            if (members.contains(String.valueOf(userId))) {
                resultList.add(notifyArticle);
            }
        }

        response.setMessage("获取用户公告成功!");
        response.setResult(resultList);
        return response;

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response<Boolean> update(NotifyArticle notifyArticle) {
        Response<Boolean> response = new Response<Boolean>();

        if (!notifyArticleWriteService.update(notifyArticle)) {

            response.setMessage("更新公告失败");
            response.setResult(Boolean.FALSE);
            return response;
        }
        response.setMessage("更新公告成功");
        response.setResult(Boolean.TRUE);
        return response;

    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Response<Boolean> delete(Long notifyArticleId) {
        Response<Boolean> response = new Response<Boolean>();
        NotifyArticle notifyArticle = notifyArticleReadService.findById(notifyArticleId);
        if (Arguments.isNull(notifyArticle)) {
            response.setMessage("对应公告不存在");
            response.setResult(Boolean.FALSE);
            return response;
        }
        notifyArticle.setStatus(-1);
        response.setMessage("删除公告成功!");
        response.setResult(Boolean.TRUE);
        return response;

    }


}
