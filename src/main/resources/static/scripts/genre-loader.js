const genres = document.getElementById('genres');

fetch('/api/admins/films') 
  .then((response) => response.json())
  .then((data) => {
    genres.innerHTML = '';
    
    data.forEach(genre => {
      const option = document.createElement('option');
      option.value = genre.name;
      option.textContent = genre.name;
      genres.appendChild(option);
    });
  })
