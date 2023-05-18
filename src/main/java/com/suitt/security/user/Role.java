package com.suitt.security.user;

import com.suitt.tables.jobTittle.JobTittle;

public enum Role {
    USER, MANAGER, CASHIER;

    public static String getByJobTittle(JobTittle jobTittle){
        return switch (jobTittle.getName().toLowerCase()) {
            case "касир" -> Role.CASHIER.name();
            case "менеджер" -> Role.MANAGER.name();
            default -> Role.USER.name();
        };
    }
}
