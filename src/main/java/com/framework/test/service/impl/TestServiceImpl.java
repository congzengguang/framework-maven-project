package com.framework.test.service.impl;

import com.framework.test.dao.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl {
    @Autowired
    public TestMapper testMapper;
}
