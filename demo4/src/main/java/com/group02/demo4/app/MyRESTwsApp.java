package com.group02.demo4.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.group02.demo4.db.UserDB;
import com.group02.demo4.resources.AuthorResource;
import com.group02.demo4.resources.BookResource;
import com.group02.demo4.test.MockDataInitializer;
import com.group02.demo4.exceptions.DataNotFoundExceptionMapper;
import com.group02.demo4.exceptions.GenericExceptionMapper;

// Set the base path for all REST endpoints in this application
@ApplicationPath("/api")
public class MyRESTwsApp extends Application {

    // Constructor to initialize the database and mock data
    public MyRESTwsApp() {
        // Initialize the user database with default users
        UserDB.initialize();
        System.out.println("User DB is initialised");
        // Initialize mock data for authors and books
        MockDataInitializer.initializeMockData();
    }

    // Override the getClasses method to specify the resources and exception handlers
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        // Register REST resource classes
        resources.add(AuthorResource.class);
        resources.add(BookResource.class);
        // Register exception mappers for handling errors
        resources.add(DataNotFoundExceptionMapper.class);
        resources.add(GenericExceptionMapper.class);
        return resources;
    }
}
