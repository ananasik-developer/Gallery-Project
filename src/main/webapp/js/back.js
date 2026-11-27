(function () {
    var buttons = document.querySelectorAll('.back-button');

    for (var i = 0; i < buttons.length; i++) {
        buttons[i].addEventListener('click', function () {
            history.back();
        });
    }
})();
