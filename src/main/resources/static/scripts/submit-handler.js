function submitHandlerParam(formId, method, endpoint){
    const formData = new FormData(document.getElementById(formId));
    const params = new URLSearchParams();
    formData.forEach((value, key) => {
        params.append(key, value);
    });
    const endpointWithParams = endpoint + "?" + params.toString();
    console.log(endpointWithParams);

    return fetch(endpointWithParams, {
        method: method,
        headers: {
            "Content-Type": "application/json"
        },
    })
}
