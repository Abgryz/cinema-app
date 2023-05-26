function onDeleteShowButtonClick(){
    if (confirm("Ви дійсно бажаєте видалити цей сеанс?")) {
        fetch('/api/admins/shows/' + id, {method: "DELETE"})
        .then(response => {
            if(response.ok){
                alert("Сеанс видалено!")
                window.location.assign("/admins/shows");
            } else{
                alert("Виникла помилка під час видалення сеансу!")
            }
        })
    } else {
        return false;
    }
}