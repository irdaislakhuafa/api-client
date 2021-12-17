package com.sample.apibackend.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseContainer {
    private Object status;
    private Object message;
    private Object data;
}
