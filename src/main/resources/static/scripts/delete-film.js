// const url = window.location.pathname;
// const id = url.substring(url.lastIndexOf('/') + 1);

function onDeleteFilmButtonClick(){
    createConfirm("Ви дійсно бажаєте видалити цей фільм?", (res) => {
        if(res){
            fetch('/api/admins/films/' + id, {method: "DELETE"})
            .then(response => {
                if(response.ok){
                    createAlert("Фільм успішно видалено!", () => window.location.assign("/films"))
                } else{
                    response.text().then(text => createAlert(text))
                }
            })
        }
    })
}