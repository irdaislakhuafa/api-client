package com.sample.apiclient.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mahasiswa {
    private Long id;
    private String npm;
    private String nama;
    private String jk;
    private String angkatan;
    private Alamat alamat;
}
