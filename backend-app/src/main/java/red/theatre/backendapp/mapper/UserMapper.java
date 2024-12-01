package red.theatre.backendapp.mapper;

import red.theatre.backendapp.dto.user.UserCreateDTO;
import red.theatre.backendapp.dto.user.UserResponseDTO;
import red.theatre.backendapp.model.User;
public interface UserMapper {
    UserResponseDTO toResponse(User user);
    User toUser(UserCreateDTO userCreateDTO);
}
