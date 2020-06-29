package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.ynunicom.dd.contract.dingdingcontractrebuild.service.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: jinye.Bai
 * @date: 2020/6/28 16:53
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    FileService fileService;

    @GetMapping("/doc")
    public void  getDoc(@RequestParam("accessToken")String accessToken, @RequestParam("fileName")String fileName, @RequestParam("userId")String userId,HttpServletResponse httpServletResponse){
        httpServletResponse = fileService.getDoc(accessToken,fileName,httpServletResponse,userId);
    }

    @GetMapping("/pdf")
    public void getPdf(@RequestParam("accessToken")String accessToken, @RequestParam("fileName")String fileName, @RequestParam("userId")String userId,HttpServletResponse httpServletResponse){
        httpServletResponse = fileService.getPdf(accessToken,fileName,httpServletResponse,userId);
    }

    @GetMapping("/other")
    public void other(@RequestParam("accessToken")String accessToken, @RequestParam("fileName")String fileName, @RequestParam("userId")String userId,HttpServletResponse httpServletResponse){
        httpServletResponse = fileService.getOther(accessToken,fileName,httpServletResponse,userId);
    }

}
