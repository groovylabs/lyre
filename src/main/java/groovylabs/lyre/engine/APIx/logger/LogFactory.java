package groovylabs.lyre.engine.APIx.logger;

import groovylabs.lyre.domain.Log;
import org.springframework.beans.factory.FactoryBean;

public class LogFactory implements FactoryBean<Log> {

    public Log logger(Class clz, Object target) {
        Log logInstance = this.getObject();
        logInstance.setTarget(clz.cast(target));
        return logInstance;
    }

    @Override
    public Log getObject() {
        return new Log();
    }

    @Override
    public Class<?> getObjectType() {
        return Log.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
