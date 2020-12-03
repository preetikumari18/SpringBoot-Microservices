package com.example.demo.controller;

import com.example.demo.entity.ExchangeRate;
import com.example.demo.entity.Person;
import com.example.demo.service.CurrencyServiceImpl;
import com.example.demo.utilities.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyConverterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //@Autowired -- when actual bean object is required to test the implementation
    @MockBean //used when actual bean is not required, used just to verify method call
    private CurrencyServiceImpl currencyService;

    @Autowired
    private RestTemplate restTemplate;

    private String API_URL = "https://open.exchangerate-api.com/v6/latest";

    @Test
    public void whenCreateIsCall_Return201() throws Exception {

        Person person = Person.builder()
                .name("Priya")
                .age(29)
                .userID("priya12")
                .country("INDIA")
                .currency("INR")
                .transactionDate(LocalDateTime.now()).build();

        mockMvc.perform(post("/v1/currencyUser/create")
                .content(JsonUtils.convertToJson(person))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        //use to check if repository  save method is invoked or not
        verify(currencyService, times(1)).createPerson(any());

    }

    @Test
    public void whenGetPerson_thenReturn200() throws Exception {

        Person person = Person.builder()
                .name("Priya")
                .age(29)
                .userID("priya12")
                .country("INDIA")
                .currency("INR")
                .transactionDate(LocalDateTime.now()).build();

        when(currencyService.getPerson("priya12")).thenReturn(person);

        mockMvc.perform(get("/v1/currencyUser/get/priya12")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(29));

    }

    @Test
    public void whenGetPersonNotFound_thenReturn404() throws Exception {

        when(currencyService.getPerson("priya12")).thenReturn(null);

        mockMvc.perform(get("/v1/currencyUser/get/priya12")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenGetResponseFromRestTemplate_thenReturn200() throws Exception {

        ResponseEntity<String> response
                = restTemplate.getForEntity(API_URL, String.class);
        //System.out.println(response.getBody());
        /*ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode name = root.path("base_code");*/


        ExchangeRate exchangeRate = restTemplate.getForObject(API_URL, ExchangeRate.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(exchangeRate.getRates()).isNotEmpty();

    }

    @Test
    public void whenGetConvertedAmountIsCall_thenReturn200() throws Exception {

        Map<String, String> requestUser = new HashMap<>();
        requestUser.put("userID","priya18");
        requestUser.put("amount","501");

        when(currencyService.getUserCurrency(requestUser)).thenReturn("501");

        mockMvc.perform(get("/v1/currencyUser/getAmount?userID=priya18&amount=501")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void whenUpdatePerson_thenReturn200() throws Exception {

        Person person = Person.builder()
                .name("Priya")
                .age(29)
                .userID("priya12")
                .country("INDIA")
                .currency("INR")
                .transactionDate(LocalDateTime.now()).build();

        when(currencyService.getPerson("priya12")).thenReturn(person);

        mockMvc.perform(get("/v1/currencyUser/get/priya12")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(29));

    }

}
