package halo.base.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT 工具类
 *
 * @author HALO
 */
@Data
@Component
public class JwtUtils {

    /**
     * 过期时间, 单位 s
     */
    private long expire = 604800;
    /**
     * 密钥
     */
    private String secret = "ji8n4ej4n439n43ld9ne9343ff6er49h";
    private String header = "Authorization";

    /**
     * 生成 JWT
     *
     * @param username 用户名
     * @return JWT
     */
    public String generateToken(String username) {

        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + 1000 * expire);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(nowDate)
                // 7天过期
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 解析 JWT
     *
     * @param jwt JWT
     * @return 解析后的信息
     */
    public Claims getClaimByToken(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            // JWT 不合法
            return null;
        }
    }

    /**
     * 判断 JWT 是否过期
     *
     * @param claims 信息
     * @return 是否过期
     */
    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

}
