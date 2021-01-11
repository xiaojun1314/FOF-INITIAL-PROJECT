package com.fof.init.cache;

import com.fof.component.redis.service.IRedisDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class InitCache implements ApplicationRunner {
	@Autowired
	private IRedisDictionaryService redisBusinessDictionaryService;
	@Async
	public void run(ApplicationArguments args) throws Exception {
		redisBusinessDictionaryService.reloadCache();
	}

}
