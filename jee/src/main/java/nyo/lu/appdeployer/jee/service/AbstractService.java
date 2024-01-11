package nyo.lu.appdeployer.jee.service;

import nyo.lu.appdeployer.jee.app.configuration.ApplicationStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class AbstractService {
    protected ApplicationStorage applicationStorage;

    @Autowired
    public void setApplicationStorage(ApplicationStorage applicationStorage) {
        this.applicationStorage = applicationStorage;
    }

    protected void noop() {
    }
}
