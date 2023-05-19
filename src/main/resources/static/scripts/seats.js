let url = window.location.pathname;
let id = url.substring(url.lastIndexOf('/') + 1);
fetch("/api/schedule/seats/" + id)
    .then(response => response.json())
    .then(data => {
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
                tableCreater(row, seatData)
                tbody.appendChild(row)
            }
          });
    })
    .then(() => {
        const buttons = document.querySelectorAll(".seat")
        buttons.forEach(button => {
            button.addEventListener('click', () => {
                formLoader(button)

                buttons.forEach(button => button.classList.remove("active"))
                button.classList.add("active")
            });
          })
    })

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

function formLoader(button){
    const form = document.getElementById("seat-form")
    form.className = ""
    const div = document.getElementById("seat-form-div")
    div.className = ""

    const label = document.getElementById("form-label")
    label.textContent = "Ви дійсно бажаєте забронювати квиток на обране вами місце?"

    const hidden = document.getElementById("hidden-id")
    hidden.value = button.id

    const cancelButton = document.getElementById("cancel-button")
    cancelButton.addEventListener('click', () => {
        form.className = "disabled"
        div.className = "disabled"
        button.classList.remove("active")
    })
}