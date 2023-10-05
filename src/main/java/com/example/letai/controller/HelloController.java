package com.example.letai.controller;


import com.example.letai.model.apimodel.Hello;
import io.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {
    private static final String ID = "id";

    //    describe the exposed REST API.
    @ApiOperation(value = "getGreeting", nickname = "getGreeting")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 200, message = "Successful retrieval",
                    response = Hello.class, responseContainer = "List") })
    @RequestMapping(method = RequestMethod.GET, value = "/api/javainuse")
    public List<Hello> sayHello(@ApiParam(value = "id",
            required = false, defaultValue = "1")  @PathVariable(ID) final int institutuionId) {
        ArrayList<Hello> arrayList= new ArrayList<>();
        arrayList.add(new Hello());
        return arrayList;
    }
}