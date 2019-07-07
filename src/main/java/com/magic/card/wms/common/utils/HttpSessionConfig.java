package com.magic.card.wms.common.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.session.events.SessionExpiredEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableRedisHttpSession(redisFlushMode = RedisFlushMode.IMMEDIATE)
public class HttpSessionConfig {

	@EventListener
	public void onSessionExpired(SessionExpiredEvent expiredEvent) {
		log.info("===>> Session Expired:{}", expiredEvent.getSessionId());
	}
	
	@EventListener
	public void onSessionDeleted(SessionDeletedEvent deletedEvent) {
		log.info("===>> Session Deleted:{}", deletedEvent.getSessionId());
	}
	
	@EventListener
	public void onSessionCreated(SessionCreatedEvent createdEvent) {
		log.info("===>> Session Created:{}", createdEvent.getSessionId());
	}
	
	@Bean
	public HeaderCookieHttpSession portalHttpSession() {
		return HeaderCookieHttpSession.xAuthToken();
	}
	
}
