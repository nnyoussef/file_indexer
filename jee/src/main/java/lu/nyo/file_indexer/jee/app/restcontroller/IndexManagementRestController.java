package lu.nyo.file_indexer.jee.app.restcontroller;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletResponse;
import lu.nyo.file_indexer.jee.app.dto.request.IndexingRequest;
import lu.nyo.file_indexer.jee.functions.indexation.*;
import lu.nyo.file_indexer.jee.functions.indexation.file.*;
import lu.nyo.file_indexer.jee.functions.indexation.file.MoveFilesToDirectoryWithIndexNameFunction.Input;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/index_management")
public class IndexManagementRestController extends BaseRestController {

    @PostMapping(value = "/create/index")
    @ResponseStatus(NO_CONTENT)
    public void createIndex(@RequestParam("indexName") String indexName,
                            @RequestParam("mappings") LinkedList<String> mappings,
                            @RequestParam("description") MultipartFile description) {
        IndexingRequest indexingRequest = new IndexingRequest();
        indexingRequest.setIndexName(indexName);
        indexingRequest.setMappings(mappings);
        indexingRequest.setDescription(description);

        functionsChainer.run(indexingRequest,
                IsIndexCreatedFunction.class,
                CreateIndexFunction.class,
                CreateIndexMappingsFunction.class,
                DbSaveIndexNameFunction.class,
                DbSaveIndexMappingsFunction.class);
    }

    @GetMapping(value = "/get_all_indices")
    public Map<String, Object> getAllIndices() {
        return functionsChainer.runWithResult(null, Map.of(), GetAllIndexesWithMappingFunction.class);
    }

    @PostMapping("index/files")
    @ResponseStatus(NO_CONTENT)
    public void indexFiles(@RequestParam("files") MultipartFile[] files,
                           @RequestParam("indices") String indices,
                           @RequestParam("index") String index) {
        functionsChainer.run(new Input(files, indices, index),
                MoveFilesToDirectoryWithIndexNameFunction.class,
                InsertIndicesInEsFunction.class,
                RegisterFileWithIndicesInDbFunction.class);

    }

    @PostMapping("/search_by_index_indices")
    public List<String> search(@RequestBody JSONObject request) {
        String indexName = request.getString("indexName");
        String dateFrom = request.getString("dateFrom");
        String dateTo = request.getString("dateTo");
        Map<String, String> data = ((Map<String, String>) request.get("data"));
        GetFilesByIndexNameFunction.Input input = new GetFilesByIndexNameFunction.Input(indexName, dateFrom, dateTo, data);
        return functionsChainer.runWithResult(input, Collections.emptyList(), GetFilesByIndexNameFunction.class, FetchFilesByIdsFunction.class);
    }

    @GetMapping("/download/{indexName}/{fileName}")
    public void downloadFile(HttpServletResponse response,
                             @PathVariable("indexName") String indexName,
                             @PathVariable("fileName") String fileName) throws IOException {
        Path fullPath = Paths.get(applicationStorage.getBasepath(), indexName, fileName);
        String contentType = Files.probeContentType(fullPath);
        response.setContentType(contentType);
        response.setContentLengthLong(Files.size(fullPath));
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                .filename(fileName, StandardCharsets.UTF_8)
                .build()
                .toString());
        Files.copy(fullPath, response.getOutputStream());
    }

}