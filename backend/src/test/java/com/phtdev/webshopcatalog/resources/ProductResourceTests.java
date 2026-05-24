package com.phtdev.webshopcatalog.resources;

import com.phtdev.webshopcatalog.dto.ProductDTO;
import com.phtdev.webshopcatalog.factory.Factory;
import com.phtdev.webshopcatalog.service.ProductService;
import com.phtdev.webshopcatalog.service.exceptions.ResourceNotRegisteredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductResource.class)
public class ProductResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService service;

    private PageImpl<ProductDTO> page;
    private ProductDTO dto;
    private long existingId;
    private long invalidId;

    @BeforeEach
    void setUp() {
        page = new PageImpl<>(List.of(Factory.createProductDto()));
        existingId = 1L;
        invalidId = 500L;
        dto = Factory.createProductDto();
    }

    @Test
    public void findAllShouldReturnPage() throws Exception {

        Mockito.when(service.findAll(ArgumentMatchers.any())).thenReturn(page);
        // Arrange + Act
        var result = mockMvc.perform(get("/products")
                    .accept(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isOk());
        Mockito.verify(service, Mockito.times(1)).findAll(ArgumentMatchers.any());
    }

    @Test
    public void findByIdShouldReturnNotFoundResponseWhenInvalidId() throws Exception {
       Mockito.doThrow(ResourceNotRegisteredException.class).when(service).findById(invalidId);

        ResultActions result = mockMvc.perform(get("/products/{id}", invalidId)
                .accept(MediaType.APPLICATION_JSON));

        // Assert status code not found
        result.andExpect(status().isNotFound());
        // Assert error response
        result.andExpect(jsonPath("$.status").value(404));
        result.andExpect(jsonPath("$.path").value("/products/" + invalidId));
        result.andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    public void findByIdShouldReturnProductWhenValidId() throws Exception {
        Mockito.when(service.findById(existingId)).thenReturn(dto);

        ResultActions result = mockMvc.perform(get("/products/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.price").exists());
        result.andExpect(jsonPath("$.description").exists());
        result.andExpect(jsonPath("$.categories").exists());
    }
}
