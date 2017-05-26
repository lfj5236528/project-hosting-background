package com.zgsy.bs.web.controller.user;


import com.alibaba.dubbo.config.annotation.Reference;
import com.zgsy.bs.common.model.Response;
import com.zgsy.bs.common.utils.Arguments;
import com.zgsy.bs.common.utils.EncryptUtil;
import com.zgsy.bs.user.enums.UserStatus;
import com.zgsy.bs.user.impl.manager.UserAndGroupManager;
import com.zgsy.bs.user.model.BasicUser;
import com.zgsy.bs.user.model.ProjectGroup;
import com.zgsy.bs.user.service.BasicUserReadService;
import com.zgsy.bs.user.service.BasicUserWriteService;
import com.zgsy.bs.user.service.ProjectGroupReadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangfujie on 16/12/19
 */
@RestController
@RequestMapping("/api/web/users")
@Slf4j
@CrossOrigin
public class Users {

    @Reference(version = "1.0.0")
    private BasicUserWriteService basicUserWriteService;
    @Reference(version = "1.0.0")
    private BasicUserReadService basicUserReadService;
    @Reference(version = "1.0.0")
    private UserAndGroupManager userAndGroupManager;
    @Reference(version = "1.0.0")
    private ProjectGroupReadService projectGroupReadService;


    /**
     * 注册用户
     *
     * @param name     用户昵称
     * @param mobile   用户手机号
     * @param password 密码
     * @return 是否注册成功
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Boolean> create(String name, String mobile, String password) {
        Response<Boolean> response = new Response<Boolean>();
        BasicUser basicUser = new BasicUser();
        basicUser.setName(mobile);
        basicUser.setMobile(mobile);
        basicUser.setStatus(UserStatus.USER_STATUS_NORMAL.index());
        basicUser.setPassword(EncryptUtil.encrypt(password));
        if (!basicUserWriteService.createBasicUser(basicUser)) {
            log.error("user create failed");
            response.setResult(Boolean.FALSE);
            response.setMessage("用户创建失败!");
            return response;
        }
        response.setResult(Boolean.TRUE);
        response.setMessage("用户创建成功!");
        return response;
    }

    /**
     * 用户登录,如果用户登录成功会存放用户信息进session
     *
     * @param mobile             手机号
     * @param password           密码
     * @param httpServletRequest
     * @return 是否登录成功
     */

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response<Boolean> login(String mobile, String password, HttpServletRequest httpServletRequest) {
        Response<Boolean> response = new Response<Boolean>();
        HttpSession httpSession = httpServletRequest.getSession();
        log.error("session id{}", httpSession.getId());
        if (httpSession.getAttribute("loginUser@"+httpSession.getId()) != null) {
            log.error("user had login!");
            response.setResult(Boolean.FALSE);
            response.setMessage("用户已经登录!");
            return response;
        } else {
            BasicUser basicUser = basicUserReadService.findBasicUserByMobile(mobile);
            if (!EncryptUtil.match(password, basicUser.getPassword())) {
                log.error("password wrong !");
                response.setResult(Boolean.FALSE);
                response.setMessage("用户密码错误!");
                return response;
            } else {
                httpSession.setAttribute("loginUser@"+httpSession.getId(), basicUser);
                log.error("loginSessionId"+httpSession.getId());
                response.setResult(Boolean.TRUE);
                response.setMessage("用户登录成功!");
                return response;
            }
        }
    }

