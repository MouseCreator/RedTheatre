package red.theatre.backendapp.mapper;

import org.springframework.stereotype.Service;
import red.theatre.backendapp.dto.user.UserCreateDTO;
import red.theatre.backendapp.dto.user.UserResponseDTO;
import red.theatre.backendapp.model.User;
@Service
public class UserMapperImpl implements UserMapper {
    @Override
    public UserResponseDTO toResponse(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setRole(user.getRole());
        dto.setLogin(user.getLogin());
        return dto;
    }

    @Override
    public User toUser(UserCreateDTO userCreateDTO) {
        User user = new User();
        user.setLogin(userCreateDTO.getName());
        user.setRole(userCreateDTO.getRole());
        return user;
    }
}
