    function toggleResetPasswordForm() {
    var form = document.getElementById('resetPasswordForm');
    form.style.display = (form.style.display === 'none' || form.style.display === '') ? 'block' : 'none';
}
    document.getElementById('resetPage').addEventListener('click', function() {
    window.location.href = 'http://localhost:8080/AdminController/resetPassword';
});

    function sendResetLink() {
    var email = document.getElementById('resetEmail').value;
    console.log("mail a été envoyé ", email);
}
