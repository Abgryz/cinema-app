function onCancelButtonClick(ticketId){
    if (confirm("Ви справді бажаєте відмінити бронювання квитка на цей сеанс?")) {
        console.log("button", ticketId)

        fetch('/api/ticket-sales/' + ticketId, {
            method: 'DELETE',
            headers: {
              'Content-Type': 'application/json',
            },
            // body: JSON.stringify(data),
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


function onProfileFormSubmit(){
    
}