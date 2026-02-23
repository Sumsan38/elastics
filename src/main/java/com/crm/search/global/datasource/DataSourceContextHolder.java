package com.crm.search.global.datasource;

public class DataSourceContextHolder {
    /*
     * Spring은 각 요청이 독립적이기 때문에, '@Transactional(readOnly = true)'를 Thread 단위로 관리한다
     */
    private static final ThreadLocal<DataSourceType> CONTEXT = new ThreadLocal<>();

    public static void set(DataSourceType type) {
        CONTEXT.set(type);
    }

    public static DataSourceType get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
