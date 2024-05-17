function validateNewUserForm() {
    var firstName = document.getElementById('prenom').value;
    var lastName = document.getElementById('nom').value;
    var password = document.getElementById('password').value;
    var regexLettersOnly = /^[a-zA-ZÀ-ÿ]+$/; // Autorise les lettres sans accent et avec accent
    var regexUppercase = /[A-Z]/; // Regex pour vérifier la présence de majuscules
    var regexLowercase = /[a-z]/; // Regex pour vérifier la présence de minuscules
    var regexDigits = /[0-9]/; // Regex pour vérifier la présence de chiffres
    var regexSpecialChars = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/; // Regex pour vérifier la présence de caractères spéciaux

    if (!regexLettersOnly.test(firstName)) {
        alert("First name must only contain letters");
        return false;// Empêche l'envoi du formulaire si la validation échoue
    }
    if (!regexLettersOnly.test(lastName)) {
        alert("Last name must only contain letters");
        return false;// Empêche l'envoi du formulaire si la validation échoue
    }
    if (password.length < 8) {
        alert("Password must contain at least 8 characters");
        return false; // Empêche l'envoi du formulaire si la validation échoue
    }
    if (!regexUppercase.test(password)) {
        alert("Password must contain at least one uppercase letter");
        return false; // Empêche l'envoi du formulaire si la validation échoue
    }
    if (!regexLowercase.test(password)) {
        alert("Password must contain at least one lowercase letter");
        return false; // Empêche l'envoi du formulaire si la validation échoue
    }
    if (!regexDigits.test(password)) {
        alert("Password must contain at least one digit");
        return false; // Empêche l'envoi du formulaire si la validation échoue
    }
    if (!regexSpecialChars.test(password)) {
        alert("Password must contain at least one special character");
        return false; // Empêche l'envoi du formulaire si la validation échoue

    }
    return true; // Le formulaire est soumis si la validation réussit

}

function validateAdminAuthForm() {
    var password = document.getElementById('password').value;
    var regexLettersOnly = /^[a-zA-ZÀ-ÿ]+$/; // Autorise les lettres sans accent et avec accent
    var regexUppercase = /[A-Z]/; // Regex pour vérifier la présence de majuscules
    var regexLowercase = /[a-z]/; // Regex pour vérifier la présence de minuscules
    var regexDigits = /[0-9]/; // Regex pour vérifier la présence de chiffres
    var regexSpecialChars = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/; // Regex pour vérifier la présence de caractères spéciaux

    if (password.length < 8) {
        alert("Password must contain at least 8 characters");
        return false; // Empêche l'envoi du formulaire si la validation échoue
    }
    if (!regexUppercase.test(password)) {
        alert("Password must contain at least one uppercase letter");
        return false; // Empêche l'envoi du formulaire si la validation échoue
    }
    if (!regexLowercase.test(password)) {
        alert("Password must contain at least one lowercase letter");
        return false; // Empêche l'envoi du formulaire si la validation échoue
    }
    if (!regexDigits.test(password)) {
        alert("Password must contain at least one digit");
        return false; // Empêche l'envoi du formulaire si la validation échoue
    }
    if (!regexSpecialChars.test(password)) {
        alert("Password must contain at least one special character");
        return false; // Empêche l'envoi du formulaire si la validation échoue
    }
    return true; // Le formulaire est soumis si la validation réussit
}