package com.sample.apiclient.controllers;

import java.lang.ref.Cleaner;

import com.sample.apiclient.model.dto.Mahasiswa;
import com.sample.apiclient.services.MahasiswaServicesRestClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/apiclient/mahasiswa")
public class MahasiswaController {

    @Autowired
    private MahasiswaServicesRestClient mahasiswaServicesRestClient;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("title", "Demo Api Client");
        model.addAttribute("listMahasiswa", mahasiswaServicesRestClient.findAll());
        return "index";
    }

    @GetMapping("/add")
    public String formAddMahasiswa(Model model) {
        model.addAttribute("title", "Add Mahasiswa");
        model.addAttribute("mahasiswa", new Mahasiswa());
        return "formAddMahasiswa";
    }

    @PostMapping("/save")
    public String saveMahasiswa(Mahasiswa mahasiswa, Model model) {
        mahasiswa.setJk(
                (mahasiswa.getJk().contains("l") || mahasiswa.getJk().equalsIgnoreCase("pria")) ? "L" : "P");
        mahasiswaServicesRestClient.save(mahasiswa);
        return "redirect:/apiclient/mahasiswa";
    }

    @GetMapping("/edit/{npm}")
    public String edit(@PathVariable("npm") String npm, Model model) {
        // System.out.println(mahasiswaServicesRestClient.findBynpm(npm).getAngkatan());
        model.addAttribute("title", "Edit Mahasiswa");
        model.addAttribute("mahasiswa", mahasiswaServicesRestClient.findByNpm(npm));
        return "formEditMahasiswa";
    }

    @PostMapping("/update")
    public String update(Mahasiswa mahasiswa, Model model) {
        try {
            Mahasiswa mahasiswaIsExists = mahasiswaServicesRestClient.findByNpm(mahasiswa.getNpm());
            if (mahasiswaIsExists != null) {
                mahasiswaServicesRestClient.update(mahasiswa);
            }
            return "redirect:/apiclient/mahasiswa";
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return "redirect:/apiclient/mahasiswa";
        }

    }

}
