var backButton = document.getElementById('back-button');

backButton.onclick = function () {
    window.location.href = 'controller?command=show_contact_list';
};
