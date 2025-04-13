package com.boltech.service_insurance.service;

import com.boltech.service_insurance.constant.JwtConstants;
import com.boltech.service_insurance.model.User;
import com.boltech.service_insurance.model.UserSession;
import com.boltech.service_insurance.repository.UserSessionRepository;
import com.boltech.service_insurance.security.dto.AuthorizationToken;
import com.boltech.service_insurance.security.dto.TokenResult;
import com.boltech.service_insurance.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserSessionService {

    @Autowired
    UserService userService;

    @Autowired
    UserSessionRepository userSessionRepository;

    @Autowired
    JwtUtil jwtUtil;

    void save(UserSession userSession) {
        userSessionRepository.save(userSession);
    }

    UserSession getById(String id) {
        return userSessionRepository.getById(id);
    }

    void deactivateExistingSessionByUserId(String userId) {
        userSessionRepository.deactivateSessionsByUserId(userId);
    }

    public void markSessionAsInactive(String sessionId) {
        userSessionRepository.deactivateById(sessionId);
    }

    public boolean isSessionActive(String id) {
        return getById(id).getIsActive();
    }

    void saveUserSession(String userId, TokenResult tokenResult) {
        UserSession userSession = new UserSession();
        userSession.setId(tokenResult.getJti());
        userSession.setUserId(userId);
        userSession.setIsActive(true);
        userSession.setCreatedAt(tokenResult.getIssuedAt());
        userSession.setUpdatedAt(tokenResult.getIssuedAt());
        userSession.setExpiredAt(tokenResult.getExpiration());
        save(userSession);
    }

    void extendSession(String id, TokenResult tokenResult, Long expiresInSeconds) {
        UserSession userSession = getById(id);
        userSession.setExpiredAt(tokenResult.getIssuedAt().plusSeconds(expiresInSeconds));
        save(userSession);
    }

    public TokenResult generateRefreshToken(String email) {
        User user = userService.getByEmail(email);

        deactivateExistingSessionByUserId(user.getId());

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtConstants.CLAIMS_ROLES, user.getRoles());

        TokenResult tokenResult = jwtUtil.generateRefreshToken(user.getId(), claims);

        saveUserSession(user.getId(), tokenResult);

        return tokenResult;
    }

    public TokenResult generateAccessToken(AuthorizationToken authorizationToken) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtConstants.CLAIMS_ROLES, authorizationToken.getUserRoles());

        TokenResult tokenResult = jwtUtil.generateAccessToken(authorizationToken.getUserId().toString(), claims, authorizationToken.getSessionId());

        extendSession(authorizationToken.getSessionId(), tokenResult, JwtConstants.TokenType.REFRESH_TOKEN.getExpirationTimeInSeconds());

        return tokenResult;
    }
}
