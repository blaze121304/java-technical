package com.rusty.datasourcesingle.dbconfig;

import org.springframework.stereotype.Component;

@Component
public class DbContextHolderThread {

    private static final ThreadLocal<DbType> currentDb = new ThreadLocal<>();

    /**
     * 현재 스레드에 데이터베이스 타입을 설정합니다.
     */
    public static void setCurrentDb(DbType dbType) {
        if (dbType == null) {
            throw new IllegalArgumentException("dbType must not be null");
        }
        currentDb.set(dbType);
    }

    /**
     * 현재 스레드의 데이터베이스 타입을 반환합니다.
     */
    public static DbType getCurrentDb() {
        DbType dbType = currentDb.get();
        return dbType != null ? dbType : DbType.PRIMARY;
    }

    /**
     * 현재 스레드의 데이터베이스 타입 설정을 제거합니다.
     */
    public static void clear() {
        currentDb.remove();
    }

    /**
     * 현재 데이터베이스 타입이 설정되어 있는지 확인합니다.
     */
    public static boolean hasCurrentDb() {
        return currentDb.get() != null;
    }

}
