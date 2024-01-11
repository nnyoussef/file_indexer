package nyo.lu.appdeployer.jee.app.restcontroller;

import nyo.lu.appdeployer.jee.app.configuration.ApplicationStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;

@Validated
@Component
@CrossOrigin
public class BaseRestController {

    protected ApplicationStorage applicationStorage;

    @Autowired
    public void setApplicationStorage(ApplicationStorage applicationStorage) {
        this.applicationStorage = applicationStorage;
    }
}
