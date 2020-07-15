package com.ynunicom.dd.contract.dingdingcontractrebuild.controller;

import com.ynunicom.dd.contract.dingdingcontractrebuild.dto.ResponseDto;
import com.ynunicom.dd.contract.dingdingcontractrebuild.service.FileService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/del")
    public ResponseDto delFile(@RequestParam("accessToken")String accessToken, @RequestParam("fileName")String fileName, @RequestParam("userId")String userId,@RequestParam("contractId")String contractId,
                               @RequestParam("taskId")String taskId){
        fileService.del(accessToken,fileName,userId,contractId,taskId);
        return  ResponseDto.success("删除附件成功");
    }

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

    @PostMapping("/push")
    public ResponseDto push(@RequestParam("accessToken")String accessToken, String mediaId, String fileName,@RequestParam("userId")String userId,@RequestParam("recvUserId")String recvUserId){
        if (fileService.push(accessToken,mediaId,fileName,recvUserId)){
            return ResponseDto.success("推送成功");
        }
        return ResponseDto.failed("推送失败");
    }

}
