const forms = document.querySelectorAll("form")
forms.forEach(form => {
    form.addEventListener("submit", event => {
        event.preventDefault()
        createConfirm("Ви справді бажаєте зберегти продажі квитків?", (res) => {
            if(res){
                submitHandlerParam(form.id, "POST", form.action)
                    .then(response => {
                        if (response.ok) {
                            createAlert("Продажі квитків збережено!")
                            if (form.id.startsWith("form")){
                                form.remove()
                            }
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
})


function onCancelButtonClick(ticketId){
    createConfirm("Ви справді бажаєте відмінити бронювання квитка?", (res) => {
        if(res){
            fetch('/api/admins/ticket-sales/' + ticketId, {method: 'DELETE'})
                .then(response => {
                    if(response.ok){
                        createAlert("Бронювання квитка відмінено!")
                        document.getElementById("row" + ticketId).remove()
                    } else {
                        response.text().then(text => createAlert(text))
                    }
                })
                .catch(error => {
                    createAlert("Бронювання квитка не вдалося скасувати!")
                    console.log(error)
                });
        }
    })
}

function onDeleteButtonClick(){
    const ticketIdInput = document.getElementById("ticketId")
    createConfirm("Ви справді бажаєте видалити продаж квитка?", (res) => {
        fetch('/api/admins/ticket-sales/' + ticketIdInput.value, {method: 'DELETE'})
            .then(response => {
                if(response.ok){
                    createAlert("Продаж квитка видалено!")
                    ticketIdInput.value = 0
                } else {
                    response.text().then(text => createAlert(text))
                }
            })
            .catch(error => {
                createAlert("Купівлю квитка не вдалося видалити!")
                console.log(error)
            });
    })
}