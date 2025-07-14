var socket = new SockJS('/ws');
var stompClient = Stomp.over(socket);

stompClient.connect({}, function() {
    stompClient.subscribe('/topic/movie-updates', function(message) {

        var film = JSON.parse(message.body);

        var notification = Lobibox.notify('info', {
            img: `../films/poster/${film.id}`,
            sound: false,
            title: `${film.text} 🎥`,
            msg: `Ya está disponible: ${film.text}, en nuestro inventario. Esperamos que la disfruten 😍. Para ver mas detalles puedes hacer click aquí.`,
            onClick: function () {
            }
        });

        // Agrega un evento al cuerpo de la notificación
        notification.$el.find('.lobibox-notify-body').on('click', function (event) {
          if ($(event.target).hasClass('lobibox-close')) {
            return;
          }
          const modalFilm = document.createElement('modal-film');
          modalFilm.setAttribute('film', film.id);
          document.querySelector('.uabc-content').appendChild(modalFilm);
        });
    });
});