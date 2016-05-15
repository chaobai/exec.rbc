package org.ruthie.exec.rbc.controller;

import java.util.Collection;

import org.ruthie.exec.rbc.model.FruitType;
import org.ruthie.exec.rbc.service.FruitTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class FruitTypeController extends AbstractController {
 
    @Autowired
    private FruitTypeService fruitTypeService;
    
    @ApiOperation(value = "Get all fruit types")
    @RequestMapping(method = RequestMethod.GET, path="/fruits", produces = "application/json")
    @ResponseBody
    public Collection<FruitType> getAllFruitTypes() {
        return fruitTypeService.getAllFruitTypes();
    }
    
    @ApiOperation(value = "Update fruit type")
    @RequestMapping(method = RequestMethod.PUT, path="/fruits", produces = "application/json")
    public void updateFruitType(@RequestBody FruitType type) {
        fruitTypeService.updateFruitType(type);
    }
    
    @ApiOperation(value = "Get a fruit type by name")
    @RequestMapping(method = RequestMethod.GET, path="/fruits/{name}", produces = "application/json")
    @ResponseBody
    public FruitType getFruitType(@PathVariable String name) {
        return fruitTypeService.getFruitType(name);
    }
}