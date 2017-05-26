package com.zgsy.bs.web.controller.group;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zgsy.bs.common.model.Response;
import com.zgsy.bs.common.utils.Arguments;
import com.zgsy.bs.user.dto.ProjectGroupDetail;
import com.zgsy.bs.user.impl.manager.UserAndGroupManager;
import com.zgsy.bs.user.model.BasicUser;
import com.zgsy.bs.user.model.Project;
import com.zgsy.bs.user.model.ProjectGroup;
import com.zgsy.bs.user.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangfujie on 16/12/20
 */
@RestController
@Slf4j
@RequestMapping("/api/web/project/group")
@CrossOrigin
public class ProjectGroups {


    @Reference(version = "1.0.0")
    private ProjectGroupWriteService projectGroupWriteService;

    @Reference(version = "1.0.0")
    private ProjectGroupReadService projectGroupReadService;

    @Reference(version = "1.0.0")
    private UserAndGroupManager userAndGroupManager;

    @Reference(version="1.0.0")
    private BasicUserReadService basicUserReadService;


    @Reference(version = "1.0.0")
    private ProjectReadService projectReadService;
    @Reference(version = "1.0.0")
    private ProjectWriteService projectWriteService;

    /**
     * 创建团队
     *
     * @param projectGroup       团队信息
     * @param httpServletRequest
     * @return 是否创建成功
     */
    @RequestMapping(value = "/create-root", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Boolean> createRootProjectGroup(@RequestBody ProjectGroup projectGroup, HttpServletRequest httpServletRequest) {
        Response<Boolean> response = new Response<Boolean>();
        HttpSession httpSession = httpServletRequest.getSession();
        BasicUser basicUser = (BasicUser) httpSession.getAttribute("loginUser@"+httpSession.getId());
        if (Arguments.isNull(basicUser)) {
            log.error("user not login ");
            response.setResult(Boolean.FALSE);
            response.setMessage("请先登录用户");
            return response;
        }
        projectGroup.setLeaderId(basicUser.getId());
        if (!projectGroupWriteService.createProjectGroup(projectGroup)) {
            log.error("project group create failed!");
            response.setResult(Boolean.FALSE);
            response.setMessage("团队创建失败");
            return response;
        }
        response.setResult(Boolean.TRUE);
        response.setMessage("团队创建成功");
        return response;

    }


    /**
     * 编辑团队信息
     * @param projectGroup
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Boolean> updateProjectGroup(@RequestBody ProjectGroup projectGroup, HttpServletRequest httpServletRequest) {
        Response<Boolean> response = new Response<Boolean>();
        HttpSession httpSession = httpServletRequest.getSession();
        BasicUser basicUser = (BasicUser) httpSession.getAttribute("loginUser@"+httpSession.getId());
        if (Arguments.isNull(basicUser)) {
            log.error("user not login! ");
            response.setResult(Boolean.FALSE);
            response.setMessage("请先登录用户");
            return response;
        }
        if (!projectGroupWriteService.updateProjectGroup(projectGroup)) {
            log.error("project group update failed!");
            response.setResult(Boolean.FALSE);
            response.setMessage("团队编辑失败");
            return response;
        }
        response.setResult(Boolean.TRUE);
        response.setMessage("团队编辑成功");
        return response;

    }

    /**
     * 获取团队信息
     * @param groupId
     * @return
     */
    @RequestMapping(value = "/find-by-id",method = RequestMethod.GET)
    public Response<ProjectGroup> findProjectGroupById(Long groupId) {
        Response<ProjectGroup> response =new Response<ProjectGroup>();
        ProjectGroup projectGroup = projectGroupReadService.findById(groupId);
        if (Arguments.isNull(projectGroup)){
            response.setResult(null);
            response.setMessage("不存在该团队");
            return response;
        }
        response.setMessage("获取团队成功");
        response.setResult(projectGroup);
        return response;
    }



    /**
     * 获取该团队下的成员
     * @param groupId 团队Id
     * @return 团队成员信息
     */
    @RequestMapping(value = "/users",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<BasicUser>>  findBasicUsersByGroupId(Long groupId){
        Response<List<BasicUser>> response =new Response<List<BasicUser>>();
        List<BasicUser> list = new ArrayList<BasicUser>();
        ProjectGroup projectGroup = projectGroupReadService.findById(groupId);
        if (Arguments.isNull(projectGroup)){
            response.setResult(null);
            response.setMessage("不存在该团队");
            return response;
        }

        List<String> userIds = projectGroup.getMembersIdsList();
        for (String id : userIds){
            Long userId = Long.valueOf(id);
            BasicUser basicUser = basicUserReadService.findBasicUserById(userId);
            list.add(basicUser);
        }
        response.setResult(list);
        response.setMessage("获取团队成功成功");
        return response;
    }


    //删除团队
    @RequestMapping(value = "/delete",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Boolean> deleteProjectGroup(Long groupId){
        Response<Boolean> response =new Response<Boolean>();

        if(projectGroupWriteService.deleteProjectGroup(groupId)){
            List<Project> projects = projectReadService.findAll();
            for(Project project : projects){
                if (project.getGroupsIdsList()==null)continue;
                if(project.getGroupsIdsList().contains(String.valueOf(groupId))){
                   List<String> list= project.getGroupsIdsList();
                    list.remove(String.valueOf(groupId));
                    project.setGroupsIdsList(list);
                    projectWriteService.update(project);
                }
            }
            response.setMessage("删除团队成功！");
            response.setResult(Boolean.TRUE);
            return response;
        }

        response.setMessage("删除团队失败！");
        response.setResult(Boolean.FALSE);

        return response;


    }



    //我的团队
    @RequestMapping(value = "/my-group",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public  Response<List<ProjectGroupDetail>> findMyGroups(HttpServletRequest httpServletRequest){
        Response<List<ProjectGroupDetail>> response =new Response<List<ProjectGroupDetail>>();
        HttpSession httpSession = httpServletRequest.getSession();
        BasicUser basicUser =(BasicUser)httpSession.getAttribute("loginUser@"+httpSession.getId());
        if (basicUser==null){
            response.setMessage("用户未登录！");
            return response;

        }else {
            String userId =String.valueOf(basicUser.getId());
            List<ProjectGroupDetail> result = new ArrayList<ProjectGroupDetail>();
            List<ProjectGroup> all = projectGroupReadService.findAll();
            for(ProjectGroup projectGroup:all){
                if (projectGroup.getMembersIdsList().contains(userId)){
                    ProjectGroupDetail projectGroupDetail = new ProjectGroupDetail();
                    projectGroupDetail.setProjectGroup(projectGroup);
                    List<Project> projects = projectReadService.findAll();
                    List<String> projectNames = new ArrayList<String>();
                    for (Project tempProject : projects){
                       if(tempProject.getGroupsIdsList().contains(projectGroup.getId().toString())){
                           projectNames.add(tempProject.getName());

                       }
                    }
                    projectGroupDetail.setProjectNames(projectNames);
                    List<String> memberNames = new ArrayList<String>();
                    for(String tempId : projectGroup.getMembersIdsList()){
                        memberNames.add(basicUserReadService.findBasicUserById(Long.valueOf(tempId)).getName());
                    }
                    projectGroupDetail.setMemberNames(memberNames);
                    result.add(projectGroupDetail);
                }

            }
            response.setResult(result);
            response.setMessage("获取成功！");
            return  response;



        }

    }


}
