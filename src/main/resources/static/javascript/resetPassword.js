//Function used for reset the password
function toggleResetPasswordForm() {
    var form = document.getElementById('resetPasswordForm');
    form.style.display = (form.style.display === 'none' || form.style.display === '') ? 'block' : 'none';
}
    function sendResetLink() {
    var email = document.getElementById('resetEmail').value;
    console.log("mail a été envoyé ", email);
    window.location.href = 'http://localhost:8080/AdminController/resetPassword';
}
