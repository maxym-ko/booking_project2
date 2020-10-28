package com.maxym.booking.db;

public final class Fields {
    private Fields() {}

    public static final String USER_ID = "id";
    public static final String USER_USERNAME = "username";
    public static final String USER_PASSWORD = "password";
    public static final String USER_ROLE = "role";

    public static final String ROOM_ID = "id";
    public static final String ROOM_CAPACITY = "capacity";
    public static final String ROOM_PRICE = "price";
    public static final String ROOM_TYPE = "type";
    public static final String ROOM_STATUS = "status";
    public static final String ROOM_IMG_NAME = "img_name";

    public static final String APPLICATION_ID = "id";
    public static final String APPLICATION_CHECK_IN_DATE = "check_in_date";
    public static final String APPLICATION_CHECK_OUT_DATE = "check_out_date";
    public static final String APPLICATION_REQUIREMENT_CAPACITY = "requirement_capacity";
    public static final String APPLICATION_REQUIREMENT_TYPE = "requirement_type";
    public static final String APPLICATION_STATUS = "status";
    public static final String APPLICATION_TOTAL_PRICE = "total_price";
    public static final String APPLICATION_BILL_ID = "bill_id";
    public static final String APPLICATION_USER_ID = "user_id";
    public static final String APPLICATION_ROOM_ID = "room_id";

    public static final String BILL_ID = "id";
    public static final String BILL_CREATED = "created";
    public static final String BILL_RECEIPT_ID = "receipt_id";
    public static final String BILL_TOTAL_PRICE = "total_price";
}
