package com.suitt.models;

public record Response(
        boolean responseStatus,
        String message,
        Object data) {

    public static Response ok(Object data) {
        return new Response(true, null, data);
    }

    public static Response fail() {
        return new Response(false, null, null);
    }
}
