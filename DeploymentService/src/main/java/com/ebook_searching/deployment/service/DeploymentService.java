package com.ebook_searching.deployment.service;
import com.ebook_searching.deployment.dto.DeploymentRequest;

public interface DeploymentService {
    boolean validateToken(String token);
    boolean deploy(DeploymentRequest request);
}
