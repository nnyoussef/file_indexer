package nyo.lu.appdeployer.jee.app.restcontroller;

import nyo.lu.appdeployer.jee.app.configuration.ApplicationStorage;
import nyo.lu.appdeployer.jee.functions.FunctionsChainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;

@Validated
@Component
@CrossOrigin
public class BaseRestController {

    @Autowired
    protected FunctionsChainer functionsChainer;

    @Autowired
    protected ApplicationStorage applicationStorage;

}
