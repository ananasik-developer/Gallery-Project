(function () {
    var track = document.getElementById('galleryStrip');
    if (!track) {
        return;
    }

    var nextButton = document.querySelector('.strip-next');
    var prevButton = document.querySelector('.strip-prev');
    var timer = null;

    function step() {
        var slide = track.querySelector('.strip-slide');
        return slide ? slide.offsetWidth + 16 : 320;
    }

    function atEnd() {
        return track.scrollLeft + track.clientWidth >= track.scrollWidth - 4;
    }

    function next() {
        if (atEnd()) {
            track.scrollTo({left: 0, behavior: 'smooth'});
        } else {
            track.scrollBy({left: step(), behavior: 'smooth'});
        }
    }

    function prev() {
        if (track.scrollLeft <= 4) {
            track.scrollTo({left: track.scrollWidth, behavior: 'smooth'});
        } else {
            track.scrollBy({left: -step(), behavior: 'smooth'});
        }
    }

    function start() {
        timer = window.setInterval(next, 4000);
    }

    function stop() {
        if (timer) {
            window.clearInterval(timer);
            timer = null;
        }
    }

    if (nextButton) {
        nextButton.addEventListener('click', next);
    }

    if (prevButton) {
        prevButton.addEventListener('click', prev);
    }

    track.addEventListener('mouseenter', stop);
    track.addEventListener('mouseleave', start);

    start();
})();
