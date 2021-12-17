package com.sample.apiclient.controllers;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.apiclient.model.dto.Mahasiswa;
import com.sample.apiclient.services.MahasiswaServicesRestClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

@Controller
@RequestMapping("/apiclient/mahasiswa")
public class MahasiswaController {

    @Autowired
    private MahasiswaServicesRestClient mahasiswaServicesRestClient;

    @GetMapping
    public String index(Model model) {
        List<Mahasiswa> listMahasiswa = new ArrayList<>();

        try {
            listMahasiswa = mahasiswaServicesRestClient.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("title", "Demo Api Client");
        model.addAttribute("listMahasiswa", listMahasiswa);
        return "index";
    }

    @GetMapping("/add")
    public String formAddMahasiswa(Model model) {
        model.addAttribute("title", "Add Mahasiswa");
        model.addAttribute("mahasiswa", new Mahasiswa());
        model.addAttribute("laki", "L");
        model.addAttribute("perempuan", "P");
        return "formAddMahasiswa";
    }

    @PostMapping("/save")
    public String saveMahasiswa(Mahasiswa mahasiswa, Model model) {
        try {
            Mahasiswa savedMahasiswa = new ObjectMapper().convertValue(
                    mahasiswaServicesRestClient.save(mahasiswa).getData(), new TypeReference<Mahasiswa>() {

                    });
            System.out.println("Success saved : " + savedMahasiswa);
            return "redirect:/apiclient/mahasiswa";
        } catch (HttpClientErrorException e) {
            // TODO: handle exception
            e.printStackTrace();
            model.addAttribute("errorCode", e.getRawStatusCode());
            model.addAttribute("message", "Data with NPM : " + mahasiswa.getNpm() + " already exists!");
            return "error-page";
        }
    }

    @GetMapping("/edit/{npm}")
    public String edit(@PathVariable("npm") String npm, Model model) {
        Mahasiswa mahasiswa = mahasiswaServicesRestClient.findByNpm(npm);
        model.addAttribute("title", "Edit Mahasiswa");
        model.addAttribute("mahasiswa", mahasiswa);
        model.addAttribute("laki", "L");
        model.addAttribute("perempuan", "P");
        return "formEditMahasiswa";
    }

    @PostMapping("/update")
    public String update(Mahasiswa mahasiswa, Model model) {
        try {
            mahasiswaServicesRestClient.update(mahasiswa);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return "redirect:/apiclient/mahasiswa";

    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        mahasiswaServicesRestClient.deleteById(id);
        return "redirect:/apiclient/mahasiswa";
    }

}
