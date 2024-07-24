package edu.pnu.config.filter;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {
	// 인가 설정을 위해 사용자의 Role 정보를 읽어 들이기 위한 객체 설정
	private final MemberRepository memberRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String srcToken = request.getHeader("Authorization");		// 요청 헤더에서 Authorization을 얻어오기
		if(srcToken == null || !srcToken.startsWith("Bearer ")) {	// 없거나 "Bearer "로 시작하지 않는다면
			filterChain.doFilter(request, response);				// 필터를 그냥 통과
			return;
		}
		
		String jwtToken = srcToken.replace("Bearer ", "");			// 토큰에서 "Bearer "를 제거
		
		try {			
//			if(!JWT.require(Algorithm.HMAC256("edu.pnu.jwt")).build().verify(jwtToken).getExpiresAt().before(new Date())) {
//				response.setStatus(HttpStatus.UNAUTHORIZED.value());
//				response.getWriter().write("토큰 만료됨");
//				return;
//			}
				
			// 토큰에서 username 추출
			String username = JWT.require(Algorithm.HMAC256("edu.pnu.jwt"))	
					.build()
					.verify(jwtToken)
					.getClaim("username")
					.asString();
			
			Optional<Member> opt = memberRepository.findByEmail(username);	// 토큰에서 얻은 username으로 DB를 검색해서 사용자를 검색
			if(!opt.isPresent()) {											// 사용자가 존재하지 않는다면
				filterChain.doFilter(request, response);					// 필터를 그냥 통과	
				return;
			}
			
			Member findMember = opt.get();
			if(opt.get().isIsdeleted()) {									// 탈퇴한 회원이면
				filterChain.doFilter(request, response);					// 필터를 그냥 통과	
				System.out.println(opt.get().isIsdeleted());
				return;
			}
			System.out.println(findMember.getRole().toString());
			// DB에서 읽은 사용자 정보를 이용해서 UserDetails 타입의 객체를 생성
			User user = new User(findMember.getEmail(), findMember.getPassword(),
					AuthorityUtils.createAuthorityList(findMember.getRole().toString()));
			
			// Authentication 객체를 생성: 사용자명과 권한 관리를 위한 정보 입력(암호는 필요 없음)
			Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			
			// 시큐리티 세션에 등록
			SecurityContextHolder.getContext().setAuthentication(auth);
		} catch (JWTVerificationException exception) {
			// 유효하지 않은 토큰 처리 (로그아웃 처리)
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getWriter().write("Token expiered or invalid");
			return;
		}
		
		filterChain.doFilter(request, response);

	}

}
