package com.yongche;

import com.le.config.client.ConfigManager;
import com.yongche.config.dict.Dicts;
import com.yongche.config.logging.LoggingConfig;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.net.URL;

/**
 * ServiceTest
 * by yongche.com
 *
 * @author mma
 * @since 2018-01-10 上午10:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServiceConfiguration.class})
@WebAppConfiguration
public class BaseTestService {
    private static String env = "test";

    @BeforeClass
    public static void init() {

    }
}

@Configuration
@ComponentScan(basePackages = {"com.yongche.**"})
class ServiceConfiguration{

}
