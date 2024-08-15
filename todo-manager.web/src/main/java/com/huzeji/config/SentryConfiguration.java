package com.huzeji.config;

import io.sentry.spring.EnableSentry;
import org.springframework.context.annotation.Configuration;

@EnableSentry(dsn = "https://7e5402070325338aa39146a4f30dc881@o4507782413680640.ingest.us.sentry.io/4507782415187968")
@Configuration
class SentryConfiguration {
}
