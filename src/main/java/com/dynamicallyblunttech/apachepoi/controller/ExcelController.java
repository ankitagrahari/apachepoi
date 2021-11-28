package com.dynamicallyblunttech.apachepoi.controller;

import com.dynamicallyblunttech.apachepoi.model.ExcelRowEle;
import com.dynamicallyblunttech.apachepoi.service.ReadExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    ReadExcelService excelService;

    @GetMapping("/read")
    @ResponseBody
    public String readExcel(){
        System.out.println("Incoming Request");
        return excelService.readExcel("src/main/resources/poi/Calculations.xlsx");
    }

    @GetMapping("/readDummy")
    @ResponseBody
    public String readDummyExcel(){
        System.out.println("Incoming Dummy Request");
        return excelService.readExcel("src/main/resources/poi/dummy.xlsx");
    }

    @PostMapping("/test")
    @ResponseBody
    public String testForPost(@RequestBody ExcelRowEle rowEle){
        System.out.println(rowEle);
        return rowEle.toString();
    }
}
