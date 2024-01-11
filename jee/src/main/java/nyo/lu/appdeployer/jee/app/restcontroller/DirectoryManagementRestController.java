package nyo.lu.appdeployer.jee.app.restcontroller;

import nyo.lu.appdeployer.jee.app.dto.request.DirectoryIndexingRequest;
import nyo.lu.appdeployer.jee.service.exceptions.DirectoryManagementException;
import nyo.lu.appdeployer.jee.service.functions.FunctionsChainer;
import nyo.lu.appdeployer.jee.service.functions.directory.DirectoryFetchAllFunction;
import nyo.lu.appdeployer.jee.service.functions.directory.DirectoryLabelingFunction;
import nyo.lu.appdeployer.jee.service.functions.directory.DirectorySaveDbFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static java.util.Collections.emptyList;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/directorymanagment")
public class DirectoryManagementRestController extends BaseRestController {

    @Autowired
    private FunctionsChainer functionsChainer;

    @PostMapping(value = "/create/dir")
    @ResponseStatus(NO_CONTENT)
    public void uploadZip(@RequestBody DirectoryIndexingRequest directoryIndexingRequest) throws DirectoryManagementException {
        functionsChainer.run(directoryIndexingRequest,
                DirectorySaveDbFunction.class,
                DirectoryLabelingFunction.class);
    }

    @GetMapping(value = "/getAllWithDetails")
    public Object getAllDirectories() {
        return functionsChainer.runWithResult(null, emptyList(), DirectoryFetchAllFunction.class);
    }
}
