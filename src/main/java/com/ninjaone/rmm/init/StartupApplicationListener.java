package com.ninjaone.rmm.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class StartupApplicationListener implements
        ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    SetupApplicationInitialData setupApplicationInitialData;

    @Override public void onApplicationEvent(ContextRefreshedEvent event) {
        setupApplicationInitialData.setupData();
    }
}