    /**
     * 用户修改密码
     *
     * @param newPassword        新密码
     * @param httpServletRequest
     * @return 是否修改成功
     */
    @RequestMapping(value = "/update-password", method = RequestMethod.POST)
    public Response<Boolean> updatePassword(String oldPassword,String newPassword, HttpServletRequest httpServletRequest) {
        Response<Boolean> response = new Response<Boolean>();
        HttpSession httpSession = httpServletRequest.getSession();
        if (httpSession.getAttribute("loginUser@"+httpSession.getId()) != null) {
            BasicUser basicUser = (BasicUser) httpSession.getAttribute("loginUser@"+httpSession.getId());
            if (EncryptUtil.match(oldPassword,basicUser.getPassword())){
                basicUser.setPassword(EncryptUtil.encrypt(newPassword));
                basicUserWriteService.updateBasicUser(basicUser);
                response.setResult(Boolean.TRUE);
                response.setMessage("修改密码成功!");
                return response;
            }else {
                response.setResult(Boolean.FALSE);
                response.setMessage("旧密码输入错误!");
                return response;
            }
        }
        log.error("user not login!");
        response.setResult(Boolean.FALSE);
        response.setMessage("用户未登录!");
        return response;
    }


    @RequestMapping(value = "/update-email", method = RequestMethod.POST)
    public Response<Boolean> updatePassword(String email, HttpServletRequest httpServletRequest) {
        Response<Boolean> response = new Response<Boolean>();
        HttpSession httpSession = httpServletRequest.getSession();
        if (httpSession.getAttribute("loginUser@"+httpSession.getId()) != null) {
            BasicUser basicUser = (BasicUser) httpSession.getAttribute("loginUser@"+httpSession.getId());
            basicUser.setEmail(email);
            basicUserWriteService.updateBasicUser(basicUser);
            response.setResult(Boolean.TRUE);
            response.setMessage("修改邮箱成功！");
            return response;
        }
        log.error("user not login!");
        response.setResult(Boolean.FALSE);
        response.setMessage("用户未登录!");
        return response;
    }

