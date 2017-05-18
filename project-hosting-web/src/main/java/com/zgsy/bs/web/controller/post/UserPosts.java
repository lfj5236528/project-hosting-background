package com.zgsy.bs.web.controller.post;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zgsy.bs.common.model.Response;
import com.zgsy.bs.user.dto.UserReply;
import com.zgsy.bs.user.model.BasicUser;
import com.zgsy.bs.user.model.UserPost;
import com.zgsy.bs.user.service.UserPostReadService;
import com.zgsy.bs.user.service.UserPostWriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Java on 2017/5/14.
 */

@RestController
@RequestMapping("/api/web/posts")
@Slf4j
@CrossOrigin
public class UserPosts {

    @Reference(version = "1.0.0")
    private UserPostWriteService userPostWriteService;
    @Reference(version = "1.0.0")
    private UserPostReadService userPostReadService;

    @RequestMapping(value = "/create",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Boolean> createUserPost(String title,String content, HttpServletRequest httpServletRequest){
        UserPost userPost = new UserPost();
        userPost.setTitle(title);
        userPost.setContent(content);
        Response<Boolean> response = new Response<Boolean>();
        HttpSession httpSession = httpServletRequest.getSession();
        if (httpSession.getAttribute("loginUser@"+httpSession.getId()) != null) {
            BasicUser basicUser = (BasicUser) httpSession.getAttribute("loginUser@"+httpSession.getId());
            userPost.setUserId(basicUser.getId().intValue());
            userPost.setUserName(basicUser.getName());
            userPost.setStatus(1);//默认发布
            userPostWriteService.createUserPost(userPost);
            response.setMessage("发布成功！");
            response.setResult(Boolean.TRUE);
            return response;
        }
        log.error("user not login!");
        response.setResult(Boolean.FALSE);
        response.setMessage("用户未登录!");
        return response;

    }


    @RequestMapping(value = "/find-all",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<UserPost>> findAll(){
        Response<List<UserPost>> response =new Response<List<UserPost>>();
        List<UserPost> list =userPostReadService.findAll();
        response.setResult(list);
        response.setMessage("success!");
        return response;


    }


    @RequestMapping(value = "/reply",method = RequestMethod.POST)
    public Response<Boolean> reply(Long postId,String content,HttpServletRequest httpServletRequest){

        Response<Boolean> response = new Response<Boolean>();
        UserPost userPost= userPostReadService.findById(postId);
        if(userPost==null){
            response.setResult(Boolean.FALSE);
            response.setMessage("不存在的帖子!");
            return response;
        }
        HttpSession httpSession = httpServletRequest.getSession();
        BasicUser basicUser =(BasicUser)httpSession.getAttribute("loginUser@"+httpSession.getId());
        if (basicUser!= null) {

            List<UserReply> list = userPost.getReplyInfoList();
            UserReply userReply =new UserReply();
            userReply.setUserName(basicUser.getName());
            userReply.setUserId(basicUser.getId());
            userReply.setReplayDate(new Date());
            userReply.setContent(content);
            if (list==null){
                userReply.setNumber(1);
                list=new ArrayList<UserReply>();

            }else {
                userReply.setNumber(list.size()+1);

            }

            list.add(userReply);
            userPost.setReplyInfoList(list);
            userPostWriteService.updateUserPost(userPost);
            response.setMessage("发表成功！");
            response.setResult(Boolean.TRUE);
            return response;

        }
        log.error("user not login!");
        response.setResult(Boolean.FALSE);
        response.setMessage("用户未登录!");
        return response;

    }





}
