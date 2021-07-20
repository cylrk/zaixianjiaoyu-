package com.chaohu.service.serviceTea.entity.subject;

import lombok.Data;

import java.util.ArrayList;

@Data
public class OneSubject {

    private String id;
    private String title;
    private ArrayList<TwoSubject> children = new ArrayList<>();
}