    /**
     * 添加用户至某团队
     *
     * @param userId  用户ID
     * @param groupId 团队ID
     * @return 是否添加成功
     */
    @RequestMapping(value = "/add-group", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Boolean> addUserGroup(Long userId, Long groupId) {
        Response<Boolean> response = new Response<Boolean>();
        BasicUser basicUser = basicUserReadService.findBasicUserById(userId);
        if (Arguments.isNull(basicUser)) {
            response.setResult(Boolean.FALSE);
            response.setMessage("操作的用户不存在!");
            return response;
        }
        ProjectGroup projectGroup = projectGroupReadService.findById(groupId);

        if (Arguments.isNull(projectGroup)) {
            response.setResult(Boolean.FALSE);
            response.setMessage("该团队不存在!");
            return response;
        }
        List<String> groupList = basicUser.getGroupIdsList();
        if (groupList.contains(String.valueOf(groupId))) {
            response.setResult(Boolean.FALSE);
            response.setMessage("该用户已经存在该团队!");
            return response;
        }
        groupList.add(String.valueOf(groupId));
        basicUser.setGroupIdsList(groupList);
        List<String> lists = projectGroup.getMembersIdsList();

        if (lists.contains(String.valueOf(userId))) {
            response.setResult(Boolean.FALSE);
            response.setMessage("该用户已经存在该团队!");
            return response;
        }
        lists.add(String.valueOf(userId));
        userAndGroupManager.updateBasicUserAndProjectGroup(projectGroup, basicUser);
        response.setResult(Boolean.TRUE);
        response.setMessage("添加团队成功!");
        return response;
    }

    /**
     * 移除用户团队
     *
     * @param userId  用户Id
     * @param groupId 团队Id
     * @return 是否移除成功
     */

    @RequestMapping(value = "/remove-group", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Boolean> removeUserGroup(Long userId, Long groupId) {
        Response<Boolean> response = new Response<Boolean>();
        BasicUser basicUser = basicUserReadService.findBasicUserById(userId);
        if (Arguments.isNull(basicUser)) {
            response.setResult(Boolean.FALSE);
            response.setMessage("操作的用户不存在!");
            return response;
        }
        ProjectGroup projectGroup = projectGroupReadService.findById(groupId);
        if (Arguments.isNull(projectGroup)) {
            response.setResult(Boolean.FALSE);
            response.setMessage("该团队不存在!");
            return response;
        }
        List<String> groupList = basicUser.getGroupIdsList();
        if (!groupList.contains(String.valueOf(groupId))) {
            response.setResult(Boolean.FALSE);
            response.setMessage("该用户不存在该团队!");
            return response;
        }
        groupList.remove(String.valueOf(groupId));
        basicUser.setGroupIdsList(groupList);
        List<String> lists = projectGroup.getMembersIdsList();
        if (!lists.contains(String.valueOf(userId))) {
            response.setResult(Boolean.FALSE);
            response.setMessage("该用户不存在该团队!");
            return response;
        }
        lists.remove(String.valueOf(userId));
        userAndGroupManager.updateBasicUserAndProjectGroup(projectGroup, basicUser);
        response.setResult(Boolean.TRUE);
        response.setMessage("移除团队成功!");
        return response;
    }

    /**
     * 获取对应用户所在的团队
     *
     * @param userId 用户ID
     * @return 团队信息
     */
    public Response<List<ProjectGroup>> findProjectGroupsByUserId(Long userId) {
        Response<List<ProjectGroup>> response = new Response<List<ProjectGroup>>();
        List<ProjectGroup> lists = new ArrayList<ProjectGroup>();
        BasicUser basicUser = basicUserReadService.findBasicUserById(userId);
        if (Arguments.isNull(basicUser)) {
            response.setInvoking(Boolean.FALSE);
            response.setMessage("操作的用户不存在!");
            return response;
        }
        List<String> rootLists = basicUser.getGroupIdsList();
        for (String id : rootLists) {
            Long groupId = Long.valueOf(id);
            ProjectGroup projectGroup = projectGroupReadService.findById(groupId);
            lists.add(projectGroup);
        }
        response.setMessage("获取团队信息成功");
        response.setResult(lists);
        return response;

    }

    /**
     * 检查用户是否登陆
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/check/login", method = RequestMethod.GET)
    public Response<BasicUser> checkUserLoginStatus(HttpServletRequest httpServletRequest){
        Response<BasicUser> response =new Response<BasicUser>();
        HttpSession httpSession = httpServletRequest.getSession();
        String sessionId=httpSession.getId();
        if (httpSession.getAttribute("loginUser@"+sessionId)!=null){
            response.setResult((BasicUser) httpSession.getAttribute("loginUser@"+sessionId));
            response.setMessage("用户已登录");
            return response;
        }else {
            response.setMessage("用户未登录");
            return response;
        }
    }

    /**
     * 用户注销
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Response<Boolean> logoutUserLoginStatus(HttpServletRequest httpServletRequest){
        Response<Boolean> response =new Response<Boolean>();
        HttpSession httpSession = httpServletRequest.getSession();
        String sessionId=httpSession.getId();
        if (httpSession.getAttribute("loginUser@"+sessionId)!=null){
            httpSession.removeAttribute("loginUser@"+sessionId);
            response.setResult(Boolean.TRUE);
            response.setMessage("用户已注销");
            return response;
        }else {
            response.setMessage("用户未登录");
            return response;
        }

    }


    @RequestMapping(value = "/findAll", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public  Response<List<BasicUser>> findAllUser(@RequestParam(required=false) Long groupId){
        Response<List<BasicUser>> response =new Response<List<BasicUser>>();
        List<BasicUser> list = basicUserReadService.findAll();
        if(!Arguments.isNull(groupId)){
            ProjectGroup projectGroup=projectGroupReadService.findById(groupId);
            List<String> ids=projectGroup.getMembersIdsList();
            for(int i=0;i<list.size();i++ ){
                String c =String.valueOf(list.get(i).getId());
                if(ids.contains(c)){
                    list.remove(i);
                    i--;
                }
            }
        }
        response.setResult(list);
        return response;
    }
}

