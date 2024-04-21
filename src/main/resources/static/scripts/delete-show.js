function onDeleteShowButtonClick(){
    createConfirm("Ви дійсно бажаєте видалити цей сеанс?", (res) => {
        if (res){
            fetch('/api/admins/shows/' + id, {method: "DELETE"})
            .then(response => {
                if(response.ok){
                    createAlert("Сеанс видалено!", () => window.location.assign("/admins/shows"))
                } else{
                    response.text().then(text => createAlert(text))
                }
            })
        } else {
            return false;
        }
    })
}