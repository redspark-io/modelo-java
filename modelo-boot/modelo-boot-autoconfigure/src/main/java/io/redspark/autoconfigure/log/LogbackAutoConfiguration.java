package io.redspark.autoconfigure.log;

import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;

public class LogbackAutoConfiguration {
  
  private static final String APPENDER_PATTERN = "%d{yyyy/MM/dd HH:mm:ss.SSS} [%thread] %-5p %c - %msg%n";
  
  private LoggerContext context;
  
  public LogbackAutoConfiguration() {
    this.context = (LoggerContext)LoggerFactory.getILoggerFactory();
  }
  
  @Bean
  @ConditionalOnProperty
  public ConsoleAppender<ILoggingEvent> stdout() {
    
    ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<ILoggingEvent>();
    appender.setName("STDOUT Apender");
    appender.setContext(context);
    appender.setEncoder(getPatternAppender());

    appender.start();
    
    return appender;
  }
  
  private FixedWindowRollingPolicy getFilePolicy(String file) {
    FixedWindowRollingPolicy policy = new FixedWindowRollingPolicy();

    policy.setContext(context);
    policy.setFileNamePattern(file);
    policy.setMaxIndex(10);
    policy.setMinIndex(1);
    
    policy.start();
    
    return policy;
  }

  private PatternLayoutEncoder getPatternAppender() {
    PatternLayoutEncoder encoder = new PatternLayoutEncoder();

    encoder.setPattern(APPENDER_PATTERN);
    encoder.setContext(context);
    
    encoder.start();
    
    return encoder;
  }
}
