package io.kotlinProject.demo.controller

//import com.fasterxml.jackson.databind.ObjectMapper
//import io.kotlinProject.demo.model.Bank
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.http.MediaType
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotlinProject.demo.model.Bank
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.*

// TODO: Re-write some tests using @Nested

@SpringBootTest
@AutoConfigureMockMvc
//internal class BankControllerTest @Autowired constructor(
//     val mockMvc: MockMvc,
//     val objectMapper: ObjectMapper
//) {
internal class BankControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper
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

    @Test
    fun `should delete provided bank`() {
        // given
        val accountNumber = 1243

        // when
        mockMvc.delete("/api/banks/$accountNumber")

        // then
            .andDo { print() }
            .andExpect {
                status { isNoContent() }
            }

        mockMvc.get("/api/banks/bank/$accountNumber")
            .andDo { print() }
            .andExpect {
                status { isNotFound() }
            }
    }

    @Test
    fun `should return NOT_FOUND if not bank exists`() {
        // given
        val accountNum = "Some random code"

        // when
        mockMvc.delete("/api/banks/$accountNum")

        // then
            .andDo { print() }
            .andExpect {
                status { isNotFound() }
            }
    }
}

// FIRST acronym:
// Fast; Isolated (independent of order, machine etc), Repeatable,
// Self-validating (actual vs expected), Timely
// `@DirtiesContext` is to tell JUnit to restore the state
// of the application context