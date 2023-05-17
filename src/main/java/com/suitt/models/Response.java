package com.suitt.models;

public record Response(
        ResponseStatus responseStatus,
        String message,
        Object data) {

    public static Response ok(Object data) {
        return new Response(ResponseStatus.OK, null, data);
    }

    public static Response fail() {
        return new Response(ResponseStatus.FAIL, null, null);
    }
}
