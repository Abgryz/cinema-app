const formId = "profile-form"
const form = document.getElementById(formId)
form.addEventListener("submit", function(event) {
    event.preventDefault();
    createConfirm("Ви справді бажаєте зберегти зміни?", (res) => {
        if (res) {
            submitHandlerParam(formId, "POST", form.action)
                .then(response => {
                    if (response.ok) {
                        createAlert("Дані успішно збережено!")
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

function onCancelButtonClick(ticketId){
    createConfirm("Ви справді бажаєте відмінити бронювання квитка на цей сеанс?", (res) => {
        if(res){
            fetch('/api/ticket-sales/' + ticketId, {
                method: 'DELETE',
                headers: {
                  'Content-Type': 'application/json',
                },
            })
            .then(response => {
                if(response.ok){
                    const row = document.getElementById("row" + ticketId)
                    row.remove()
                    createAlert("Бронювання видалено!")
                } else {
                    response.text().then(text => createAlert(text))
                }
            })
            .catch(error => {
                console.error(error)
                createAlert("Виникла помилка!")
            });
        }
    })
}