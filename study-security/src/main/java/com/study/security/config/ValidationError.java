package com.study.security.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
public class ValidationError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;
    private List<ValidationErrorDetail> message;

    public ValidationError(Instant timestamp, Integer status, String error, String path, List<FieldError> message) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
        this.message = message.stream().map(ValidationErrorDetail::new).toList();
    }

    public record ValidationErrorDetail(String field, String message) {
        public ValidationErrorDetail(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
