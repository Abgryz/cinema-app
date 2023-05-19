const rentalDateInput = document.getElementById("rentalDate");
const currentDate = new Date().toISOString().split("T")[0];
rentalDateInput.setAttribute("max", currentDate);