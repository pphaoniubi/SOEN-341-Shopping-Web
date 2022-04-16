package com.amazon.dto;

public class AddressChangeDto {

    private String content;
    private Boolean enabled;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public AddressChangeDto() {}

    public AddressChangeDto(String content, Boolean enabled) {
        this.content = content;
        this.enabled = enabled;
    }
}
