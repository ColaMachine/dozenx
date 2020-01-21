package com.dozenx.web.module.interfaceapi.exampletest.service;


import com.dozenx.web.core.log.ResultDTO;

public interface ExampleTestService {
    ResultDTO callInterface(Integer id, String params);
    void Test();
    ResultDTO addTestCase(Integer id, String params);
    ResultDTO deleteTestCase(Integer id);
    ResultDTO updateTestCase(Integer id, String vcparams);
    //ResultDTO selectTestCase();
}