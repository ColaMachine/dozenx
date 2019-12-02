package com.dozenx.web.module.interfaceapi.interfacelog.service;

import com.dozenx.web.core.log.ResultDTO;

public interface InterfaceLogService {
    ResultDTO getTestResult(String params);
    ResultDTO getTestResultByParams(String params);
}
