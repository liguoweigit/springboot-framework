package com.yongche;

import com.le.config.client.ConfigManager;
import com.yongche.config.logging.WebLogbackConfigurer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * Created by yangnan on 16/10/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServiceConfiguration.class})
@WebAppConfiguration
public class MockBaseTest {

    private static String env = "dev";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeClass
    public static void init() {
        System.setProperty("APP_ENV", env);
        try {
            URL url = ResourceUtils.getURL("classpath:" + env + "-config-node.xml");
            System.setProperty(ConfigManager.ENV_CONFIG_INFO_FILE_PATH, url.getFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void initMvc(){
        ServletContext context = wac.getServletContext();
        WebLogbackConfigurer.initLogging(context);
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    public MockMvc getMockMvc() {
        return mockMvc;
    }
}
