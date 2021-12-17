package com.sample.apiclient.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseContainer {
    // tipe data Object bisa menjadi tipe data apapun (String, Integer, Boolean,
    // ArrayList, etc)
    private Object status;
    private Object message;
    private Object data;
}
