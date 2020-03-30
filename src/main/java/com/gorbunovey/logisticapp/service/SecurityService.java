package com.gorbunovey.logisticapp.service;

import com.gorbunovey.logisticapp.dto.UserDTO;

public interface SecurityService {
    UserDTO getAuthUser();
}
