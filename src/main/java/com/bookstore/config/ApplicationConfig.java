/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.config;

/**
 *
 * @author ADMIN
 */

import com.bookstore.resource.*;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.Set;
import java.util.HashSet;

@ApplicationPath("/api") // Base URI for all endpoints
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resource = new HashSet<>();
        
        // Register your REST resource classes here
        resource.add(BookResource.class);
        resource.add(AuthorResource.class);
        resource.add(CustomerResource.class);
        resource.add(CartResource.class);
        resource.add(OrderResource.class);

        return resource;
    }
}



