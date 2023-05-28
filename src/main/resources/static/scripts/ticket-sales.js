const forms = document.querySelectorAll("form")
forms.forEach(form => {
    form.addEventListener("submit", event => {
        event.preventDefault()
        if (confirm("Ви дійсно бажаєте зберегти продажі квитків?")){
            submitHandlerParam(form.id, "POST", form.action)
                .then(response => response.json())
                .then(response => {
                    if (response.responseStatus){
                        console.log(response)
                        alert("Продаж квитка збережено")
                        if (form.id.startsWith("form")){
                            form.remove()
                        }
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
})


function onCancelButtonClick(ticketId){
    if (confirm("Ви справді бажаєте відмінити бронювання квитка?")) {

        fetch('/api/admins/ticket-sales/' + ticketId, {method: 'DELETE'})
        .then(response => response.json())
        .then(data =>{
            if(data.responseStatus === true){
                alert("Бронювання скасовано!")
                document.getElementById("form" + ticketId).remove()
            } else{
                alert("Бронювання квитка не вдалося скасувати!")
            }
        })
        .catch(error => {
            alert("Бронювання квитка не вдалося скасувати!")
            console.log(error)
        });
    }
}

function onDeleteButtonClick(){
    const ticketIdInput = document.getElementById("ticketId")
    if (confirm("Ви справді бажаєте видалити продаж квитка?")){

        fetch('/api/admins/ticket-sales/' + ticketIdInput.value, {method: 'DELETE'})
        .then(response => response.json())
        .then(data =>{
            if(data.responseStatus === true){
                alert("Купівлю скасовано!")
                ticketIdInput.value = 0
            } else{
                alert("Купівлю квитка не вдалося скасувати!")
            }
        })
        .catch(error => {
            alert("Купівлю квитка не вдалося скасувати!")
            console.log(error)
        });
    }
}