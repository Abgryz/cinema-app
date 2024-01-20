const formId = "adding-form"
const form = document.getElementById(formId)

form.addEventListener("submit", event => {
    event.preventDefault()
    if (confirm("Ви дійсно бажаєте зберегти введені дані?")){
        submitHandlerParam(formId, "POST", form.action)
            .then(response => response.json())
            .then(response => {
                if (response.responseStatus){
                    console.log(response)
                    alert("Введені дані збережено")
                    window.location.assign(window.location.pathname);
                } else {
                    alert("Виникла помилка!")
                }
            })
            .catch(err => {
                console.error(err)
                alert("Виникла помилка!")
            })
    }
})