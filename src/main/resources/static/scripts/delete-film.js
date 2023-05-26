// const url = window.location.pathname;
// const id = url.substring(url.lastIndexOf('/') + 1);

function onDeleteFilmButtonClick(){
    if (confirm("Ви дійсно бажаєте видалити цей фільм?")) {
        fetch('/api/admins/films/' + id, {method: "DELETE"})
        .then(response => {
            if(response.ok){
                alert("Фільм видалено!")
                window.location.assign("/admins/films");
            } else{
                alert("Виникла помилка під час видалення фільму!")
            }
        })
    } else {
        return false;
    }
}