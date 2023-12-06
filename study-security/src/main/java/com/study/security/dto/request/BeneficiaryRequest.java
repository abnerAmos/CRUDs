package com.study.security.dto.request;

import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class BeneficiaryRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    @Size(min = 8, max = 11, message = "O contato deve possuir de 8 a 11 caracteres")
    private String phone;
    private String birthday;
    private List<DocumentRequest> documents;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public List<DocumentRequest> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentRequest> documents) {
        this.documents = documents;
    }
}
