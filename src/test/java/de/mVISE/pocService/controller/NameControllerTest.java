package de.mVISE.pocService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.mVISE.pocService.entity.NameEntity;
import de.mVISE.pocService.service.NameService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(NameController.class)
@AutoConfigureRestDocs
class NameControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NameService nameService;


    @Test
    void getNameByName() throws Exception {
        NameEntity name = new NameEntity(2,"Antonio");
        List<NameEntity> nameEntities = new ArrayList<>();
        nameEntities.add(name);

        given(nameService.getNameByName("Antonio")).willReturn((nameEntities));

        this.mockMvc.perform(get("/api/name/name/Antonio"))
                .andExpect(status().isOk())
                .andDo(document("get-nameBy-Name",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[].id").description("The unique identifier of the NameEntity"),
                                fieldWithPath("[].name").description("The name of the NameEntity")),
                        responseHeaders(headerWithName("Content-Type").description("The Content-Type of the payload, e.g. application/json"))
                        )
                );
    }

    @Test
    void getAllNames() throws Exception{

        NameEntity nameAntonio = new NameEntity(2,"Antonio");
        NameEntity nameOliver = new NameEntity(2,"Oliver");
        List<NameEntity> nameList = new ArrayList<>();
        nameList.add(nameAntonio);
        nameList.add(nameOliver);

        given(nameService.getAllNames()).willReturn((nameList));

        this.mockMvc.perform(get("/api/name"))
                .andExpect(status().isOk())
                .andDo(document("get-all-names",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(

                                fieldWithPath("[].id").description("The unique identifier of the NameEntity"),
                                fieldWithPath("[].name").description("GThe name of the NameEntity")
                        ),
                        responseHeaders(headerWithName("Content-Type").description("The Content-Type of the payload, e.g. application/json"))
                        )
                );
    }

    @Test
    void getNameById()throws Exception{

        NameEntity name = new NameEntity(222,"Antonio");

        given(nameService.getNameById(222)).willReturn(name);

        this.mockMvc.perform(get("/api/name/id/222"))
                .andExpect(status().isOk())
                .andDo(document(
                        "Get-NameEntity-By-Id",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(

                                fieldWithPath("id").description("The unique identifier of the NameEntity"),
                                fieldWithPath("name").description("The name of the NameEntity")
                        ),
                        responseHeaders(headerWithName("Content-Type").description("The Content-Type of the payload, e.g. application/json"))

                ));



    }

    @Test
    void createName() throws Exception {
        NameEntity nameLeon = new NameEntity(103, "Leon");

        given(nameService.createNameEntity(nameLeon)).willReturn("Name is saved");

        MvcResult result = this.mockMvc.perform(post("/api/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nameLeon))
                )
                .andExpect(status().isOk())
                .andReturn();

        // Überprüfe, ob die Antwort nicht leer ist, bevor die Dokumentation hinzugefügt wird
        String responseContent = result.getResponse().getContentAsString().trim();
        if (!responseContent.isEmpty()) {
            this.mockMvc.perform(post("/api/name")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(nameLeon))
                    )
                    .andDo(document("Create-Name",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),
                            responseFields(
                                    fieldWithPath("String").description("Response from createNameEntity method")
                            ),
                            requestFields(
                                    fieldWithPath("id").description("The unique identifier of the NameEntity"),
                                    fieldWithPath("Name").description("The Name of the NameEntity")
                            )
                    ));
        } else {
            System.out.println("Response is empty, skipping documentation");
        }
    }

    @Test
    void updateNameById() throws Exception{

        NameEntity nameLeon = new NameEntity(103, "Leon");

        NameEntity newName = new NameEntity(104,"LEON BLA");


        given(nameService.updateName(103,newName.getName())).willReturn("Name is updated");

        MvcResult mvcResult = this.mockMvc.perform(put("/api/name/id/103")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newName))
                )
                .andExpect(status().isOk())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString().trim();

        if (!result.isEmpty()) {
            this.mockMvc.perform(put("/api/name/id/103")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(nameLeon))
            ).andDo(document("Update-Name-By-Id",
                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),

                            requestFields(
                                    fieldWithPath("id").description("The unique identifier of the NameEntity who needs to be updated"),
                                    fieldWithPath("name").description("The new Name of the NameEntity who needs to be updated")
                            )
                    )
            );
        } else {
            System.out.println("Response is empty, skipping documentation");
            System.out.println("Response content: " + result);
        }

    }

    @Test
    void deleteNameById() throws Exception{

        this.mockMvc.perform(delete("/api/name/id/3"))
                .andExpect(status().isOk())
                .andDo(document("Delete-Name-By-Id",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())


                        ));
    }


}