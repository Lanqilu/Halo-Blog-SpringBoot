package com.halo.admin.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author HALO
 */
public class CaptchaException extends AuthenticationException {

	public CaptchaException(String msg) {
		super(msg);
	}
}
