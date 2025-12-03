/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/

package io.openliberty.sample;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("system")
public class SampleApplication extends Application {

   static {
        try {
            System.out.println("Delaying application startup for 100 seconds...");
            Thread.sleep(1000); // 1 seconds delay
            System.out.println("Application startup delay completed.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Startup delay was interrupted: " + e.getMessage());
        }
    }
}
