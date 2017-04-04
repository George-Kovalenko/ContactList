var errorMessagePopup = document.getElementById('error-message-popup');
var errorMessage = document.getElementById('error-message');
var submitErrorMessageButton = document.getElementById('submit-error-message-button');

function openModalWindow(modalWindow) {
    modalWindow.style.display = 'block';
}

function closeModalWindow(modalWindow) {
    modalWindow.style.display = 'none';
}

submitErrorMessageButton.onclick = function () {
    closeModalWindow(errorMessagePopup);
    errorMessage.innerHTML = null;
};

function addErrorMessage(message) {
    var newError = document.createElement('p');
    newError.innerHTML = message;
    errorMessage.appendChild(newError);
}

var searchContactButton = document.getElementById('search-contact-button');
var contactSearchForm = document.getElementById('contact-search');

searchContactButton.onclick = function () {
    if (checkInputFieldsBeforeSubmit()) {
        openModalWindow(errorMessagePopup);
        return;
    }
    contactSearchForm.submit();
};

var firstName = document.getElementById('first-name');
firstName.onkeyup = function () {
    if (checkTextInputField(this, 30, containsLettersHyphen.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

var lastName = document.getElementById('last-name');
lastName.onkeyup = function () {
    if (checkTextInputField(this, 30, containsLettersHyphen.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

var middleName = document.getElementById('middle-name');
middleName.onkeyup = function () {
    if (checkTextInputField(this, 30, containsLettersHyphen.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

var day = document.getElementById('day');
day.onkeyup = function () {
    checkDateInput();
};

var month = document.getElementById('month');
month.onkeyup = function () {
    checkDateInput();
};

var year = document.getElementById('year');
year.onkeyup = function () {
    checkDateInput();
};

var nationality = document.getElementById('nationality');
nationality.onkeyup = function () {
    if (checkTextInputField(this, 45, containsLettersHyphenSpace.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

var country = document.getElementById('country');
country.onkeyup = function () {
    if (checkTextInputField(this, 45, containsLettersHyphenSpace.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

var city = document.getElementById('city');
city.onkeyup = function () {
    if (checkTextInputField(this, 45, containsLettersHyphenSpace.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

var street = document.getElementById('street');
street.onkeyup = function () {
    if (checkTextInputField(this, 45, containsLettersDigitsHyphenSpace.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

var house = document.getElementById('house');
house.onkeyup = function () {
    if (checkTextInputField(this, 10, containsDigits.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

var flat = document.getElementById('flat');
flat.onkeyup = function () {
    if (checkTextInputField(this, 10, containsDigits.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

var postcode = document.getElementById('postcode');
postcode.onkeyup = function () {
    if (checkTextInputField(this, 10, containsDigits.name) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

function checkInputFieldsBeforeSubmit() {
    if (!isAnyFieldEntered()) {
        addErrorMessage('Ни одно поле для поиска не заполнено.')
        return true;
    }
    var errorMessages = [];
    var message;
    if ((message = checkTextInputField(firstName, 30, containsLettersHyphen.name)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(lastName, 30, containsLettersHyphen.name)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(middleName, 30, containsLettersHyphen.name)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkDateInput()) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(nationality, 45, containsLettersHyphenSpace.name)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(country, 45, containsLettersHyphenSpace.name)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(city, 45, containsLettersHyphenSpace.name)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(street, 45, containsLettersDigitsHyphenSpace.name)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(house, 10, containsDigits.name)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(flat, 10, containsDigits.name)) != '') {
        errorMessages.push(message);
    }
    if ((message = checkTextInputField(postcode, 10, containsDigits.name)) != '') {
        errorMessages.push(message);
    }
    errorMessages.forEach(function (item) {
        addErrorMessage(item);
    });
    return errorMessages.length != 0;
}

function isAnyFieldEntered() {
    if (firstName.value.trim().length > 0) {
        return true;
    }
    if (lastName.value.trim().length > 0) {
        return true;
    }
    if (middleName.value.trim().length > 0) {
        return true;
    }
    if (nationality.value.trim().length > 0) {
        return true;
    }
    if (day.value.trim().length > 0) {
        return true;
    }
    if (country.value.trim().length > 0) {
        return true;
    }
    if (city.value.trim().length > 0) {
        return true;
    }
    if (street.value.trim().length > 0) {
        return true;
    }
    if (house.value.trim().length > 0) {
        return true;
    }
    if (flat.value.trim().length > 0) {
        return true;
    }
    if (postcode.value.trim().length > 0) {
        return true;
    }
    return false;
}

function checkTextInputField(inputField, maxLength, checkFunction) {
    var text = inputField.value.trim();
    var displayFieldName = inputField.parentNode.textContent.trim();
    if (text.length > maxLength) {
        return 'Длина поля "' + displayFieldName + '" должна быть не больше ' + maxLength + ' символов.';
    } else if (text.length === 0) {
        return '';
    } else {
        switch (checkFunction) {
            case containsLettersHyphen.name:
                if (!containsLettersHyphen(text)) {
                    return 'Поле "' + displayFieldName + '" может содержать только буквы.';
                }
                break;
            case containsLettersHyphenSpace.name:
                if (!containsLettersHyphenSpace(text)) {
                    return 'Поле "' + displayFieldName + '" может содержать только буквы.';
                }
                break;
            case containsLettersDigitsHyphenSpace.name:
                if (!containsLettersDigitsHyphenSpace(text)) {
                    return 'Поле "' + displayFieldName + '" может содержать только буквы и цифры.';
                }
                break;
            case containsDigits.name:
                if (!containsDigits(text)) {
                    return 'Поле "' + displayFieldName + '" может содержать только цифры.';
                }
                break;
            default:
                break;
        }

    }
    return '';
}

function checkDigitsInputField(inputField, min, max, required) {
    var length = inputField.value.trim().length;
    if (required && length < 1) {
        return false;
    }
    if (!required && length === 0) {
        return true;
    }
    if (containsDigits(inputField.value)) {
        if (inputField.value >= min && inputField.value <= max) {
            return true;
        }
    }
    return false;
}

function checkDateInput() {
    var dayCorrect, monthCorrect, yearCorrect;
    var message = '';
    if (day.value.length > 0 || month.value.length > 0 || year.value.length > 0) {
        dayCorrect = checkDigitsInputField(day, 1, 31, true);
        monthCorrect = checkDigitsInputField(month, 1, 12, true);
        yearCorrect = checkDigitsInputField(year, 1930, new Date().getFullYear(), true);
        highlightInput(day, dayCorrect);
        highlightInput(month, monthCorrect);
        highlightInput(year, yearCorrect);
        if (!dayCorrect) {
            message += 'Поле "День" некорректно заполнено.<br>';
        }
        if (!monthCorrect) {
            message += 'Поле "Месяц" некорректно заполнено.<br>';
        }
        if (!yearCorrect) {
            message += 'Поле "Год" некорректно заполнено.<br>';
        }
        return message;
    } else {
        highlightInput(day, true);
        highlightInput(month, true);
        highlightInput(year, true);
    }
    return message;
}

var alphabet_ru = 'abcdefghijklmnopqrstuvwxyz';
var alphabet_en = 'абвгдеёжзийклмнопрстуфхцчшщъыьэюя';
var digits = '0123456789';
var hyphen = '-';
var space = ' ';

function containsDigits(value) {
    return containsOnlyCharsIgnoreCase(value, digits);
}

function containsLettersHyphen(value) {
    return containsOnlyCharsIgnoreCase(value, alphabet_en + alphabet_ru + hyphen);
}

function containsLettersHyphenSpace(value) {
    return containsOnlyCharsIgnoreCase(value, alphabet_en + alphabet_ru + hyphen + space);
}

function containsLettersDigitsHyphenSpace(value) {
    return containsOnlyCharsIgnoreCase(value, alphabet_en + alphabet_ru + digits + hyphen + space);
}

function containsOnlyCharsIgnoreCase(value, chars) {
    var lowerCaseValue = value.toLowerCase();
    var lowerCaseChars = chars.toLowerCase();
    for (var i = 0; i < value.length; i++) {
        if (lowerCaseChars.indexOf(lowerCaseValue.charAt(i)) == -1) {
            return false;
        }
    }
    return true;
}

function highlightInput(inputElement, isCorrect) {
    if (isCorrect) {
        inputElement.style.borderColor = 'initial';
    } else {
        inputElement.style.borderColor = 'red';
    }
}

document.getElementById('back-button').onclick = function () {
    window.location.href = 'controller?command=show_contact_list&page=1';
};