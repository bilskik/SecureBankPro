package pl.bilskik.backend.controller.mapping;

public final class UrlMapping {

    public final static String AUTH_PATH = "/api/auth";
    public final static String REGISTER_PATH = "/register";
    public final static String RESET_PASSWORD_BEGIN_PATH = "/reset-password/begin";
    public final static String RESET_PASSWORD_FINISH_PATH = "/reset-password/finish";
    public final static String LOGIN_FINISH_PATH = "/login/finish";
    public final static String LOGIN_BEGIN_PATH = "/login/begin";
    public final static String LOGOUT_PATH = "/logout";
    public final static String LOGOUT_SUCCESS_PATH = "/logout/success";
    public final static String PAYMENT_PATH = "/payment";
    public final static String TRANSFER_PATH = "/api/transfer";
    public final static String USER_PATH = "/api/user";
    public final static String USER_DETAILS_PATH = "/details";
    public final static String CSRF_PATH = "/csrf";

    //shorter version:
    public final static String REGISTER_URL = AUTH_PATH + REGISTER_PATH;
    public final static String LOGOUT_URL = AUTH_PATH + LOGOUT_PATH;
    public final static String LOGOUT_SUCCESS_URL = AUTH_PATH + LOGOUT_SUCCESS_PATH;
    public final static String LOGIN_BEGIN_URL = AUTH_PATH + LOGIN_BEGIN_PATH;
    public final static String LOGIN_FINISH_URL = AUTH_PATH + LOGIN_FINISH_PATH;
    public final static String RESET_PASSWORD_BEGIN_URL = AUTH_PATH + RESET_PASSWORD_BEGIN_PATH;
    public final static String RESET_PASSWORD_FINISH_URL = AUTH_PATH + RESET_PASSWORD_FINISH_PATH;
    public final static String CSRF_URL = AUTH_PATH + CSRF_PATH;

}
