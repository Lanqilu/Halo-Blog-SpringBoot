package com.halo.admin.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author HALO
 */
public class CaptchaException extends AuthenticationException {

	public CaptchaException(String msg) {
		super(msg);
	}
}
