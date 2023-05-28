const formId = "profile-form"
const form = document.getElementById(formId)
form.addEventListener("submit", function(event) {
    event.preventDefault();
    if (confirm("Ви справді бажаєте зберегти зміни?")){
        submitHandlerParam(formId, "POST", form.action)
            .then(response => {
                console.log(response)
                if (response.ok){
                    alert("Дані збережено!")
                } else {
                    alert("Сталася помилка!")
                }
            })
            .catch(err => {
                console.error(err)
                alert("Сталася помилка!")
            })
    }
})

function onCancelButtonClick(ticketId){
    if (confirm("Ви справді бажаєте відмінити бронювання квитка на цей сеанс?")) {
        console.log("button", ticketId)

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
                alert("Бронювання відмінено!")
            }
        })
        .catch(error => {
            console.log(error)
        });
    }
}