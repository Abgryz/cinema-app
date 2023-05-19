package com.suitt.security.user;

import com.suitt.tables.jobTittle.JobTittle;

public enum Role {
    ROLE_USER, ROLE_MANAGER, ROLE_CASHIER;

    public static String getByJobTittle(JobTittle jobTittle){
        return switch (jobTittle.getName().toLowerCase()) {
            case "касир" -> Role.ROLE_CASHIER.name();
            case "менеджер" -> Role.ROLE_MANAGER.name();
            default -> Role.ROLE_USER.name();
        };
    }
}
