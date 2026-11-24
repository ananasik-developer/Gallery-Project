(function () {
    var button = document.querySelector('.like-button');
    if (!button) {
        return;
    }

    var counter = button.querySelector('.like-count');

    button.addEventListener('click', function () {
        var artworkId = button.getAttribute('data-artwork');
        var token = button.getAttribute('data-token');
        var url = button.getAttribute('data-url');

        button.disabled = true;

        fetch(url, {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: 'artworkId=' + encodeURIComponent(artworkId) + '&csrfToken=' + encodeURIComponent(token)
        })
            .then(function (response) {
                if (!response.ok) {
                    throw new Error('fail');
                }
                return response.json();
            })
            .then(function (data) {
                counter.textContent = data.count;
                if (data.liked) {
                    button.classList.add('active');
                } else {
                    button.classList.remove('active');
                }
                button.disabled = false;
            })
            .catch(function () {
                button.disabled = false;
            });
    });
})();
