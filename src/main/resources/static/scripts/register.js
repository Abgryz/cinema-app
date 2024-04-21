// import {createModal} from "./modal-window";
// import {submitHandlerParam} from "./submit-handler.js"

const formId = "register-form"
const form = document.getElementById(formId)
const registerButton = document.getElementById("register-button")

registerButton.addEventListener("click", function (event) {
    if (form.querySelector("#password").value !== form.querySelector("#repeat-password").value) {
        createAlert("Паролі не співпадають")
    } else {
        submitHandlerParam(formId, "POST", "api/register")
            .then(response => {
                if (response.ok) {
                    createAlert("Реєстрація пройшла успішно. Тепер ви можете увійти в систему.", () => redirectToLogin())
                } else {
                    response.text().then(text => createAlert(text))
                }
            })
            .catch(err => {
                console.error(err)
                createAlert("Виникла помилка при реєстрації. Спробуй ще раз")
            })
    }
})

function redirectToLogin(){
    window.location.assign("/login");
}