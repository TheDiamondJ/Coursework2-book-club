package com.example.backend.service;

import com.example.backend.common.entity.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


@Service
public interface UploadService {
    Result upload(MultipartFile file, HttpServletRequest request);

}
