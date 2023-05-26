function onCancelButtonClick(ticketId){
    if (confirm("Ви справді бажаєте відмінити бронювання квитка?")) {

        fetch('/api/admins/ticket-sales/' + ticketId, {method: 'DELETE'})
        .then(response => response.json())
        .then(data =>{
            if(data.responseStatus === true){
                alert("Бронювання скасовано!")
                document.getElementById("form" + ticketId).remove()
                return
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
                return
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