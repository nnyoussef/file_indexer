package lu.nyo.file_indexer.jee.app.restcontroller;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletResponse;
import lu.nyo.file_indexer.jee.app.dto.request.IndexingRequest;
import lu.nyo.file_indexer.jee.functions.fetchers.FetchFileRefByIndexIdAndFileName;
import lu.nyo.file_indexer.jee.functions.fetchers.FetchIndexIdByNameFunction;
import lu.nyo.file_indexer.jee.functions.fetchers.GetAllIndexesWithMappingFunction;
import lu.nyo.file_indexer.jee.functions.fetchers.IsIndexCreatedFunction;
import lu.nyo.file_indexer.jee.functions.indexation.CreateIndexFunction;
import lu.nyo.file_indexer.jee.functions.indexation.CreateIndexMappingsFunction;
import lu.nyo.file_indexer.jee.functions.indexation.DbSaveIndexMappingsFunction;
import lu.nyo.file_indexer.jee.functions.indexation.DbSaveIndexNameFunction;
import lu.nyo.file_indexer.jee.functions.indexation.delete_dir.IndexDeleteFromDbFunction;
import lu.nyo.file_indexer.jee.functions.indexation.delete_dir.IndexDeleteFromEsFunction;
import lu.nyo.file_indexer.jee.functions.indexation.delete_dir.IndexDeleteFromFsFunction;
import lu.nyo.file_indexer.jee.functions.indexation.details.GetAllIndexesInDbFunction;
import lu.nyo.file_indexer.jee.functions.indexation.details.GetAllMetaDataFromEsByIndexIdFileNameFunction;
import lu.nyo.file_indexer.jee.functions.indexation.details.GroupByIndexNameFunction;
import lu.nyo.file_indexer.jee.functions.indexation.file.*;
import lu.nyo.file_indexer.jee.functions.indexation.file.MoveFilesToDirectoryWithIndexNameFunction.Input;
import lu.nyo.file_indexer.jee.functions.indexation.file.delete.DeleteFileRefFromfSFunction;
import lu.nyo.file_indexer.jee.functions.indexation.file.delete.DeleteFileRefsFromDbFunction;
import lu.nyo.file_indexer.jee.functions.indexation.file.delete.DeleteFileRefsFromEsFunction;
import lu.nyo.file_indexer.jee.functions.indexation.rename.IndexRenameDbFunction;
import lu.nyo.file_indexer.jee.functions.indexation.rename.IndexRenameEsFunction;
import lu.nyo.file_indexer.jee.functions.indexation.rename.IndexRenameFsFunction;
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

import static java.util.Map.of;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

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
        return functionsChainer.runWithResult(null, of(), GetAllIndexesWithMappingFunction.class);
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
        return functionsChainer.runWithResult(input, Collections.emptyList(),
                GetFilesByIndexNameFunction.class,
                FetchFilesByIdsFunction.class);
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

    @GetMapping("/get/details/all")
    public Object getAllDetails() {
        return functionsChainer.runWithResult(null, null,
                GetAllIndexesInDbFunction.class,
                GroupByIndexNameFunction.class);
    }

    @DeleteMapping("/{indexName}")
    @ResponseStatus(OK)
    public void deleteIndex(@PathVariable("indexName") String indexName) {
        functionsChainer.run(indexName,
                IndexDeleteFromDbFunction.class,
                IndexDeleteFromEsFunction.class,
                IndexDeleteFromFsFunction.class);
    }

    @PostMapping("/rename/index")
    @ResponseStatus(OK)
    public void renameIndex(@RequestBody IndexRenameEsFunction.Input input) {
        functionsChainer.run(input,
                IndexRenameDbFunction.class,
                IndexRenameEsFunction.class,
                IndexRenameFsFunction.class);
    }

    @DeleteMapping("/{indexName}/{fileName}")
    @ResponseStatus(OK)
    public Object deleteFile(@PathVariable("indexName") String indexName,
                             @PathVariable("fileName") String fileName) {
        return functionsChainer.runWithResult(of("indexName", indexName, "fileName", fileName),
                of(),
                FetchIndexIdByNameFunction.class,
                FetchFileRefByIndexIdAndFileName.class,
                DeleteFileRefsFromDbFunction.class,
                DeleteFileRefsFromEsFunction.class,
                DeleteFileRefFromfSFunction.class);
    }

    @GetMapping("metadata/{indexName}/{fileName}")
    Object getAllMetaDataOf(@PathVariable("indexName") String indexName,
                            @PathVariable("fileName") String fileName) {
        return functionsChainer.runWithResult(of("indexName", indexName, "fileName", fileName),
                of(),
                FetchIndexIdByNameFunction.class,
                FetchFileRefByIndexIdAndFileName.class,
                GetAllMetaDataFromEsByIndexIdFileNameFunction.class);
    }
}
