package io.kotlinProject.demo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotlinProject.demo.model.Bank
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post

// TODO: Re-write some tests using @Nested

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
     private val mockMvc: MockMvc,
     private val objectMapper: ObjectMapper
) {
    // as we use `SpringBoot`, we don't need to have
    // `object` of the class, but we make requests to the
    // very REST API (Endpoint)

    // @Autowired allows making requests to REST
    // API without issuing any HTTP requests

    // `objectMapper` is for serialization of JSON
    @Test
    fun `should return all banks`() {
        // when/then
        mockMvc.get("/api/banks")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$[0].accountNumber") {
                    value("435353")
            }}
    }

    @Test
    fun `should return only one bank`() {
        // when/then
        mockMvc.get("/api/banks/OneBank")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }

    @Test
    fun `should return bank by accNum`() {
        // when/then
        val accNum: String = "435353"

        mockMvc.get("/api/banks/bank/$accNum")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }

    @Test
    fun `should create new bank`() {
        // given
        val newBank = Bank("acc123", 31.434, 5)

        // when
        val response = mockMvc.post("/api/banks") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(newBank)
        }
        // then
        response
            .andDo { print() }
            .andExpect {
                status { isCreated() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.accountNumber") { value("acc123")}
                jsonPath("$.trust") { value(31.434) }
                jsonPath("$.transactionFee") { value(5)}
            }

        // check that db indeed persists new bank
        mockMvc.get("/api/banks/bank/${newBank.accountNumber}")
            .andExpect {
                content { json(objectMapper.writeValueAsString(newBank)) }
            }
    }

    @Test
    fun `should return BAD_REQUEST`() {
        // given
        val invalidBank = Bank("1234", 1.0, 1)

        // when
        val response = mockMvc.post("/api/banks") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(invalidBank)
        }

        // then
        response
            .andDo { print() }
            .andExpect {
                status { isBadRequest() }
            }
    }

    @Test
    fun `should alter exisitng bank`() {
        // given
        val updatedBank = Bank("1234", 5.1, 2)

        // when
        val response = mockMvc.patch("/api/banks") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updatedBank)
        }

        // then
        response
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    json(objectMapper.writeValueAsString(updatedBank))
                }
            }

        // to check that db indeed persists the changes
        mockMvc.get("/api/banks/bank/${updatedBank.accountNumber}")
            .andExpect {
                content { json(objectMapper.writeValueAsString(updatedBank)) }
            }
    }

    @Test
    fun `should return BAD_REQUEST_PATCH`() {
        // given
        val currBank = Bank("1214", 7.5, 4)

        // when
        val response = mockMvc.patch("/api/banks") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(currBank)
        }

        // then
        response
            .andDo { print() }
            .andExpect {
                status { isNotFound() }
            }
    }
}