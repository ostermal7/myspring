package com.example.myspring.controllers;

import com.example.myspring.dao.DAOManager;
import com.example.myspring.entity.Discipline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/disciplines")
public class DisciplineController {
    private final DAOManager daoManager;

    @Autowired
    public DisciplineController(DAOManager daoManager) {
        this.daoManager = daoManager;
    }

    @GetMapping()
    public String getDisciplinesPage(Model model){
        model.addAttribute("allDisciplines", daoManager.disciplineList());
        return "/disciplines";
    }

    @PostMapping()
    public String deleteDisc(@RequestParam("deleteDiscHidden") String ids){
        String[] idsStr=ids.split(",");
            for (String id: idsStr){
                daoManager.deleteDiscipline(Integer.parseInt(id));
            }


        return "redirect:/disciplines";
    }
    @GetMapping("/newDiscipline")
    public String NewDiscipline(@ModelAttribute("discipline") Discipline discipline){

        return "discipline-creating";
    }

    @PostMapping("/newDiscipline")
    public String createDiscipline(@ModelAttribute("discipline") Discipline discipline){
        daoManager.save(discipline);
        return "redirect:/disciplines";
    }

    @GetMapping("/discipline-modifying")
    public String modifyingPage(@ModelAttribute("modDiscipline") Discipline modDiscipline,
                                @RequestParam("modifyingDiscHidden") String modifyingDisciplineId,
                                Model model
    ){
        Discipline modDisc=daoManager.getDisciplineById(Integer.parseInt(modifyingDisciplineId));
        model.addAttribute("discForModifying",modDisc);

        return "discipline-modifying";
    }

    @PostMapping("/discipline-modifying")
    public String modify(@ModelAttribute("modDiscipline") Discipline modDiscipline,
                         @RequestParam("idDisc") String modifiedDisciplineId
                         ){
        daoManager.changeName(modDiscipline,Integer.parseInt(modifiedDisciplineId));
        return "redirect:/disciplines";
    }


}
