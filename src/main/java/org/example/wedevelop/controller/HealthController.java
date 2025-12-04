package org.example.wedevelop.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.wedevelop.common.BaseResponse;
import org.example.wedevelop.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "健康检查", description = "系统健康状态检查接口")
@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping("/")
    public BaseResponse<String> healthCheck() {
        return ResultUtils.success( "ok");
    }
}