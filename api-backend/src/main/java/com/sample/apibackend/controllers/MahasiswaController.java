package com.sample.apibackend.controllers;

import java.util.ArrayList;
import java.util.List;

import com.sample.apibackend.helpers.ResponseContainer;
import com.sample.apibackend.model.entities.MahasiswaEntity;
import com.sample.apibackend.model.repositories.MahasiswaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mahasiswa")
public class MahasiswaController {
    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @GetMapping("/getall")
    public ResponseEntity<?> getAll() {
        try {
            List<MahasiswaEntity> listMahasiswa = new ArrayList<MahasiswaEntity>();

            mahasiswaRepository.findAll().forEach(listMahasiswa::add);

            if (listMahasiswa.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(
                        new ResponseContainer(
                                HttpStatus.OK, "success", listMahasiswa),
                        HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MahasiswaEntity mahasiswa) {
        try {
            MahasiswaEntity savedMahasiswa = mahasiswaRepository.save(mahasiswa);
            return new ResponseEntity<>(new ResponseContainer(
                    HttpStatus.CREATED, "success",
                    savedMahasiswa),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseContainer(
                    HttpStatus.EXPECTATION_FAILED, "failed to save : " + mahasiswa, null),
                    HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/save/all")
    public ResponseEntity<?> saveAll(@RequestBody List<MahasiswaEntity> mahasiswas) {
        try {
            List<MahasiswaEntity> savedMahasiswa = mahasiswaRepository.saveAll(mahasiswas);
            return new ResponseEntity<>(new ResponseContainer(
                    HttpStatus.CREATED, "success",
                    savedMahasiswa),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseContainer(
                    HttpStatus.EXPECTATION_FAILED, "failed to save", null),
                    HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody MahasiswaEntity mahasiswa) {
        try {
            if (mahasiswaRepository.existsByNpm(mahasiswa.getNpm())) {
                MahasiswaEntity updatedMahasiswa = mahasiswaRepository.save(mahasiswa);

                return new ResponseEntity<>(new ResponseContainer(
                        HttpStatus.OK, "success", updatedMahasiswa), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new ResponseContainer(
                                HttpStatus.NOT_FOUND, "mahasiswa tih NPM : " + mahasiswa.getNpm() + " not found", null),
                        HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(
                    new ResponseContainer(
                            HttpStatus.EXPECTATION_FAILED, "error : " + e.getLocalizedMessage(), null),
                    HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            MahasiswaEntity deletedMahasiswa = mahasiswaRepository.findById(id).get();
            mahasiswaRepository.deleteById(id);
            return new ResponseEntity<>(
                    new ResponseContainer(HttpStatus.OK, "success deleted", deletedMahasiswa), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ResponseContainer(HttpStatus.EXPECTATION_FAILED, "failed " + e.getMessage(), null),
                    HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/find/byid/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        try {
            MahasiswaEntity mahasiswa = mahasiswaRepository.findById(id).get();

            if (mahasiswaRepository.existsById(id)) {
                return new ResponseEntity<>(
                        new ResponseContainer(
                                HttpStatus.OK, "success", mahasiswa),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new ResponseContainer(
                                HttpStatus.NOT_FOUND, "data with ID : " + id + " not found", null),
                        HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return new ResponseEntity<>(
                    new ResponseContainer(
                            HttpStatus.EXPECTATION_FAILED, "error : " + e.getLocalizedMessage(), null),
                    HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/find/bynpm/{npm}")
    public ResponseEntity<?> getByNpm(@PathVariable("npm") String npm) {
        try {
            MahasiswaEntity mahasiswa = mahasiswaRepository.findByNpm(npm);

            if (mahasiswaRepository.existsByNpm(npm)) {
                return new ResponseEntity<>(
                        new ResponseContainer(
                                HttpStatus.OK, "success", mahasiswa),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new ResponseContainer(
                                HttpStatus.NOT_FOUND, "data with NPM : " + npm + " not found", null),
                        HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return new ResponseEntity<>(
                    new ResponseContainer(
                            HttpStatus.EXPECTATION_FAILED, "error : " + e.getLocalizedMessage(), null),
                    HttpStatus.EXPECTATION_FAILED);
        }
    }

}
