package net.setlog.setstock.common.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Quartz 스케줄러 설정 클래스
 * 주기적인 작업 실행을 위한 Quartz 스케줄러 설정을 정의
 */
@Configuration
@EnableScheduling
public class QuartzConfig {

    private final DataSource dataSource;

    /**
     * 생성자
     * @param dataSource 데이터 소스
     */
    public QuartzConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Quartz 스케줄러 팩토리 빈 설정
     * @return SchedulerFactoryBean 객체
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setDataSource(dataSource);
        schedulerFactory.setOverwriteExistingJobs(true);
        schedulerFactory.setAutoStartup(true);

        // Quartz 속성 설정
        Properties properties = new Properties();

        // JDBC 작업 저장소 설정
        properties.setProperty("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        properties.setProperty("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");
        properties.setProperty("org.quartz.jobStore.tablePrefix", "QRTZ_");
        properties.setProperty("org.quartz.jobStore.isClustered", "true");
        properties.setProperty("org.quartz.jobStore.misfireThreshold", "60000");

        // 스레드 풀 설정
        properties.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        properties.setProperty("org.quartz.threadPool.threadCount", "10");
        properties.setProperty("org.quartz.threadPool.threadPriority", "5");

        // 기타 설정
        properties.setProperty("org.quartz.scheduler.instanceId", "AUTO");
        properties.setProperty("org.quartz.scheduler.instanceName", "StockTradingScheduler");

        schedulerFactory.setQuartzProperties(properties);

        return schedulerFactory;
    }

    /**
     * Quartz 스케줄러 빈 설정
     * @return Scheduler 객체
     * @throws SchedulerException 스케줄러 생성 중 발생할 수 있는 예외
     */
    @Bean
    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean().getScheduler();
        scheduler.start();
        return scheduler;
    }
}