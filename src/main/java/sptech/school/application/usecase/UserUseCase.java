package sptech.school.application.usecase;

import sptech.school.domain.dto.request.LoginRequestDTO;
import sptech.school.domain.entity.User;

public interface UserUseCase<T extends User, DTO> {
    T update(DTO user, Integer id);
    T login(LoginRequestDTO user);
    T validateSpecify(DTO dto, T userTarget);
}