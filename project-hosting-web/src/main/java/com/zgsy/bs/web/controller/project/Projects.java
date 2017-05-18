package com.zgsy.bs.web.controller.project;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zgsy.bs.common.model.Response;
import com.zgsy.bs.common.utils.Arguments;
import com.zgsy.bs.user.dto.ProjectAndGroupName;
import com.zgsy.bs.user.model.BasicUser;
import com.zgsy.bs.user.model.Project;
import com.zgsy.bs.user.model.ProjectGroup;
import com.zgsy.bs.user.service.ProjectGroupReadService;
import com.zgsy.bs.user.service.ProjectReadService;
import com.zgsy.bs.user.service.ProjectWriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Java on 2017/5/9.
 */
@RestController
@Slf4j
@RequestMapping("/api/web/projects")
@CrossOrigin

public class Projects {

    @Reference(version = "1.0.0")
    private ProjectReadService projectReadService;
    @Reference(version = "1.0.0")
    private ProjectGroupReadService projectGroupReadService;

    @Reference(version = "1.0.0")
    private ProjectWriteService projectWriteService;

    @RequestMapping(value = "/findByCreaterId",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Project>> findByCreaterId(HttpServletRequest httpServletRequest){
        Response<List<Project>> response =new Response<List<Project>>();
        HttpSession httpSession = httpServletRequest.getSession();
        BasicUser basicUser =(BasicUser)httpSession.getAttribute("loginUser@"+httpSession.getId());
        if (Arguments.isNull(basicUser)){
            response.setMessage("获取失败！");
            return  response;
        }
        Long createrId = basicUser.getId();
        List<Project> lists = projectReadService.findByCreaterId(createrId);
        if (lists.size()>=0){
            response.setResult(lists);
            response.setMessage("获取成功！");
        }else {
            response.setMessage("获取失败！");
        }
        return response;
    }

    @RequestMapping(value = "/findById",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Project> findById(Long id){
        Response<Project> response =new Response<Project>();
       Project project = projectReadService.findById(id);
        response.setResult(project);
        response.setMessage("获取成功！");
        return response;
    }


    @RequestMapping(value = "/find-group",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<ProjectGroup>> findProjectGroupsById(Long id){
        Response<List<ProjectGroup>> response =new Response<List<ProjectGroup>>();
        Project project = projectReadService.findById(id);

        List<String> ids = project.getGroupsIdsList();
        List<ProjectGroup> lists =new ArrayList<ProjectGroup>();

        for(String i: ids){
            Long index =Long.parseLong(i);
            lists.add( projectGroupReadService.findById(index));
        }
        response.setResult(lists);
        return response;

    }



    @RequestMapping(value = "/add-group",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Boolean> findProjectGroupsById(Long id,Long groupId){
       List<ProjectGroup> lists= projectGroupReadService.findAll();
        groupId= lists.get(lists.size()-1).getId();
        Response<Boolean> response =new Response<Boolean>();
        Project project = projectReadService.findById(id);
        List<String> ids = project.getGroupsIdsList();
        ids.add(groupId.toString());
        project.setGroupsIdsList(ids);
        projectWriteService.update(project);
        response.setResult(Boolean.TRUE);
        return response;

    }


    //我的项目
    @RequestMapping(value = "/my-project",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<ProjectAndGroupName>> findMyProject(HttpServletRequest httpServletRequest){
        Response<List<ProjectAndGroupName>> response =new Response<List<ProjectAndGroupName>>();
        HttpSession httpSession = httpServletRequest.getSession();
        BasicUser basicUser =(BasicUser)httpSession.getAttribute("loginUser@"+httpSession.getId());
        if (basicUser==null){
            response.setMessage("用户未登录！");
            return response;

        }else {
            String userId= String.valueOf(basicUser.getId());
            List<ProjectGroup> list = projectGroupReadService.findAll();
            List<ProjectGroup> inList = new ArrayList<ProjectGroup>();
            if(list!=null){
                for(ProjectGroup projectGroup : list){
                    if(projectGroup.getMembersIdsList().contains(userId)){
                        inList.add(projectGroup);
                    }
                }

                List<Project> lists = projectReadService.findAll();
                List<ProjectAndGroupName> result = new ArrayList<ProjectAndGroupName>();

                for(ProjectGroup projectGroup:inList){
                    for (Project project:lists){
                        if(project.getGroupsIdsList().contains(String.valueOf(projectGroup.getId()))||project.getCreaterId()==Long.valueOf(userId)){
                            Boolean tag = true;
                            for (ProjectAndGroupName p:result){
                                if (p.getProject().getId()==project.getId()){
                                    tag=false;
                                    break;
                                }
                            }
                            if (tag){
                                ProjectAndGroupName projectAndGroupName =new ProjectAndGroupName();
                                projectAndGroupName.setProject(project);
                                List<String> groupNames= new ArrayList<String>();
                                List<String> groupIds=project.getGroupsIdsList();
                                for(String id:groupIds){
                                    ProjectGroup temp = projectGroupReadService.findById(Long.valueOf(id));
                                    groupNames.add(temp.getName());
                                }
                                projectAndGroupName.setGroupNames(groupNames);
                                result.add(projectAndGroupName);
                            }


                        }

                    }

                }
                response.setMessage("获取成功！");
                response.setResult(result);

            }

        }
        return response;
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Boolean> createProject(String name,HttpServletRequest httpServletRequest){
        Response<Boolean> response = new Response<Boolean>();

        HttpSession httpSession = httpServletRequest.getSession();
        BasicUser basicUser =(BasicUser)httpSession.getAttribute("loginUser@"+httpSession.getId());
        if (Arguments.isNull(basicUser)){
            response.setMessage("用户未登录！");
            return  response;
        }
        if(Arguments.isNull(name)){
            response.setMessage("项目名称不能为空！");
            return  response;
        }
        Project project = new Project();
        project.setName(name);
        project.setCreaterId(basicUser.getId());
       if(!projectWriteService.create(project)){
           response.setMessage("项目启动失败！");
           return  response;
       }
        response.setMessage("项目启动成功，请去创建团队和任务清单以便开启项目！");
        response.setResult(Boolean.TRUE);
        return  response;
    }




}
