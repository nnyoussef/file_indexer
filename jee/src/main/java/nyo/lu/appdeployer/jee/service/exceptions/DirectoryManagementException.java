package nyo.lu.appdeployer.jee.service.exceptions;

import java.util.LinkedList;
import java.util.List;

import static java.lang.String.format;


public final class DirectoryManagementException extends RuntimeException {

    public static enum DirectoryManagementErrors {
        PATH_ALREADY_CREATED("%s already have been created"),
        LABEL_ALREADY_EXISTS("Label for %s already exists"),
        LABELS_LIMITS_REACHED("Label limits for %s have been reached."),
        INDEXING_UNKOWN_ISSUE("Indexing unknown issue for %s "),
        AUTRE("");
        private String message;

        DirectoryManagementErrors(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    private final String path;
    private final List<DirectoryManagementErrors> directoryManagementErrors = new LinkedList<>();

    public DirectoryManagementException(String path) {
        this.path = path;
    }

    public DirectoryManagementException addError(DirectoryManagementErrors directoryManagementError) {
        directoryManagementErrors.add(directoryManagementError);
        return this;
    }

    public List<String> getDirectoryManagementErrors() {
        return directoryManagementErrors.stream()
                .map(e -> format(e.message, path))
                .toList();
    }
}
