package com.ebook_searching.deployment.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class DeploymentRequest {
    @NotEmpty
    private List<String> serviceNames;
    private String versionTag;

    // Getters and Setters
}
