package com.yusif.service.WebDav;

import com.yusif.service.WebDav.WebDavSubFunction.WebDavSubFunctionBaseEnvInit;
import com.yusif.service.WebDav.WebDavSubFunction.WebDavSubFunctionMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
@Slf4j
@ConditionalOnProperty(value = "webdav-trigger",havingValue = "true")
@Component
public class Trigger  implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, WebDavSubFunctionBaseEnvInit> Init = applicationContext.getBeansOfType(WebDavSubFunctionBaseEnvInit.class);
        Map<String, WebDavSubFunctionMonitor> monitor = applicationContext.getBeansOfType(WebDavSubFunctionMonitor.class);
         Init.forEach((a,b)->{
            try {
                b.baseEnvInit();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        monitor.forEach((a,b)->{
            try {
                b.monitorInit();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                b.monitorStart();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


    }
}
