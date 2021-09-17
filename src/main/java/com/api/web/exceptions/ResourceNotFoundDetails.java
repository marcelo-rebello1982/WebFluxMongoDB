package com.api.web.exceptions;

import java.util.Date;
import java.util.Set;


public class ResourceNotFoundDetails {

    private String title;
    private String detail;
    private Date timestamp;
    private Integer status;
    private String code;
    private Set<ErrorDetails> errors;
    private String developerMessage;

    public ResourceNotFoundDetails(Date timestamp, Integer status, String code, Set<ErrorDetails> errors, String title) {
        this.timestamp = timestamp;
        this.status = status;
        this.code = code;
        this.errors = errors;
        this.title = title;
    }

    public ResourceNotFoundDetails(String title, String detail, Date timestamp, Integer status, String code, Set<ErrorDetails> errors) {
        this.title = title;
        this.detail = detail;
        this.timestamp = timestamp;
        this.status = status;
        this.code = code;
        this.errors = errors;
    }

    public ResourceNotFoundDetails() {
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }


    public ResourceNotFoundDetails(Date timestamp, Integer status, String code, Set<ErrorDetails> errors) {
        this.timestamp = timestamp;
        this.status = status;
        this.code = code;
        this.errors = errors;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<ErrorDetails> getErrors() {
        return errors;
    }

    public void setErrors(Set<ErrorDetails> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResourceNotFoundDetails{");
        sb.append("timestamp=").append(timestamp);
        sb.append(", status=").append(status);
        sb.append(", code='").append(code).append('\'');
        sb.append(", errors=").append(errors);
        sb.append('}');
        return sb.toString();
    }

    public static final class Builder {

        private String title;
        private int status;
        private String detail;
        private long timestamp;
        private String devleloperMessage;
        private String message;

        public Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder developerMessage(String devleloperMessage) {
            this.devleloperMessage = devleloperMessage;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public ResourceNotFoundDetails build() {

            ResourceNotFoundDetails resourceNotFoundDetails = new ResourceNotFoundDetails();
            resourceNotFoundDetails.setDeveloperMessage(devleloperMessage);
//            resourceNotFoundDetails.setTitle(title);
//            resourceNotFoundDetails.setDetail(detail);
//            resourceNotFoundDetails.setTimestamp(timestamp);
//            resourceNotFoundDetails.setMessage(message);
            return resourceNotFoundDetails;

        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }


        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}


