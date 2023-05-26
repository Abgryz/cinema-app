const halls = document.getElementById('hall');
const url = window.location.pathname;
const id = url.substring(url.lastIndexOf('/') + 1);

fetch('/api/halls') 
  .then((response) => response.json())
  .then((data) => {
    halls.innerHTML = '';
    data.forEach(hall => {
      const option = document.createElement('option');
      option.value = hall.id;
      option.textContent = hall.id;
      option.id = "hall" + hall.id
      halls.appendChild(option);
    });
  })
  .then(() => {
    if(!isNaN(id)){
      fetch("/api/shows/" + id)
      .then(response => response.json())
      .then(data => {
        document.getElementById("hall" + data.hallId).selected = true
      })
    }
  })
