package com.snapbundle.pojo.base;

public enum Result
{
    OK(1, "%s"),
    ERR_FAILURE(0, "%s"),
    ERR_UNAUTHORIZED(-1, "Authenticated user is not authorized to make this call"),
    ERR_NO_SUCH_URN(-2, "No such URN"),
    ERR_UNLICENSED_FEATURE(-3, "Unlicensed feature"),
    ERR_UNKNOWN_ENTITY_TYPE(-4, "Unknown Entity Reference Type: %s"),
    ERR_MISSING_FIELD(-5, "JSON is missing a required field: %s"),
    ERR_UNKNOWN_DEVICE_IDENTIFICATION(-6, "No device found with identification %s"),
    ERR_ALREADY_EXISTS(-7, "A %s with matching key %s already exists"),
    ERR_NOT_EXISTS(-8, "No matching record with %s of %s exists"),
    ERR_UNKNOWN_ENTITY(-9, "Unknown %s entity with urn %s"),

    ERR_EXTENSION_SECURITY_RESTRICTION(-50, "Extensions are not permitted to perform %s"),
    ERR_EXTENSION_NO_ACCESS(-51, "Caller lacked the authorization to complete the requested operation"),
    ERR_INTERNAL(-500, "Internal Server Error");

    private final String formattedMessage;

    private final int code;

    Result(int code, String formattedMessage)
    {
        this.code = code;
        this.formattedMessage = formattedMessage;
    }

    public String getFormattedMessage()
    {
        return formattedMessage;
    }

    public int getCode()
    {
        return code;
    }

    public static Result translate(int code)
    {
        switch (code)
        {
            case 1:
                return OK;
            case 0:
                return ERR_FAILURE;
            case -1:
                return ERR_UNAUTHORIZED;
            case -2:
                return ERR_NO_SUCH_URN;
            case -3:
                return ERR_UNLICENSED_FEATURE;
            case -4:
                return ERR_UNKNOWN_ENTITY_TYPE;
            case -5:
                return ERR_MISSING_FIELD;
            case -6:
                return ERR_UNKNOWN_DEVICE_IDENTIFICATION;
            case -7:
                return ERR_ALREADY_EXISTS;
            case -8:
                return ERR_NOT_EXISTS;
            case -50:
                return ERR_EXTENSION_SECURITY_RESTRICTION;
            case -51:
                return ERR_EXTENSION_NO_ACCESS;
            case -500:
                return ERR_INTERNAL;
            default:
                throw new IllegalArgumentException("Unknown Result: " + code);
        }
    }
}
