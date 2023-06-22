package com.example.tp3.web;


import com.example.tp3.entities.Patient;
import com.example.tp3.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/")
    public String home(){
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "4") int size,
                        @RequestParam(defaultValue = "") String keyword) {

        Page<Patient> pagePatients = patientRepository.findByNomContains(keyword, PageRequest.of(page, size));
        model.addAttribute("listPatients", pagePatients);
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);

        return "patients";
    }

    @GetMapping("/delete")
    public String delete(Long id,
                         String keyword,
                         int page) {

        patientRepository.deleteById(id);

        return "redirect:/index?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/formPatients")
    public String formPatients(Model model) {

        model.addAttribute("patient", new Patient());

        return "formPatients";
    }

    @PostMapping("/savePatient")
    public String savePatient(@Valid Patient patient,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "formPatients";

        patientRepository.save(patient);

        return "redirect:/index?keyword=" + patient.getNom();
    }

    @GetMapping("/editPatient")
    public String editPatient(Model model, Long id) {

        Patient patient = patientRepository.findById(id).get();
        model.addAttribute("patient", patient);

        return "editPatients";
    }

}
