const formId = "adding-form"
const form = document.getElementById(formId)

form.addEventListener("submit", event => {
    event.preventDefault()
    createConfirm("Ви дійсно бажаєте зберегти введені дані?", (res) => {
        if (res){
            submitHandlerParam(formId, "POST", form.action)
                .then(response => {
                    if (response.ok) {
                        createAlert("Дані успішно збережено!", () => window.location.assign(window.location.pathname))
                    } else {
                        response.text().then(text => createAlert(text))
                    }
                })
                .catch(err => {
                    console.error(err)
                    createAlert("Виникла помилка!")
                })
        }
    })
})