function validateNewUserForm() {
    var firstName = document.getElementById('firstname').value;
    var lastName = document.getElementById('lastname').value;
    var password = document.getElementById('password').value;
    var regexLettersOnly = /^[a-zA-ZÀ-ÿ]+$/;
    var regexUppercase = /[A-Z]/;
    var regexLowercase = /[a-z]/;
    var regexDigits = /[0-9]/;
    var regexSpecialChars = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/;

    if (!regexLettersOnly.test(firstName)) {
        alert("First name must only contain letters");
        return false;
    }
    if (!regexLettersOnly.test(lastName)) {
        alert("Last name must only contain letters");
        return false;
    }
    if (password.length < 8) {
        alert("Password must contain at least 8 characters");
        return false;
    }
    if (!regexUppercase.test(password)) {
        alert("Password must contain at least one uppercase letter");
        return false;
    }
    if (!regexLowercase.test(password)) {
        alert("Password must contain at least one lowercase letter");
        return false;
    }
    if (!regexDigits.test(password)) {
        alert("Password must contain at least one digit");
        return false;
    }
    if (!regexSpecialChars.test(password)) {
        alert("Password must contain at least one special character");
        return false;

    }
    return true;

}

function validateAdminAuthForm() {
    var password = document.getElementById('password').value;
    var regexLettersOnly = /^[a-zA-ZÀ-ÿ]+$/;
    var regexUppercase = /[A-Z]/;
    var regexLowercase = /[a-z]/;
    var regexDigits = /[0-9]/;
    var regexSpecialChars = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/;

    if (password.length < 8) {
        alert("Password must contain at least 8 characters");
        return false;
    }
    if (!regexUppercase.test(password)) {
        alert("Password must contain at least one uppercase letter");
        return false;
    }
    if (!regexLowercase.test(password)) {
        alert("Password must contain at least one lowercase letter");
        return false;
    }
    if (!regexDigits.test(password)) {
        alert("Password must contain at least one digit");
        return false;
    }
    if (!regexSpecialChars.test(password)) {
        alert("Password must contain at least one special character");
        return false;
    }
    return true;
}
document.getElementById('forgotPasswordBtn').addEventListener('click', function() {
    document.getElementById('resetPasswordForm').style.display = 'block';
});


