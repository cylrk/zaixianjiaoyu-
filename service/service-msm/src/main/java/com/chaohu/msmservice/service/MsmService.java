package com.chaohu.msmservice.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;


public interface MsmService {
    boolean sendCode(String phone, HashMap<String, String> map);
}
