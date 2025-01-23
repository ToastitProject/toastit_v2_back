package org.toastit_v2.feature.user.application.service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.toastit_v2.common.common.application.code.CommonExceptionCode;
import org.toastit_v2.common.common.application.exception.RestApiException;
import org.toastit_v2.common.security.domain.CustomUserDetails;
import org.toastit_v2.feature.user.application.port.UserRepository;
import org.toastit_v2.feature.user.domain.User;
import org.toastit_v2.feature.user.domain.UserStatus;
import org.toastit_v2.feature.user.web.request.UserUpdateNicknameRequest;
import org.toastit_v2.feature.user.web.request.UserUpdatePasswordRequest;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    public static final String PROFILE_IMAGE_DIR = "profile";

    private final UserAuthService authService;

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserByDetails(CustomUserDetails userDetails) {
        return repository.findByEmail(userDetails.getEmail()).orElseThrow(
                () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
        );
    }

    @Override
    public void checkDuplicate(String nickname) {
        repository.findByNickname(nickname).ifPresent(user -> {
            throw new RestApiException(CommonExceptionCode.EXIST_NICKNAME_ERROR);
        });
    }

    @Override
    public void update(CustomUserDetails userDetails, UserUpdateNicknameRequest request) {
        String nickname = userDetails.getNickname();

        repository.findByNickname(nickname).ifPresentOrElse(
                user -> {
                    throw new RestApiException(CommonExceptionCode.EXIST_NICKNAME_ERROR);
                },
                () -> repository.findByEmail(userDetails.getEmail()).ifPresentOrElse(
                        user -> repository.save(user.update(request)),
                        () -> {
                            throw new RestApiException(CommonExceptionCode.NOT_FOUND_USER);
                        }
                )
        );
    }

    public void update(CustomUserDetails userDetails, UserUpdatePasswordRequest request) {
        String encryptPassword = passwordEncoder.encode(request.getPassword());

        repository.findByEmail(userDetails.getEmail()).ifPresentOrElse(
                user -> repository.save(user.update(encryptPassword)),
                () -> {
                    throw new RestApiException(CommonExceptionCode.NOT_FOUND_USER);
                }
        );
    }

    @Override
    public void deactivate(CustomUserDetails userDetails, HttpServletRequest request) {
        authService.logout(userDetails);

        Long userId = repository.findByEmail(userDetails.getEmail())
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
                )
                .getId();

        repository.update(userId, UserStatus.INACTIVE);
    }

    /**
     * 사용자 프로필 이미지를 업데이트하는 데 사용됩니다.
     * <p>
     * 이 메서드는 전달된 파일이 있을 경우 해당 파일을 업로드하고, 기존 이미지가 있을 경우 이를 삭제한 후, 사용자 정보를 업데이트합니다.
     * </p>
     *
     * <p>
     * 추후 기능 개발 예정: 이미지 관련 기능들이 개발 된 후 확장할 예정입니다.
     * </p>
     *
     * @param userDetails 현재 인증된 사용자 정보를 담고 있는 {@link CustomUserDetails} 객체
     * @param file        업데이트할 이미지 파일을 포함하는 {@link Optional} 객체
     * @throws RestApiException {@link CommonExceptionCode#FILE_ERROR} 또는 {@link CommonExceptionCode#NOT_FOUND_USER} 오류가
     *                          발생할 경우
     */
//    public void update(CustomUserDetails userDetails, Optional<MultipartFile> file) {
//        file.orElseThrow(
//                () -> new RestApiException(CommonExceptionCode.FILE_ERROR)
//        );
//
//        String imageUrl = cloudStorageService.upload(file, PROFILE_IMAGE_DIR);
//
//        userRepository.findByEmail(userDetails.getEmail()).ifPresentOrElse(
//                user -> {
//                    if (!user.getImageUrl().isEmpty()) {
//                        cloudStorageService.delete(user.getImageUrl(), PROFILE_IMAGE_DIR);
//                    }
//                    userRepository.save(user.update(imageUrl));
//                },
//                () -> {
//                    throw new RestApiException(CommonExceptionCode.NOT_FOUND_USER);
//                }
//        );
//    }

}
