const halls = document.getElementById('genres');
const url = window.location.pathname;
const id = url.substring(url.lastIndexOf('/') + 1);

fetch('/api/genres') 
  .then(response => response.json())
  .then(data => {
    halls.innerHTML = '';
    
    data.forEach(genre => {
      const option = document.createElement('option');
      option.value = genre.name;
      option.textContent = genre.name;
      option.id = genre.name;
      halls.appendChild(option);
    });
  })
  .then(() => {
    if(!isNaN(id)){
      fetch('/api/films/' + id)
        .then(response => response.json())
        .then(data => {
          const genres = data.filmGenres
          genres.forEach(genre => {
            document.getElementById(genre).selected = true
          })
        })
    }
  })

