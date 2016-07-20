package com.tinymvc.ioc;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by shenwenbo on 16/7/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:appcontext-core.xml"})
public class TinyMvcProxyBeanTest {

    public void ProsyTest() {

    }

}
