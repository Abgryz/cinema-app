const formId = "register-form"
const form = document.getElementById(formId)

form.addEventListener("submit", function(event) {
    event.preventDefault()
    if(form.querySelector("#password").value !== form.querySelector("#repeat-password").value){
        alert("Паролі не співпадають. Спробуй ще раз")
    } else {
        submitHandlerParam(formId, "POST", form.action)
            .then(response => response.json())
            .then(response => {
                if(response.responseStatus){
                    console.log(response)
                    alert("Реєстрація пройшла успішно")
                    redirectToLogin()
                } else {
                    alert("Виникла помилка при реєстрації")
                }
            })
            .catch(err => {
                console.error(err)
                alert("Виникла помилка при реєстрації")
            })
    }
})

function redirectToLogin(){
    window.location.assign("/login");
}