package com.example.myspring.controllers;

import com.example.myspring.dao.DAOManager;
import com.example.myspring.entity.Discipline;
import com.example.myspring.entity.Semestr;
import com.example.myspring.entity.Student;
import com.example.myspring.entity.StudentDiscAndMark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentsController {
    private final DAOManager daoManager;

    @Autowired
    public StudentsController(DAOManager daoManager) {
        this.daoManager = daoManager;
    }

    @GetMapping()
    public String studentsPage(Model model){
        model.addAttribute("allStudents", daoManager.studentsList());
        return "/students";
    }

    @GetMapping("/student-creating")
    public String studentCreate(@ModelAttribute("student")Student student){
        return "student-creating";
    }

    @PostMapping("/student-creating")
    public String creating(@ModelAttribute("student") Student student){
        daoManager.createStudent(student);
        return "redirect:/students";
    }

    @PostMapping()
    public String deleteStudents(@RequestParam("deleteStudentHidden") String allId){
        String[] idsStr=allId.split(",");
        for (String id: idsStr){

            daoManager.deleteStudents(Integer.parseInt(id));
        }
        return "redirect:/students";
    }

    @GetMapping("/student-modifying")
    public String modifyStudentPage(@ModelAttribute("modStudent") Student modStudent,
                                    @RequestParam("modifyStudentHidden") String modifyingStudentId,
                                    Model model
    ){
        Student modStud=daoManager.getStudentById(Integer.parseInt(modifyingStudentId));
        model.addAttribute("studentForModifying",modStud);

        return "student-modifying";
    }

    @PostMapping("/student-modifying")
    public String modifyStudent(@ModelAttribute("modStudent") Student modStudent,
                                @RequestParam("idStudent") String id){
        daoManager.changeStudent(modStudent,Integer.parseInt(id));

        return "redirect:/students";
    }

    @GetMapping("/student-progress")
    public String studentProgressPage(@RequestParam("progressStudentHidden") String progStudentId,
                                      HttpServletRequest request,
                                      Model model){
        Student progressStudentInfo=daoManager.getStudentById(Integer.parseInt(progStudentId));
        model.addAttribute("progressStudent",progressStudentInfo);

        List<Semestr> allSems=daoManager.getAllActiveSemesters();
        model.addAttribute("allSems",allSems);
        String semId=request.getParameter("semesterForId");
        if (semId==null){
            semId=allSems.get(0).getId()+"";
        }
        model.addAttribute("selected",Integer.parseInt(semId));
        List<StudentDiscAndMark> studDiscAndMarks=daoManager.getDisciplinesAndMarksFromSemester(Integer.parseInt(semId),Integer.parseInt(progStudentId));
        model.addAttribute("studDiscAndMarks",studDiscAndMarks);

        return "student-progress";

    }

    @PostMapping("/student-progress")
    public String studentProgressPagePost(@RequestParam("chooseStudIdHidden") String id,
                                          @RequestParam("semesterForId") String semId,
                                          Model model){
        Student studForProg=daoManager.getStudentById(Integer.parseInt(id));
        model.addAttribute("progressStudent",studForProg);
        model.addAttribute("selected",Integer.parseInt(semId));
        List<StudentDiscAndMark> studDiscAndMarks=daoManager.getDisciplinesAndMarksFromSemester(Integer.parseInt(semId),Integer.parseInt(semId));
        model.addAttribute("studDiscAndMarks",studDiscAndMarks);
        List<Semestr> allSems=daoManager.getAllActiveSemesters();
        model.addAttribute("allSems",allSems);
        return "student-progress";
    }
}
