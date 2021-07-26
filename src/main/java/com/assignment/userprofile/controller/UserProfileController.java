package com.assignment.userprofile.controller;

import com.assignment.userprofile.dto.UserProfileDto;
import com.assignment.userprofile.exception.ResourceNotFoundException;
import com.assignment.userprofile.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class UserProfileController {

    private UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping("userprofile")
    public ResponseEntity<UserProfileDto> createUserProfile(@Valid @RequestBody UserProfileDto userProfileDto) {
        UserProfileDto createdUserProfileDto =  userProfileService.save(userProfileDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(createdUserProfileDto.getId()).toUri();
        return ResponseEntity.created(location).body(createdUserProfileDto);
    }

    @GetMapping("userprofiles/{id}")
    public ResponseEntity<UserProfileDto> getUserProfileById(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<UserProfileDto> userProfileData = Optional.ofNullable(userProfileService.get(id));
        if (userProfileData.isPresent()) {
            return new ResponseEntity<>(userProfileData.get(), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Couldn't find any matching user profile");
        }
    }

    @GetMapping("userprofiles")
    public ResponseEntity<List<UserProfileDto>> getAllUserProfiles() {
        List<UserProfileDto> userProfileList = userProfileService.getAll();
        return new ResponseEntity<>(userProfileList, HttpStatus.OK);
    }

    @PutMapping("userprofiles/{id}")
    public ResponseEntity<UserProfileDto> updateUserProfileById(@PathVariable Long id, @Valid @RequestBody UserProfileDto userProfileDto) throws ResourceNotFoundException {
        UserProfileDto updatedUserProfileDto = userProfileService.update(id, userProfileDto);
        if(updatedUserProfileDto == null) {
            throw new ResourceNotFoundException("Couldn't find any matching user profile to update");
        } else {
            return new ResponseEntity<>(updatedUserProfileDto, HttpStatus.OK);
        }
    }

    @DeleteMapping("userprofiles/{id}")
    public ResponseEntity<UserProfileDto> deleteUserProfileById(@PathVariable Long id) {
        userProfileService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
