package com.zgsy.bs.web.controller.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zgsy.bs.common.model.Response;
import com.zgsy.bs.common.utils.Arguments;
import com.zgsy.bs.user.dto.ProjectTaskAndStatus;
import com.zgsy.bs.user.dto.ProjectTaskDetail;
import com.zgsy.bs.user.dto.ProjectTaskModel;
import com.zgsy.bs.user.dto.TaskUser;
import com.zgsy.bs.user.enums.TaskStatus;
import com.zgsy.bs.user.model.BasicUser;
import com.zgsy.bs.user.model.Project;
import com.zgsy.bs.user.model.ProjectTask;
import com.zgsy.bs.user.service.BasicUserReadService;
import com.zgsy.bs.user.service.ProjectReadService;
import com.zgsy.bs.user.service.ProjectTaskReadService;
import com.zgsy.bs.user.service.ProjectTaskWriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Java on 2017/1/14
 */
@RestController
@Slf4j
@RequestMapping("/api/web/task")
@CrossOrigin

public class ProjectTasks {


    @Reference(version = "1.0.0")
    public ProjectTaskWriteService projectTaskWriteService;
    @Reference(version = "1.0.0")
    public ProjectReadService projectReadService;

    @Reference(version = "1.0.0")
    public ProjectTaskReadService projectTaskReadeService;

    @Reference(version = "1.0.0")
    public BasicUserReadService basicUserReadService;

    /**
     * 新建任务模块
     *
     * @param projectTask
     * @return
     */

