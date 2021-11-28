package com.dynamicallyblunttech.apachepoi;

import com.dynamicallyblunttech.apachepoi.controller.ExcelController;
import com.dynamicallyblunttech.apachepoi.model.ExcelRowEle;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ApachepoiApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ExcelController controller;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void contextLoads() {

    }

    @Test
    void readDummyExcelTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/excel/readDummy")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());

        Assertions.assertNotNull(result.getResponse().getContentAsString());
        Assertions.assertEquals("Success", result.getResponse().getContentAsString());
    }

    @Test
    void integrationPostTest() throws Exception {
        ExcelRowEle obj = new ExcelRowEle();
        obj.setOpen(500); obj.setHigh(700);
        obj.setClose(678); obj.setLow(477); obj.setVolume(5623498);
        obj.setTypicalPrice((obj.getHigh()+obj.getLow()+obj.getClose())/3);
        obj.setVp(obj.getVolume()*obj.getTypicalPrice());
        obj.setTotalV(obj.getVolume()); obj.setTotalVP(obj.getVp());
        obj.setVwap(obj.getTotalV()/obj.getTotalVP());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/excel/test")
                .content(objectMapper.writeValueAsString(obj))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        Assertions.assertEquals(response, obj.toString());
    }
}
