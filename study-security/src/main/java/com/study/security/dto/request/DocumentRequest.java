package com.study.security.dto.request;

import java.io.Serial;
import java.io.Serializable;

public class DocumentRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String documentType;
    private String description;

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
