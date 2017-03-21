var addPhoneButton = document.getElementById('add-phone-button');
var phonePopup = document.getElementById('phone-popup');
var cancelPhoneButton = document.getElementById('cancel-phone-button');

addPhoneButton.onclick = function() {
    phonePopup.style.display = 'block';
}

cancelPhoneButton.onclick = function() {
    phonePopup.style.display = 'none';
}