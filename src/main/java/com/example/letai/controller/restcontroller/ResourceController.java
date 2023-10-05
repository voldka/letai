package com.example.letai.controller.restcontroller;

import com.example.letai.config.authenticate.config.user.CustomUserDetailsService;
import com.example.letai.config.authenticate.jwt.JwtUtil;
import com.example.letai.model.body.payload.AuthenticationResponse;
import com.example.letai.model.entity.RefreshToken;
import com.example.letai.model.response.GenericResponse;
import com.example.letai.repository.RefreshTokenRepository;
import com.example.letai.services.UserService;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ResourceController {

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	@Autowired
	private JwtUtil jwtUtil;
	private UserDetailsService userDetailsService;

	@RequestMapping("/hellouser")
	public String getUser()
	{
		return "Hello User";
	}
	
	@RequestMapping("/helloadmin")
	public String getAdmin()
	{
		return "Hello Admin";
	}
	@RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
	public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// From the HttpRequest get the claims
		try {
			DefaultClaims claims = (DefaultClaims) request.getAttribute("claims");
			String oldRefreshToken = (String) request.getAttribute("refreshToken");
			RefreshToken rs = refreshTokenRepository.findByToken(oldRefreshToken);
			if(rs==null){
				return ResponseEntity.badRequest().build();
			}else{
				refreshTokenRepository.delete(rs);

				Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
				String accessToken = jwtUtil.doGenerateAccessToken(expectedMap, expectedMap.get("sub").toString());
				String refreshToken = jwtUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());

				RefreshToken token = new RefreshToken();
				token.setToken(refreshToken);
				refreshTokenRepository.save(token);

				Cookie cookie = new Cookie("refreshToken", refreshToken);
				cookie.setHttpOnly(true);
				cookie.setPath("/");
				response.addCookie(cookie);

				return ResponseEntity.ok(new AuthenticationResponse(accessToken));
			}
		}catch (Exception e){
			return ResponseEntity.badRequest().body(new GenericResponse("err","have error system"));
		}
	}
	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}

}
