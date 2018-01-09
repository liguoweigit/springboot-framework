package com.yongche;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

/**
 * Created by mma on 17/2/13.
 */
public class TestController extends BaseTest{

    public static final Logger logger = LoggerFactory.getLogger(TestController.class);


    /**
     * 诱导取消接口测试
     * @author mma
     * @throws Exception
     */
    @Test
    public void testView() throws Exception {
        MvcResult result = getMockMvc().perform(
                MockMvcRequestBuilders.get("/order/induced?service_order_id=1111&driver_id=1&reason_id=1&user_id=1")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testConf(){
        //decisionService.flushDispatchLog(6371667158178454819L,8,null,null);
        //logger.info("111");
        /*for (int i = 0; i < 50; i++) {
            boolean success = MqProduce.getInstance().publish(MqProduce.QUEUE_SELECTION,"test"+i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }
}
