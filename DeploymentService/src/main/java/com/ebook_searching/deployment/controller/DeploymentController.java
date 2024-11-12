package com.ebook_searching.deployment.controller;

import com.ebook_searching.deployment.service.DeploymentService;
import com.ebook_searching.deployment.dto.DeploymentRequest;
import org.ebook_searching.common.exception.InvalidFieldsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/deployment")
public class DeploymentController {
    @Autowired
    private DeploymentService deploymentService;

    @PostMapping("")
    public ResponseEntity<String> triggerDeployment(@RequestHeader("Authorization") String token,
                                                    @Valid @RequestBody DeploymentRequest request){


        if (!deploymentService.validateToken(token)) {
            System.out.println("Invalid token " + token);
            throw InvalidFieldsException.fromFieldError("Authorization", "Invalid token");
        }

        boolean success = deploymentService.deploy(request);
        if (success) {
            return ResponseEntity.ok("Deployment triggered successfully");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deployment failed");
    }

}
