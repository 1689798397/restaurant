package com.example.controller;

import com.example.common.minio.Uploader;
import com.example.common.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件上传接口")
@RestController
public class UploadController {
    @Autowired
    private Uploader uploader;

    @ApiOperation("文件上传")
    @ApiImplicitParam(name = "file", value = "文件", dataType = "MultipartFile", required = true)
    @PostMapping("/upload")
    public ResultVO upload2(@RequestParam("file") MultipartFile file) throws Exception {
        return ResultVO.ok(uploader.uploadFile(file));
    }
}
