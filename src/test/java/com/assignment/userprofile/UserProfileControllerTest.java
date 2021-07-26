package com.assignment.userprofile;

import com.assignment.userprofile.controller.UserProfileController;
import com.assignment.userprofile.dto.AddressDto;
import com.assignment.userprofile.dto.UserProfileDto;
import com.assignment.userprofile.exception.ResourceNotFoundException;
import com.assignment.userprofile.model.Address;
import com.assignment.userprofile.model.UserProfile;
import com.assignment.userprofile.service.UserProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserProfileController.class)
public class UserProfileControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProfileService userProfileService;

    private UserProfileDto userProfileDto;

    private UserProfileDto getUserProfileDtoMock(boolean isCreate) {
        AddressDto addressMock = new AddressDto((isCreate) ? null : 2l, "566112", "Hyderabad", "3 Nipper St", "TL", null);
        Set<AddressDto> addresses = new HashSet<>();
        addresses.add(addressMock);
        UserProfileDto userProfileDtoMock = new UserProfileDto((isCreate) ? null : 1l, "John Doe","JD", "9999011110", addresses);
        return userProfileDtoMock;
    }

    @BeforeEach
    void setup() {
        userProfileDto = getUserProfileDtoMock(false);
    }

    @Test
    void testGet_whenValidInput_thenReturns2XX() throws Exception {

        when(userProfileService.get(anyLong())).thenReturn(userProfileDto);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/userprofiles/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nickName").value("JD"));
    }

    @Test
    void testGet_whenNotFound_thenThrowResourceNotFoundException() throws Exception {

        when(userProfileService.get(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/userprofiles/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Couldn't find any matching user profile"));
    }

    @Test
    void testGetAll_whenValidInput_thenReturns2XX() throws Exception {

        when(userProfileService.getAll()).thenReturn(Arrays.asList(userProfileDto));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/userprofiles")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nickName").value("JD"));
    }

    @Test
    void testGetAll_whenNotFound_thenThrowResourceNotFoundException() throws Exception {

        when(userProfileService.getAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/userprofiles")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testCreate_whenValidInput_thenReturn2XX() throws Exception {

        when(userProfileService.save(any())).thenReturn(userProfileDto);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/userprofile")
                .content( new ObjectMapper().writeValueAsString(getUserProfileDtoMock(true)))
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nickName").value("JD"));
    }

    @Test
    void testCreate_whenInvalidInput_thenThrowValidationException() throws Exception {

        when(userProfileService.save(any())).thenReturn(userProfileDto);

        UserProfileDto request = getUserProfileDtoMock(true);
        request.setPhoneNumber("1234");
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/userprofile")
                .content( new ObjectMapper().writeValueAsString(request))
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Validation Error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Phone number should consist of 10 digits"));
    }

    @Test
    void testUpdate_whenValidInput_thenReturn2XX() throws Exception {

        userProfileDto.setFullName("updatedName");
        when(userProfileService.update(anyLong(), any())).thenReturn(userProfileDto);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/userprofiles/1")
                .content( new ObjectMapper().writeValueAsString(userProfileDto))
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("updatedName"));
    }

    @Test
    void testUpdate_whenInvalidInput_thenThrowValidationException() throws Exception {

        userProfileDto.setFullName(null);
        when(userProfileService.update(anyLong(), any())).thenReturn(userProfileDto);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/userprofiles/1")
                .content( new ObjectMapper().writeValueAsString(userProfileDto))
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Validation Error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Name cannot be blank"));
    }

    @Test
    void testDelete_whenValidInput_thenReturns2XX() throws Exception {

        Mockito.doNothing().when(userProfileService).delete(anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/userprofiles/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }




}
