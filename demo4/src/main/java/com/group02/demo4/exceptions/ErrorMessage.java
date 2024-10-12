package com.group02.demo4.exceptions;

import jakarta.xml.bind.annotation.XmlRootElement;

// This class represents the structure of an error message that can be returned to the client
@XmlRootElement
public class ErrorMessage {
    
    // A descriptive message explaining the error
    private String errorMessage;
    
    // The HTTP status code associated with the error
    private int errorCode;
    
    // A link to documentation or further information about the error
    private String documentation;

    // Default constructor required for JAXB serialization/deserialization
    public ErrorMessage() {}

    // Parameterized constructor to initialize all fields of the error message
    public ErrorMessage(String errorMessage, int errorCode, String documentation) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.documentation = documentation;
    }

    // Getter for the errorMessage field
    public String getErrorMessage() {
        return errorMessage;
    }

    // Setter for the errorMessage field
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // Getter for the errorCode field
    public int getErrorCode() {
        return errorCode;
    }

    // Setter for the errorCode field
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    // Getter for the documentation field
    public String getDocumentation() {
        return documentation;
    }

    // Setter for the documentation field
    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }
}
