package com.gorbunovey.logisticapp.service.api;

import com.gorbunovey.logisticapp.dto.UserDTO;

public interface SecurityService {
    UserDTO getAuthUser();
}
