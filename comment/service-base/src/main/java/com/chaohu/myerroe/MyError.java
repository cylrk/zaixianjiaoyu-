package com.chaohu.myerroe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyError extends RuntimeException {
    private Integer code;
    private String  mgs;
}
