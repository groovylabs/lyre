package groovylabs.lyre.engine.APIx.logger;

import groovylabs.lyre.domain.Log;
import org.springframework.beans.factory.FactoryBean;

public class LogFactory implements FactoryBean<Log> {

    @SuppressWarnings("unchecked")
    public Log logger(Object... objects) {
        Log logInstance = this.getObject();
        logInstance.setTarget(objects[0]);
        logInstance.setParameters(objects);
        logInstance.build();
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
