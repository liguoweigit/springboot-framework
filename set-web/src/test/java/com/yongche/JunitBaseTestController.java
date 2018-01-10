package com.yongche;

import com.le.config.client.ConfigManager;
import com.yongche.config.dict.Dicts;
import com.yongche.config.logging.LoggingConfig;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.net.URL;

/**
 * spring bean test
 * by yongche.com
 *
 * @author mma
 * @since 2017-05-10 上午10:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class JunitBaseTestController {

    private static String env = "test";

    @BeforeClass
    public static void init() {
        /*System.setProperty("APP_ENV", env);
        try {
            URL url = ResourceUtils.getURL("classpath:" + env + "-config-node.xml");
            System.setProperty(ConfigManager.ENV_CONFIG_INFO_FILE_PATH, url.getFile());
            LoggingConfig logging = ConfigManager.get(LoggingConfig.class);
            String location = Dicts.get("logbackPath");
//            try {
////                initLogging(location);
//            } catch (JoranException e) {
//                e.printStackTrace();
//            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
    }

//    private static void initLogging(String location) throws FileNotFoundException, JoranException {
//        String resolvedLocation = SystemPropertyUtils.resolvePlaceholders(location);
//        URL url = ResourceUtils.getURL(resolvedLocation);
//        LoggerContext loggerContext = (LoggerContext) StaticLoggerBinder.getSingleton().getLoggerFactory();
//
//        // in the current version logback automatically configures at startup the context, so we have to reset it
//        loggerContext.reset();
//
//        // reinitialize the logger context.  calling this method allows configuration through groovy or xml
//        //new ContextInitializer(loggerContext).configureByResource(url);
//        JoranConfiguratorExt configurator = new JoranConfiguratorExt();
//        configurator.setContext(loggerContext);
//        configurator.doConfigure(url);
//    }
}
