package com.assignment.userprofile;

import com.assignment.userprofile.dto.AddressDto;
import com.assignment.userprofile.dto.UserProfileDto;
import com.assignment.userprofile.model.Address;
import com.assignment.userprofile.model.UserProfile;
import com.assignment.userprofile.repository.UserProfileRepository;
import com.assignment.userprofile.service.UserProfileService;
import com.assignment.userprofile.service.UserProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNull;

@SpringBootTest
class UserprofileServiceMockTest {

	@Autowired
	private UserProfileServiceImpl userProfileService;

	@MockBean
	private UserProfileRepository userProfileRepoMock;
	private UserProfile userProfileSetup;
	private UserProfileDto userProfileDtoSetup;

	private UserProfile getUserProfileMock(boolean isCreate) {
		Address addressMock = new Address((isCreate) ? null : 2l, "566112", "Hyderabad", "3 Nipper St", "TL", null);
		Set<Address> addresses = new HashSet<>();
		addresses.add(addressMock);
		UserProfile userProfileMock = new UserProfile((isCreate) ? null : 1l, "John Doe","JD", "9999011110", addresses);
		return userProfileMock;
	}

	@BeforeEach
	void setup() {
		userProfileSetup = getUserProfileMock(false);
		userProfileDtoSetup = new ModelMapper().map(this.userProfileSetup, UserProfileDto.class);
	}

	@Test
	void testGetById_when_notfound() {
		when(userProfileRepoMock.findById(anyLong())).thenReturn(Optional.empty());
		assertEquals("should return null", null, userProfileService.get(1l));
	}

	@Test
	void testGetById_when_found() {
		when(userProfileRepoMock.findById(anyLong())).thenReturn(Optional.of(userProfileSetup));
		UserProfileDto actual = userProfileService.get(1l);
		assertEquals("should return a user profile dto", userProfileDtoSetup.toString(), actual.toString());
	}

	@Test
	void testGetAll_when_no_records() {
		when(userProfileRepoMock.findAll()).thenReturn(new ArrayList<>());
		assertEquals("should return a zero sized list", 0, userProfileService.getAll().size());
	}

	@Test
	void testGetAll_when_records_exist() {
		when(userProfileRepoMock.findAll()).thenReturn(Arrays.asList(userProfileSetup));
		List<UserProfileDto> actual = userProfileService.getAll();
		assertEquals("should return a list", 1, actual.size());
		assertEquals("should match with the mock list items",userProfileDtoSetup.toString() , actual.get(0).toString());
	}

	@Test
	void testCreate_success() {
		UserProfileDto createUserProfileDto = new ModelMapper().map(getUserProfileMock(true), UserProfileDto.class);
		when(userProfileRepoMock.save(any())).thenReturn(userProfileSetup);
		UserProfileDto actual = userProfileService.save(createUserProfileDto);
		assertEquals("should return a matching mock user profile dto", userProfileDtoSetup.toString(), actual.toString());
	}

	@Test
	void testUpdate_success() {
		when(userProfileRepoMock.findById(anyLong())).thenReturn(Optional.of(userProfileSetup));
		UserProfile updatedUserProfile = getUserProfileMock(false);
		updatedUserProfile.setNickName("updated");
		UserProfileDto updatedUserProfileDto = new ModelMapper().map(updatedUserProfile, UserProfileDto.class);
		when(userProfileRepoMock.save(any())).thenReturn(updatedUserProfile);
		UserProfileDto actual = userProfileService.update(1l, updatedUserProfileDto);
		assertEquals("should return updated mock user profile dto", updatedUserProfileDto.toString(),actual.toString());
	}

	@Test
	void testUpdate_when_not_found() {
		when(userProfileRepoMock.findById(anyLong())).thenReturn(Optional.empty());
		UserProfileDto actual = userProfileService.update(1l, userProfileDtoSetup);
		verify(userProfileRepoMock, never()).save(any());
		assertNull("should return null", actual);
	}

	@Test
	void testDelete_success() {
		Mockito.doNothing().when(userProfileRepoMock).deleteById(anyLong());
		userProfileService.delete(1l);
		verify(userProfileRepoMock, atLeast(1)).deleteById(1l);
	}

}
