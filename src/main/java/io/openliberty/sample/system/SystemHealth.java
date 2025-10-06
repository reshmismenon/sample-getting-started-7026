/*******************************************************************************
 * Copyright (c) 2018, 2020 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
package io.openliberty.sample.system;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class SystemHealth implements HealthCheck {
  
  @Inject
  SystemConfig systemConfig;
  
  // Static flag to ensure the delay only happens once
  private static boolean startupDelayCompleted = false;
  
  public boolean isHealthy() {
      if (systemConfig.isInMaintenance()) {
        return false;
      }
       return true;
    }
  
  @Override
  public HealthCheckResponse call() {
    // Add a startup delay on first health check
    if (!startupDelayCompleted) {
      try {
        System.out.println("Delaying application readiness for 180 seconds...");
        Thread.sleep(180000); // 180 seconds = 3 minutes
        startupDelayCompleted = true;
        System.out.println("Startup delay completed. Application is now ready.");
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.err.println("Startup delay was interrupted: " + e.getMessage());
      }
    }
    
    if (!isHealthy()) {
      return HealthCheckResponse.named(SystemResource.class.getSimpleName())
          .withData("services","not available").down().build();
    }
    return HealthCheckResponse.named(SystemResource.class.getSimpleName())
            .withData("services","available").up().build();
  }
}






