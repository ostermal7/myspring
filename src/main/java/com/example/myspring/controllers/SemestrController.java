package com.example.myspring.controllers;

import com.example.myspring.dao.DAOManager;
import com.example.myspring.entity.Discipline;
import com.example.myspring.entity.Semestr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/semesters")
public class SemestrController {
    private final DAOManager daoManager;

    @Autowired
    public SemestrController(DAOManager daoManager) {
        this.daoManager = daoManager;
    }

    @GetMapping()
    public String semesterPage(HttpServletRequest request,
                                Model model){
        List<Semestr> listOfSems=daoManager.getAllActiveSemesters();
        model.addAttribute("listOfSems",listOfSems);
        String semesterId=request.getParameter("semestrik");
        if (semesterId==null){
            semesterId=daoManager.getAllActiveSemesters().get(0).getId()+"";
        }
        model.addAttribute("selected",Integer.parseInt(semesterId));

        List<Discipline> disciplinesOfSemester=daoManager.getDisciplinesFromSemester(Integer.parseInt(semesterId));
        model.addAttribute("disciplinesOfSemester",disciplinesOfSemester);
        return "semesters";
    }

    @PostMapping()
    public String semesterPagePost(@RequestParam("deleteSemIdHidden") String id){
        System.out.println(id);
        daoManager.deleteSemester(Integer.parseInt(id));

        return "redirect:/semesters";
    }

    @GetMapping("/newSemester")
    public String semesterCreationPage(@ModelAttribute("newSemester")Semestr semestr,
                                       Model model){
        List<Discipline> listOfAllDisciplines=daoManager.disciplineList();
        model.addAttribute("discList",listOfAllDisciplines);
        return "semester-creating";
    }

    @PostMapping("/newSemester")
    public String createSemesterPost(@ModelAttribute("newSemester") Semestr semestr,
                                     HttpServletRequest request){
        String[] discArrId=request.getParameterValues("all-disciplines[]");
        List<Semestr> newSemesterId=daoManager.gettingIdForNewSemester();
        int creatingId=(newSemesterId.get(newSemesterId.size()-1).getId()+1);
        daoManager.createNewSemester(semestr.getDuration(),creatingId);
        for (int i = 0; i <discArrId.length; i++) {
                daoManager.putDisciplinesOfNewSemester(creatingId,discArrId[i]);
        }
        return "redirect:/semesters";
    }

    @GetMapping("/modifyingSemester")
    public String modifySemesterPage(@ModelAttribute("semesterForModifying")Semestr semestr,
                                     @RequestParam("modifySemesterIdHidden") String id,
                                     Model model){
        Semestr semesterForModifying=daoManager.getSemesterById(Integer.parseInt(id));
        model.addAttribute("semesterForModifying",semesterForModifying);
        List<Discipline> listOfAllDisciplines=daoManager.disciplineList();
        model.addAttribute("discList",listOfAllDisciplines);

        return "semester-modifying";
    }

    @PostMapping("/modifyingSemester")
    public String modifySem(@ModelAttribute("semesterForModifying")Semestr semestr,
                            @RequestParam("modifySemesterIdHidden") String id,
                            HttpServletRequest request){
        String[] discArrId=request.getParameterValues("all-disciplines[]");
        daoManager.deleteOldDisciplines(Integer.parseInt(id));
        for (int i = 0; i <discArrId.length ; i++) {
            daoManager.putDisciplinesOfNewSemester(Integer.parseInt(id),discArrId[i]);
        }
        daoManager.setSemesterDuration(semestr.getDuration(),Integer.parseInt(id));
        return "redirect:/semesters";
    }
}
