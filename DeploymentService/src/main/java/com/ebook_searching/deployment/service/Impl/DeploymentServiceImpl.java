package com.ebook_searching.deployment.service.Impl;

import com.ebook_searching.deployment.service.DeploymentService;
import com.ebook_searching.deployment.dto.DeploymentRequest;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class DeploymentServiceImpl implements DeploymentService {
    @Override
    public boolean validateToken(String token) {
        String deploymentToken = System.getenv("DEPLOYMENT_TOKEN");
        return deploymentToken.equals(token);
    }

    @Override
    public boolean deploy(DeploymentRequest request) {
        boolean allSuccessful = true;

        for (String serviceName : request.getServiceNames()) {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command("bash", "-c", "cd " + serviceName + " && ./kubernetes");

                Process process = processBuilder.start();
                BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                String line;
                while ((line = outputReader.readLine()) != null) {
                    System.out.println("OUTPUT: " + line);
                }
                while ((line = errorReader.readLine()) != null) {
                    System.out.println("ERROR: " + line);
                }

                int exitCode = process.waitFor();
                if (exitCode != 0) {
                    System.out.println("Deployment failed for service: " + serviceName);
                    allSuccessful = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                allSuccessful = false;
            }
        }

        return allSuccessful;
    }
}