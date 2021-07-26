package com.assignment.userprofile.service;
import com.assignment.userprofile.dto.UserProfileDto;

import java.util.List;

public interface UserProfileService {
    public UserProfileDto save(UserProfileDto userProfileDto);
    public UserProfileDto update(Long id, UserProfileDto userProfileDto);
    public UserProfileDto get(Long id);
    public List<UserProfileDto> getAll();
    public void delete(Long id);
}
