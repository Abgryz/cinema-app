let url = window.location.pathname;
let id = url.substring(url.lastIndexOf('/') + 1);
fetch("/api/schedule/" + id)
    .then(response => response.json())
    .then(data => {
        console.log(data)
        const tbody = document.querySelector(".seats-tbody");
        currentRow = 0;
        data.forEach(seatData => {
            if (currentRow == seatData.seat.row) {
                const lastRow = document.querySelector(".seats-tbody tr:last-child")
                tableCreater(lastRow, seatData)
          
            } else {
                currentRow = seatData.seat.row
                const row = document.createElement('tr')
                row.classList.add("table-row")

                rowCounter = document.createElement("td")
                rowCounter.textContent = "Ряд " + currentRow

                row.appendChild(rowCounter)

                tableCreater(row, seatData)
                tbody.appendChild(row)
            }
          });
    })
    .then(() => {
        const buttons = document.querySelectorAll(".seat")
        buttons.forEach(button => {
            button.addEventListener('click', () => {
                onSeatButtonClick(button)

                buttons.forEach(button => button.classList.remove("active"))
                button.classList.add("active")
            });
        })
    })
    // .then(() => {
    //     const form = document.querySelector("#seat-form")
    //     form.action = form.action + "/" + id
    // })

function tableCreater(row, seatData){
    const rowCell = document.createElement('td')
    const button = buttonCreater(seatData)
    rowCell.appendChild(button)
    row.appendChild(rowCell) 
}

function buttonCreater(seatData){
    const button = document.createElement('button')
    button.setAttribute("id", seatData.seat.id)
    button.innerHTML = seatData.seat.seatNumber
    if(seatData.isBusy == true){
        button.disabled = true
        button.classList.add("busy")
    } else{
        button.innerHTML += "<br><span class='price'>" + seatData.ticket.price * seatData.seat.priceCoefficient + "грн<span>"
    }
    button.classList.add("seat")
    return button
}

function onSeatButtonClick(button){

    if(confirm("Ви дійсно бажаєте забронювати квиток на обране вами місце?")){
        fetch('/api/schedule/' + id + "?seatId=" + button.id, {method: 'POST'})
        .then(response => {
            if (response.ok) {
                button.disabled = true
                button.classList.add("busy")
                button.classList.remove("active")
            } else {
                alert("Виникла помилка!")
            }
          })
        .catch(error => {console.log(error)});
    }


    // const form = document.getElementById("seat-form")
    // form.classList.remove("disabled")
    // const div = document.getElementById("seat-form-div")
    // div.classList.remove("disabled")

    // const label = document.getElementById("form-label")
    // label.textContent = "Ви дійсно бажаєте забронювати квиток на обране вами місце?"

    // const hidden = document.getElementById("seatId")
    // hidden.value = button.id

    // const cancelButton = document.getElementById("cancel-button")
    // cancelButton.addEventListener('click', () => {
    //     form.classList.add("disabled")
    //     div.classList.add("disabled")
    //     button.classList.remove("active")
    // })
}