package com.zgsy.bs.user.impl;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by liangfujie on 16/12/19
 */
@Configuration
@ImportResource("classpath:dubbo.xml")
@EnableScheduling
public class ProjectHostingUserConfiguration {
}
