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

var emailSubject = document.getElementById('email-subject');
var emailText = document.getElementById('email-text');
var emailTemplateSelect = document.getElementById('email-template');
var emailTextArea = document.getElementById('email-text');
var sendEmailButton = document.getElementById('send-email-button');
var sendEmailForm = document.getElementById('send-email');

emailTemplateSelect.onchange = function () {
    var selectedIndex = emailTemplateSelect.selectedIndex;
    emailTextArea.disabled = selectedIndex > 0;
    if (emailTextArea.disabled) {
        emailTextArea.value = document.getElementById(emailTemplateSelect[selectedIndex].value).value;
    } else {
        emailTextArea.value = '';
    }
};


sendEmailButton.onclick = function () {
    if (!checkFieldsBeforeSubmit()) {
        openModalWindow(errorMessagePopup);
        return;
    }
    emailSubject.value = emailSubject.value.trim();
    emailText.value = emailText.value.trim();
    sendEmailForm.submit();
};

function checkFieldsBeforeSubmit() {
    if (document.getElementsByName('recipient-id').length === 0) {
        addErrorMessage('У выбранных контактов не указан email.')
        return false;
    }
    var message;
    if ((message = checkFieldLength(emailSubject, 80)) != '') {
        addErrorMessage(message);
    }
    if (emailTemplateSelect.selectedIndex === 0) {
        if ((message = checkFieldLength(emailText, 254)) != '') {
            addErrorMessage(message);
        }
    }
    return message === '';
}

emailSubject.onkeyup = function () {
    if (checkFieldLength(this, 80) === '') {
        highlightInput(this, true);
    } else {
        highlightInput(this, false);
    }
};

emailText.onkeyup = function () {
    if (emailTemplateSelect.selectedIndex === 0) {
        if (checkFieldLength(this, 254) === '') {
            highlightInput(this, true);
        } else {
            highlightInput(this, false);
        }
    }
};

function checkFieldLength(field, maxLength) {
    var length = field.value.trim().length;
    var displayFieldName = field.parentNode.textContent.trim();
    if (length < 1) {
        return 'Поле "' + displayFieldName + '" должно быть заполнено.';
    }
    if (length > maxLength) {
        return 'Длина поля "' + displayFieldName + '" должна быть не больше ' + maxLength +' символов.';
    }
    return '';
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