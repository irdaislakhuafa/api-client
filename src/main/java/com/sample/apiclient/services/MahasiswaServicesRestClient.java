package com.sample.apiclient.services;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.apiclient.helpers.ResponseContainer;
import com.sample.apiclient.model.dto.Mahasiswa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MahasiswaServicesRestClient {
    // import object RestTemplate
    @Autowired
    private RestTemplate restTemplate;

    // import object ObjectMapper
    @Autowired
    private ObjectMapper mapper = new ObjectMapper(); // untuk menconvert object

    // menyiapkan url api
    private static final String apiUrl = "http://localhost:8080/api/mahasiswa";

    // READ
    public List<Mahasiswa> findAll() {
        try {
            // GET all data from API
            // ada 2 parameter (urlApi, nanti response JSON nya di convert ke class/object
            // dari class apa)
            ResponseContainer responseApi = restTemplate.getForObject(apiUrl + "/getall",
                    ResponseContainer.class);

            // field "data" dari object responseApi saya convert ke ArrayList of object
            // Mahasiswa
            List<Mahasiswa> listMahasiswa = mapper.convertValue(
                    responseApi.getData(), new TypeReference<List<Mahasiswa>>() {

                    });
            return listMahasiswa; // mengembalikan object listMahasiswa
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // READ
    public Mahasiswa findById(Long id) {
        try {
            // FindById from API
            // ada 1 parameter (url api nya, nanti response Api ini disimpan dalam bentuk
            // object dari class apa)
            ResponseContainer responseApi = restTemplate.getForObject(apiUrl + "/find/byid/" + id,
                    ResponseContainer.class);

            // mengkonversi dari "Object" data dari responseApi ke object class Mahasiswa
            Mahasiswa existsMahasiswa = mapper.convertValue(
                    responseApi.getData(), new TypeReference<Mahasiswa>() {

                    });

            return existsMahasiswa;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

    // READ
    public Mahasiswa findByNpm(String npm) {
        try {
            // FindByNpm from API
            // ada 1 parameter (url api nya, nanti response Api ini disimpan dalam bentuk
            // object dari class apa)
            ResponseContainer responseApi = restTemplate.getForObject(apiUrl + "/find/bynpm/" + npm,
                    ResponseContainer.class);

            // mengkonversi dari "Object" data dari responseApi ke object class Mahasiswa
            Mahasiswa existsMahasiswa = mapper.convertValue(
                    responseApi.getData(), new TypeReference<Mahasiswa>() {
                    });

            return existsMahasiswa;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

    // CREATE
    public ResponseContainer save(Mahasiswa mahasiswa) {
        // parameter (url apinya, object yang dikirim apa, tipe response JSON nanti
        // diconvert ke object class apa )
        ResponseContainer responseApi = restTemplate.postForObject(apiUrl + "/save", mahasiswa,
                ResponseContainer.class);
        return responseApi;
    }

    // UPDATE
    public ResponseContainer update(Mahasiswa mahasiswa) {
        // disini saya menggunakan POST bukan PUT
        // mirip kyk save() di atas
        ResponseContainer responseApi = restTemplate.postForObject(apiUrl + "/update", mahasiswa,
                ResponseContainer.class);
        return responseApi;
    }

    // DELETE
    public void deleteById(Long id) {
        // disini saya menggunakan method DELETE
        // parameternya 1 (url apinya)
        restTemplate.delete(apiUrl + "/delete/" + id);
    }

}
