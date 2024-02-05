package com.medium.clone.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResourceNotFoundException extends Exception{

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
