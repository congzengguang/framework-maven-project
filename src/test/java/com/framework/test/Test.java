package com.framework.test;

import com.framework.test.dao.TestMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:spring.xml","classpath:spring-mvc.xml"})
public class Test {
    @Autowired
    private TestMapper testMapper;
    @org.junit.Test
    public void insertTest(){
        com.framework.test.model.Test test = new com.framework.test.model.Test();
        test.setName("测试");
        test.setSex(1);
        this.testMapper.insert(test);
    }
}
