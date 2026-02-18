package org.example.wedevelop.manager;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.wedevelop.config.CosClientConfig;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * COS 对象存储管理器
 */
@Component
@Slf4j
public class CosManager {

    @Resource
    private CosClientConfig cosClientConfig;

    @Resource
    private COSClient cosClient;

    /**
     * 上传对象
     *
     * @param key
     * @param file
     * @return
     */
    public PutObjectResult putObjectResult(String key, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), key, file);
        return cosClient.putObject(putObjectRequest);
    }

    /**
     * 上传文件到 COS 并返回访问 URL
     *
     * @param key
     * @param file
     * @return
     */
    public String uploadFile(String key, File file) {
        // 上传文件
        PutObjectResult result = putObjectResult(key, file);
        if (result != null) {
            // 构建访问 URL
            String url = String.format("%s%s", cosClientConfig.getHost(), key);
            log.info("文件上传 COS 成功：{} -> {}", file.getName(), url);
            return url;
        } else {
            log.error("文件上传 COS 失败，返回结果为空");
            return null;
        }
    }
}
