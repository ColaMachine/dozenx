package com.dozenx.web.module.storage.action;

import com.dozenx.common.util.StringUtil;
import com.dozenx.web.module.storage.bean.LitemallStorage;
import com.dozenx.web.module.storage.service.LitemallStorageService;
import com.dozenx.web.module.storage.service.StorageService;
import com.dozenx.web.util.ResultUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 对象存储服务
 */
@RestController
@RequestMapping("/wx/storage")
@Validated
public class WxStorageController {
    private final Log logger = LogFactory.getLog(WxStorageController.class);

    @Autowired
    private StorageService storageService;
    @Autowired
    private LitemallStorageService litemallStorageService;

    private String generateKey(String originalFilename) {
        int index = originalFilename.lastIndexOf('.');
        String suffix = originalFilename.substring(index);

        String key = null;
        LitemallStorage storageInfo = null;

        do {
            key = StringUtil.getRandomString(20) + suffix;
            storageInfo = litemallStorageService.findByKey(key);
        }
        while (storageInfo != null);

        return key;
    }

    @PostMapping("/upload")
    public Object upload(@RequestParam("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        LitemallStorage litemallStorage = storageService.store(file.getInputStream(), file.getSize(), file.getContentType(), originalFilename);
        return ResultUtil.ok(litemallStorage);
    }

    /**
     * 访问存储对象
     *
     * @param key 存储对象key
     * @return
     */
    @GetMapping("/fetch/{key:.+}")
    public ResponseEntity<Resource> fetch(@PathVariable String key, HttpServletRequest request) {
        LitemallStorage litemallStorage = litemallStorageService.findByKey(key);
        if (key == null) {
            return ResponseEntity.notFound().build();
        }
        if (key.contains("../")) {
            return ResponseEntity.badRequest().build();
        }
        String type = litemallStorage.getType();
        MediaType mediaType = MediaType.parseMediaType(type);//先查数据库 拿到文件类型

        Resource file = storageService.loadAsResource(key);//在从存储系统中取到他
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(mediaType).body(file);
    }

    /**
     * 访问存储对象
     *
     * @param key 存储对象key
     * @return
     */
    @GetMapping("/download/{key:.+}")
    public ResponseEntity<Resource> download(@PathVariable String key) {
        LitemallStorage litemallStorage = litemallStorageService.findByKey(key);
        if (key == null) {
            return ResponseEntity.notFound().build();
        }
        if (key.contains("../")) {
            return ResponseEntity.badRequest().build();
        }

        String type = litemallStorage.getType();
        MediaType mediaType = MediaType.parseMediaType(type);

        Resource file = storageService.loadAsResource(key);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(mediaType).header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}
