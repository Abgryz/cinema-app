function onCancelButtonClick(ticketId){
    if (confirm("Ви справді бажаєте відмінити бронювання квитка?")) {
        console.log("button", ticketId)

        fetch('/api/admins/cancel-booking/' + ticketId, {
            method: 'DELETE',
            headers: {
              'Content-Type': 'application/json',
            },
            // body: JSON.stringify(data),
        })
        .then(response => {
            console.log(response)
        })
        .catch(error => {
            console.log(error)
        });
    }
}