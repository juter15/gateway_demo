//package com.example.gateway_demo.security;
//
//import com.example.gateway_demo.model.RefreshTokenEntity;
//import com.example.gateway_demo.model.User;
//import com.example.gateway_demo.repository.RefreshTokenRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ignite.Ignite;
//import org.apache.ignite.IgniteCache;
//import org.springframework.stereotype.Component;
//
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.util.Date;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class RefreshTokenOperator {
//	public static final String CACHE_NAME = "REFRESH_TOKEN_CACHE";
//
//	private final RefreshTokenRepository refreshTokenRepository;
//	private final Ignite ignite;
//
//
//	public void saveRefreshToken(User user, String refreshToken) {
//		RefreshTokenEntity value = new RefreshTokenEntity()
//				.setUserSeq(Long.parseLong(user.getUserSeq()))
//				.setUserNa(user.getUsername())
//				.setRefreshToken(refreshToken)
//				.setCreatedTime(new Date());
//
//		refreshTokenRepository.save(refreshToken, value);
//	}
//
//
//	public String findAuthCode(String refreshToken) {
//		return refreshTokenRepository.fi(refreshToken)
//				.map(RefreshTokenEntity::getAuthCode)
//				.orElse(null);
//	}
//
//
//	public void removeRefreshToken(String refreshToken) {
//		IgniteCache<String, RefreshTokenEntity> cache = ignite.cache(CACHE_NAME);
//		cache.remove(refreshToken);
//	}
//
//
//	@SuppressWarnings("Duplicates")
//	public String generateRefreshToken() {
//		IgniteCache<String, RefreshTokenEntity> cache = ignite.cache(CACHE_NAME);
//		String refreshToken = "";
//
//		try {
//			do {
//				// Initialize SecureRandom
//				// This is a lengthy operation, to be done only upon initialization of the application
//				SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
//
//				// generate a random number
//				String randomNum = Integer.toString(prng.nextInt());
//
//				// get its digest
//				MessageDigest sha = MessageDigest.getInstance("SHA-1");
//				byte[] result =  sha.digest(randomNum.getBytes());
//
//				refreshToken = hexEncode(result);
//
//				log.debug("### Created Refresh Token. randomNumber={}, refreshToken={}", randomNum, refreshToken);
//			} while (cache.get(refreshToken) != null);
//
//		} catch (NoSuchAlgorithmException e) {
//			log.error("Refresh Token 생성 실패", e);
//		}
//
//		return refreshToken;
//	}
//
//
//	private String hexEncode(byte[] input){
//		StringBuilder result = new StringBuilder();
//		char[] digits = {'0', '1', '2', '3', '4','5','6','7','8','9','a','b','c','d','e','f'};
//		for (byte b : input) {
//			result.append(digits[(b & 0xf0) >> 4]);
//			result.append(digits[b & 0x0f]);
//		}
//		return result.toString();
//	}
//}