    @RequestMapping(value = "/create-model", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Boolean> createTask(ProjectTask projectTask, HttpServletRequest httpServletRequest) {
        projectTask.setStatus(TaskStatus.TASK_NORMAL.index());
        projectTask.setParentId(-1l);
        Response<Boolean> response = new Response<Boolean>();
        HttpSession httpSession = httpServletRequest.getSession();
        BasicUser basicUser = (BasicUser) httpSession.getAttribute("loginUser@" + httpSession.getId());
        if (basicUser != null) {
            Map<String, String> maps = new HashMap<String, String>();
            maps.put("createrId", String.valueOf(basicUser.getId()));
            maps.put("createrName", basicUser.getName());
            projectTask.setExtras(maps);
        } else {
            response.setMessage("用户未登录！");
            response.setResult(Boolean.FALSE);
            return response;

        }


        List<Project> projects = projectReadService.findByCreaterId(basicUser.getId());
        projectTask.setProjectId(projects.get(projectTask.getProjectId().intValue()-1).getId());

        if (!projectTaskWriteService.createProjectTask(projectTask)) {
            log.error("create.project.task.failed");
            response.setMessage("创建任务失败！");
            response.setResult(Boolean.FALSE);
            return response;
        }
        response.setMessage("创建任务模块成功！");
        response.setResult(Boolean.TRUE);
        return response;

    }


    //获取该项目下所有的模块
    @RequestMapping(value = "/find-project-models",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<ProjectTask>> findProjectModels(Long projectId,HttpServletRequest httpServletRequest){

        Response<List<ProjectTask>> response =new Response<List<ProjectTask>>();
        HttpSession httpSession = httpServletRequest.getSession();
        BasicUser basicUser =(BasicUser)httpSession.getAttribute("loginUser@"+httpSession.getId());
        if (basicUser==null){
            response.setMessage("用户未登录！");
            return response;

        }
        List<Project> projects = projectReadService.findByCreaterId(basicUser.getId());
        projectId = projects.get(projectId.intValue()-1).getId();
        List<ProjectTask> list = projectTaskReadeService.findProjectModels(projectId);
        response.setResult(list);
        response.setMessage("获取项目模块成功！");
        return  response;

    }

    //获取该项目模块的详细信息
    @RequestMapping(value = "/find-model-info",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<ProjectTaskModel> findProjectTaskModelInfo(Long modelId){
        Response<ProjectTaskModel> response =new Response<ProjectTaskModel>();
        ProjectTaskModel projectTaskModel =new ProjectTaskModel();
        ProjectTask projectTask = projectTaskReadeService.findById(modelId);
        List<ProjectTask> list = projectTaskReadeService.findChildTasks(modelId);
        projectTaskModel.setName(projectTask.getName());
        projectTaskModel.setDescMessage(projectTask.getDescMessage());
        projectTaskModel.setCreaterId(Long.valueOf(projectTask.getExtras().get("createrId")));
        projectTaskModel.setCreaterName(projectTask.getExtras().get("createrName"));
        projectTaskModel.setProjectId(projectTask.getProjectId());
        projectTaskModel.setProjectTasks(list);
        projectTaskModel.setCreatedDate(projectTask.getCreatedAt());
        response.setResult(projectTaskModel);
        response.setMessage("获取成功！");
        return response;

    }



    /**
     * 获取该模块下所有子任务
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/find-child-tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<ProjectTask>> findChildTasks(Long parentId) {

        Response<List<ProjectTask>> response = new Response<List<ProjectTask>>();
        List<ProjectTask> list = projectTaskReadeService.findChildTasks(parentId);
        response.setResult(list);
        response.setMessage("获取子任务成功！");
        return response;


    }


    /**
     * 新建子任务
     *
     * @param projectTask
     * @return
     */

    @RequestMapping(value = "/create-child", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Boolean> createChildTask(@RequestBody ProjectTask projectTask, HttpServletRequest httpServletRequest) {
        projectTask.setStatus(TaskStatus.TASK_NORMAL.index());
        Response<Boolean> response = new Response<Boolean>();
        HttpSession httpSession = httpServletRequest.getSession();
        BasicUser basicUser = (BasicUser) httpSession.getAttribute("loginUser@" + httpSession.getId());
        if (basicUser != null) {
            Map<String, String> maps = new HashMap<String, String>();
            ProjectTask parentTask = projectTaskReadeService.findById(projectTask.getParentId());
            maps.put("parentName",parentTask.getName());
            maps.put("createrName", basicUser.getName());
            maps.put("createrId", String.valueOf(basicUser.getId()));
            projectTask.setExtras(maps);
            Long projectId = projectTask.getProjectId();
            List<Project> projects = projectReadService.findByCreaterId(basicUser.getId());
            if (projects.size()>0){
                projectId = projects.get(projectId.intValue()-1).getId();
            }
            projectTask.setProjectId(projectId);


        } else {
            response.setMessage("用户未登录！");
            response.setResult(Boolean.FALSE);
            return response;

        }
        if (!projectTaskWriteService.createProjectTask(projectTask)) {
            response.setMessage("创建任务失败！");
            log.error("create.project.task.failed");
            response.setResult(Boolean.FALSE);
            return response;
        }
        response.setResult(Boolean.TRUE);
        response.setMessage("创建成功！");
        return response;

    }


    //获取任务详细信息
    @RequestMapping(value = "/find-by-id",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<ProjectTaskAndStatus> findTaskById(Long id){
        Response<ProjectTaskAndStatus> response =new Response<ProjectTaskAndStatus>();
        ProjectTask projectTask = projectTaskReadeService.findById(id);
        ProjectTaskAndStatus projectTaskAndStatus = new ProjectTaskAndStatus();
        projectTaskAndStatus.setProjectTask(projectTask);
        projectTaskAndStatus.setStatusDesc(TaskStatus.getDescByIndex(projectTask.getStatus()));
        if(projectTask==null){
            response.setMessage("不存在对应任务！");
            response.setResult(projectTaskAndStatus);
        }
        response.setResult(projectTaskAndStatus);
        response.setMessage("获取任务成功！");
        return response;

    }


    //获取任务负责人及可添加成员
    @RequestMapping(value = "/find-users",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<TaskUser> findTaskUsers(Long id){
        Response<TaskUser> response =new Response<TaskUser>();
        TaskUser taskUser = new TaskUser();

        if(id==null){
            List<BasicUser> canAddUsers = basicUserReadService.findAll();
            taskUser.setCanAddUsers(canAddUsers);
            response.setResult(taskUser);
            response.setMessage("获取用户！");
            return response;
        }
        ProjectTask projectTask = projectTaskReadeService.findById(id);
        List<BasicUser> users =new ArrayList<BasicUser>();
        List<String> list = projectTask.getUserIdsList();
        List<BasicUser> canAddUsers = basicUserReadService.findAll();
        if(list!=null){
            for (int i=0;i<canAddUsers.size();i++){
                if (list.contains(String.valueOf(canAddUsers.get(i).getId()))){
                    users.add(canAddUsers.get(i));
                    canAddUsers.remove(i);
                    i--;
                }
            }
        }

        taskUser.setUsers(users);
        taskUser.setCanAddUsers(canAddUsers);
        response.setResult(taskUser);
        response.setMessage("获取用户！");
        return response;

    }


    //编辑任务
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response<Boolean> updateTask(@RequestBody ProjectTask projectTask) {
        Response<Boolean> response = new Response<Boolean>();
        if (!projectTaskWriteService.updateProjectTask(projectTask)) {
            log.error("update.project.task.failed");
            response.setMessage("编辑任务失败！");
            response.setResult(Boolean.FALSE);
            return response;
        }
        response.setResult(Boolean.TRUE);
        response.setMessage("编辑成功！");
        return response;
    }

    //此删除为逻辑删除子任务
    @RequestMapping(value = "/delete-child", method = RequestMethod.POST)
    public Response<Boolean> deleteChildTask(Long taskId) {
        Response<Boolean> response = new Response<Boolean>();
        ProjectTask projectTask = projectTaskReadeService.findById(taskId);
        if (Arguments.isNull(projectTask)) {
            log.error("delete.project.task.failed");
            response.setMessage("删除任务失败,不存在对应任务！");
            response.setResult(Boolean.FALSE);
            return response;

        }
        projectTask.setStatus(TaskStatus.TASK_DELETED.index());
        projectTaskWriteService.updateProjectTask(projectTask);
        response.setMessage("已逻辑删除该子任务");
        response.setResult(Boolean.TRUE);
        return response;
    }

    //此删除为删除任务模块
    @RequestMapping(value = "/delete-model", method = RequestMethod.POST)
    public Response<Boolean> deleteProjectTask(Long taskId) {
        Response<Boolean> response = new Response<Boolean>();
        ProjectTask projectTask = projectTaskReadeService.findById(taskId);
        if (Arguments.isNull(projectTask)) {
            response.setMessage("删除任务失败,不存在对应任务！");
            response.setResult(Boolean.FALSE);
            return response;

        }

        projectTaskWriteService.deleteProjectTask(taskId);
        List<ProjectTask> list = projectTaskReadeService.findChildTasks(taskId);
        for (ProjectTask task:list){
            task.setStatus(TaskStatus.TASK_DELETED.index());
            projectTaskWriteService.updateProjectTask(task);
        }
        response.setMessage("删除模块成功并且该模块下所有子任务已逻辑删除！");
        response.setResult(Boolean.TRUE);
        return response;
    }

    //获取某个项目某个状态的任务
    @RequestMapping(value = "/find-by-status",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<ProjectTask>> findChildTaskByParentIdAndStatus(Long parentId,Integer status,HttpServletRequest httpServletRequest){
        Response<List<ProjectTask>>  response =new Response<List<ProjectTask>>();

        HttpSession httpSession = httpServletRequest.getSession();
        BasicUser basicUser =(BasicUser)httpSession.getAttribute("loginUser@"+httpSession.getId());
        if (basicUser==null){
            response.setMessage("用户未登录！");
            return response;

        }
        List<ProjectTask> projectTasks = projectTaskReadeService.findAll();
        List<ProjectTask> projectTaskList = new ArrayList<ProjectTask>();
        if (projectTasks.size()>0){
            for (ProjectTask p : projectTasks){
                if (p.getStatus() ==status&&p.getParentId()!=-1&&p.getUserIdsList()!=null&&p.getUserIdsList().contains(basicUser.getId().toString())){
                    projectTaskList.add(p);
                }
            }
            response.setMessage("获取任务成功！");
            response.setResult(projectTaskList);
        }


        return  response;

    }







    //获得某个用户的任务
    @RequestMapping(value = "/find/by/user-id", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<ProjectTask>> findByUserId(Long userId) {

        Response<List<ProjectTask>> response = new Response<List<ProjectTask>>();
        List<ProjectTask> list = projectTaskReadeService.findByUserId(userId);
        response.setResult(list);
        return response;

    }

    //获得某个团队的任务
    @RequestMapping(value = "/find/by/group-id", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<ProjectTask>> findByGroupId(Long groupId) {

        Response<List<ProjectTask>> response = new Response<List<ProjectTask>>();
        List<ProjectTask> list = projectTaskReadeService.findByGroupId(groupId);
        response.setResult(list);
        return response;

    }


    //我的任务
    @RequestMapping(value = "/my-task",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<ProjectTaskDetail>> findMyTask(HttpServletRequest httpServletRequest){
        Response<List<ProjectTaskDetail>> response = new Response<List<ProjectTaskDetail>>();
        HttpSession httpSession = httpServletRequest.getSession();
        BasicUser basicUser =(BasicUser)httpSession.getAttribute("loginUser@"+httpSession.getId());
        if (basicUser==null){
            response.setMessage("用户未登录！");
            return response;

        }else {
            String userId= String.valueOf(basicUser.getId());
            List<ProjectTaskDetail> result = new ArrayList<ProjectTaskDetail>();
            List<ProjectTask> all = projectTaskReadeService.findAll();
            for (ProjectTask projectTask :all){
                if (projectTask.getUserIdsList()!=null&&projectTask.getUserIdsList().contains(userId)&&projectTask.getParentId()!=-1){
                    ProjectTaskDetail projectTaskDetail = new ProjectTaskDetail();
                    projectTaskDetail.setProjectTask(projectTask);
                    Long projectId = projectTask.getProjectId();
                    projectTaskDetail.setProjectName(projectReadService.findById(projectId).getName());

                    List<String> userIds = projectTask.getUserIdsList();
                    List<String> userNames = new ArrayList<String>();
                    for(String id : userIds){
                        BasicUser user  =  basicUserReadService.findBasicUserById(Long.valueOf(id));
                        userNames.add(user.getName());
                    }
                    projectTaskDetail.setUserNames(userNames);
                    projectTaskDetail.setStatus(TaskStatus.getDescByIndex(projectTask.getStatus()));
                    result.add(projectTaskDetail);
                }

            }
            response.setResult(result);
            response.setMessage("获取成功！");
            return response;


        }

    }


    @RequestMapping(value = "/finish",method = RequestMethod.GET)
    public  Response<Boolean> finishTask(Long taskId){
        Response<Boolean> response =new Response<Boolean>();

        ProjectTask projectTask =projectTaskReadeService.findById(taskId);
        Map<String,String> map =  projectTask.getExtras();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("finishDate",sdf.format(new Date()));
        projectTask.setExtras(map);
        if (projectTask.getStatus()==TaskStatus.TASK_NORMAL.index()){
            projectTask.setStatus(TaskStatus.TASK_FINISH.index());
            if(!projectTaskWriteService.updateProjectTask(projectTask)){
                response.setMessage("任务完成状态更新失败！");
                return response;
            }
            response.setMessage("任务标记为完成成功！");
            return response;
        }
        response.setMessage("只有处于正在进行中的任务才可以更新完成状态！");
        return response;
    }




}
