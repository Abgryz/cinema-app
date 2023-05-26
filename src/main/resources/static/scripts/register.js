function checkPasswords() {
    if (document.getElementById("password").value !== document.getElementById("repeat-password").value) {
      console.log("error")
      alert("Passwords do not match. Please try again.");
      return false;
    }
    return true;
}


function redirectToLogin(){
    window.location.assign("/login");
}