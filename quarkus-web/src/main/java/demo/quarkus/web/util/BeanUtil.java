package demo.quarkus.web.util;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.CDI;

/**
 * @author Jin Zheng
 * @since 2022-03-31
 */
public class BeanUtil {
    private BeanUtil() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> type) {
        var manager = CDI.current().getBeanManager();
        var beans = manager.getBeans(type);
        if (beans == null || beans.isEmpty()) {
            return null;
        }
        Bean<T> bean = (Bean<T>) beans.iterator().next();
        var ctx = manager.createCreationalContext(bean);
        return (T) manager.getReference(bean, type, ctx);
    }
}
