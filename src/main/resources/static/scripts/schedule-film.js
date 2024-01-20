let url = window.location.pathname;
let id = url.substring(url.lastIndexOf('/') + 1);
fetch("/api/schedule/films/" + id)
    .then(response => response.json())
    .then(data => {
        console.log(data)
        let films = data.film
        let cinemaShows = data.cinemaShows
        let halls = data.halls


        const tbody = document.querySelector("#schedule-body");

        // Создаем строки таблицы и заполняем их данными из массива
        cinemaShows.forEach(cinemaShow => {
            const row = document.createElement("tr");

            const movieCell = document.createElement("td");
            movieCell.innerText = data.film.filmName;
            row.appendChild(movieCell);

            const dateCell = document.createElement("td");
            dateCell.innerText = cinemaShow.dateAndTime.replace("T", "\n");
            row.appendChild(dateCell);

            const durationCell = document.createElement("td");
            durationCell.innerText = data.film.filmDuration;
            row.appendChild(durationCell);

            const hallCell = document.createElement("td");
            hallCell.innerText = cinemaShow.hallId;
            row.appendChild(hallCell);

            const typeCell = document.createElement("td");
            typeCell.innerText = getHallType(halls, cinemaShow.hallId);
            row.appendChild(typeCell);

            const buttonCell = document.createElement("td");
            const button = document.createElement("a");
            button.innerText = "Бронювати квиток";
            button.href = "/schedule/" + cinemaShow.id;
            button.classList.add("ticket-button");
            buttonCell.appendChild(button);
            row.appendChild(buttonCell);

            tbody.appendChild(row);
        });
    })

function getHallType(halls, id){
    let str
    halls.forEach(hall =>{
        if (hall.id == id)
            str = hall.type
    })
    return str
}

