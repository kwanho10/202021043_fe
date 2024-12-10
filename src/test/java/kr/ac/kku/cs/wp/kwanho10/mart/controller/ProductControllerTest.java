package kr.ac.kku.cs.wp.kwanho10.mart.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.ac.kku.cs.wp.kwanho10.mart.dto.ProductDTO;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/app-context.xml")
public class ProductControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.objectMapper = new ObjectMapper();
    }

//    @Test
//    public void testAddProduct() throws Exception {
//        ProductDTO productDTO = new ProductDTO(1L, "Test Product", 10, "Electronics", 2000L, 1.5, false);
//
//        mockMvc.perform(post("/product/add")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(productDTO)))
//                .andExpect(status().is3xxRedirection()) // 리다이렉트 확인
//                .andExpect(redirectedUrl("/product/list"))
//                .andDo(MockMvcResultHandlers.print());
//    }
//    
    

    @Test
    public void testListProducts() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andReturn();

        @SuppressWarnings("unchecked")
        List<ProductDTO> products = (List<ProductDTO>) mvcResult.getModelAndView().getModel().get("products");
        assertTrue(products.size() > 0);
    }

    @Test
    public void testDetailPage() throws Exception {
        Long productId = 1L;

        MvcResult mvcResult = mockMvc.perform(get("/product/detail/" + productId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"))
                .andReturn();

        ProductDTO product = (ProductDTO) mvcResult.getModelAndView().getModel().get("product");
        assertEquals(productId, product.getId());
        assertEquals("Test Product", product.getName());
    }

    
    
    @Test
    public void testDeleteProduct() throws Exception {
        Long productId = 1L;

        mockMvc.perform(delete("/product/delete/" + productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andDo(MockMvcResultHandlers.print());
    }
    
    
    
}
