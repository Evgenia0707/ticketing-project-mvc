package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private final UserService userService;
    private final ProjectService projectService;

    public ProjectController(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping("/create")// project create page
    public String createProject(Model model) {
//attribute - look in the html -project create obj for form
        model.addAttribute("project", new ProjectDTO());
//list of managers assignedManager
        model.addAttribute("managers", userService.findManagers());//do we have service? create
//projects List on table
        model.addAttribute("projects", projectService.findAll());

        return "/project/create";
    }

    @PostMapping("/create")//save button and submit form
    public String insertProject(@Valid @ModelAttribute("project") ProjectDTO project, BindingResult bindingResult, Model model) {
        //        project.setProjectStatus(Status.OPEN);
        //when create project - no status - .value(html) - null exception - need add logic in service
        if (bindingResult.hasErrors()) {
            model.addAttribute("managers", userService.findManagers());
            model.addAttribute("projects", projectService.findAll());
            return "/project/create";
        }
        projectService.save(project);  //in DB - saving - we have service
        return "redirect:/project/create";
    }

    //DELETE
    @GetMapping("/delete/{projectCode}")
    public String deleteProject(@PathVariable("projectCode") String projectCode) { //from CRUDService
        projectService.deleteById(projectCode);
        return "redirect:/project/create";
    }

    @GetMapping("/complete/{projectCode}")//{projectCode} -unique
    public String completeProject(@PathVariable("projectCode") String projectCode) {
        //status should be converted to Complete - new method for that in the projectService
        projectService.complete(projectService.findById(projectCode));
        return "redirect:/project/create";
    }

    //UPDATE (copy from user)
    @GetMapping("/update/{projectCode}")
    public String editProject(@PathVariable("projectCode") String projectCode, Model model) {
        //user obj ${project}
        model.addAttribute("project", projectService.findById(projectCode));//send specific project
        //role ${project}
        model.addAttribute("managers", userService.findManagers());//find all roles from DB
        //project ${projects}
        model.addAttribute("projects", projectService.findAll());//use Service(user) findAll-impl ready   <tr th:each="user" : ${users}>
        return "/project/update";
    }

    @PostMapping("/update")//save button for update
    public String updateProject(@Valid @ModelAttribute("project") ProjectDTO project, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("managers", userService.findManagers());
            model.addAttribute("projects", projectService.findAll());
            return "/project/update";
        }
        //status should be set  before update
        projectService.update(project);
        return "redirect:/project/create";
    }

    //Project Status
    @GetMapping("/manager/project-status")
    // go left-sidebar.html <a class="nav-link" th:href="@{/project/manager/project-status}">Project Status</a>
    public String getProjectByManager(Model model) {
        UserDTO manager = userService.findById("john@cydeo.com");
        //check view - what need like attribute (projects)
        List<ProjectDTO> projects = projectService.getCountedListOfProjectDTO(manager);
        model.addAttribute("projects", projects);
        return "/manager/project-status";
    }

}
