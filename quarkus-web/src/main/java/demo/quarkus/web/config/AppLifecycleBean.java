package demo.quarkus.web.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jin Zheng
 * @since 2022-03-30
 */
@Slf4j
@ApplicationScoped
public class AppLifecycleBean  {

    public AppLifecycleBean() {
    }

    public void onStartup(@Observes StartupEvent event) {
        log.info("正在启动中...");
    }

    public void onShutdown(@Observes ShutdownEvent event) {
        log.info("正在关闭中...");
    }
}
