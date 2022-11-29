package group4.feedapp.API.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import group4.feedapp.API.dao.FAUserDAO;
import group4.feedapp.API.model.FAUser;

@Service
public class TokenService {

	private final JwtEncoder jwtEncoder;
	private final FAUserDAO userDao;
	
	public TokenService(JwtEncoder jwtEncoder, FAUserDAO userDao) {
		this.jwtEncoder = jwtEncoder;
		this.userDao = userDao;
	}
	
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        FAUser user = userDao.readUserByEmail(authentication.getName());
        if (user == null)
        	return "";
        
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("name", user.getName())
                .claim("userId", user.getId())
                .claim("scope", scope)
                .claim("admin", user.isAdmin())
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
