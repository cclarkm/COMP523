package com.unc.hospitalschool.security;

public class SecurityConstants {
<<<<<<< HEAD
  public static final String SECRET = "SecretKeyToGenJWTs"; // change
  public static final long EXPIRATION_TIME = 864_000_000; // milliseconds
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final String SIGN_UP_URL = "/users/sign-up";
=======
	public static final String SECRET = "SecretKeyToGenJWTs"; //change
    public static final long EXPIRATION_TIME = 28_800_000; //8 hours /*864_000_000;*/ // milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
>>>>>>> c48922aeda56cdd8d463d2a8995a2d3cedad3a26
}
