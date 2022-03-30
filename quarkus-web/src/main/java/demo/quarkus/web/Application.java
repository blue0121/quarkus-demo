package demo.quarkus.web;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jin Zheng
 * @since 2022-03-30
 */
@QuarkusMain
public class Application {
    public Application() {
    }

    public static void main(String[] args) {
        Quarkus.run(App.class, args);
    }

    @Slf4j
    public static class App implements QuarkusApplication {
        @Override
        public int run(String... args) throws Exception {
            log.info("========== 启动成功 ==========");
            Quarkus.waitForExit();
            return 0;
        }
    }
}
