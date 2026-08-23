package com.chaukas.user;

import com.chaukas.exception.UserAlreadyExistsException;
import com.chaukas.user.dto.CreateUserRequest;
import com.chaukas.user.dto.UserResponse;
import com.chaukas.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new UserAlreadyExistsException(request.email());
        }

        User user = new User(request.name(), request.email(), request.phone());
        userRepository.save(user);
//       after persist/save, the generated ID is available on the managed entity.
//       So we can use same ref var user, it will now hve id also
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getPhone());
    }
}
