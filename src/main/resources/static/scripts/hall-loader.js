const halls = document.getElementById('hall');

fetch('/api/admins/cinemashows') 
  .then((response) => response.json())
  .then((data) => {
    halls.innerHTML = '';
    data.forEach(hall => {
      const option = document.createElement('option');
      option.value = hall.id;
      option.textContent = hall.id;
      halls.appendChild(option);
    });
  })
