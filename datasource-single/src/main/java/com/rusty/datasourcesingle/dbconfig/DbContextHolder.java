package com.rusty.datasourcesingle.dbconfig;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class DbContextHolder {

    private static final String DB_TYPE_ATTRIBUTE = "CURRENT_DB_TYPE";

    /**
     * 현재 세션에 데이터베이스 타입을 설정합니다.
     */
    public static void setCurrentDb(DbType dbType) {
        if (dbType == null) {
            throw new IllegalArgumentException("dbType must not be null");
        }
        RequestContextHolder.currentRequestAttributes()
                .setAttribute(DB_TYPE_ATTRIBUTE, dbType, RequestAttributes.SCOPE_SESSION);
    }

    /**
     * 현재 세션의 데이터베이스 타입을 반환합니다.
     */
    public static DbType getCurrentDb() {
        try {
            DbType dbType = (DbType) RequestContextHolder.currentRequestAttributes()
                    .getAttribute(DB_TYPE_ATTRIBUTE, RequestAttributes.SCOPE_SESSION);
            return dbType != null ? dbType : DbType.PRIMARY;
        } catch (IllegalStateException e) {
            return DbType.PRIMARY;
        }
    }

    /**
     * 현재 세션의 데이터베이스 타입 설정을 제거합니다.
     */
    public static void clear() {
        try {
            RequestContextHolder.currentRequestAttributes()
                    .removeAttribute(DB_TYPE_ATTRIBUTE, RequestAttributes.SCOPE_SESSION);
        } catch (IllegalStateException e) {
            // 요청 컨텍스트가 없는 경우 무시
        }
    }

    /**
     * 현재 데이터베이스 타입이 설정되어 있는지 확인합니다.
     */
    public static boolean hasCurrentDb() {
        try {
            return RequestContextHolder.currentRequestAttributes()
                    .getAttribute(DB_TYPE_ATTRIBUTE, RequestAttributes.SCOPE_SESSION) != null;
        } catch (IllegalStateException e) {
            return false;
        }
    }

}
