var stompClient = null;
var myUsername = null;
var myFlag = null;
var myPieceColor = null;
var otherPieceColor = null;

function connect() {
    var socket = new SockJS('/reversi');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        register();
        $("#start").prop("disabled", true);
        stompClient.subscribe('/topic/start', function (playRoom) {
            initial(JSON.parse(playRoom.body));
        });
        stompClient.subscribe('/topic/move', function (playRoom) {
            doMove(JSON.parse(playRoom.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    $("#start").prop("disabled", false);
    console.log("Disconnected");
}

function register() {
    myUsername = $("#username").val();
    stompClient.send("/game/register", {}, JSON.stringify({'username': myUsername}));
}

function initial(playRoom) {
    var board = $("#board");
    board.empty();
    if (playRoom.playerA !== null && playRoom.playerB !== null) {
        myFlag = playRoom.playerA.name === myUsername ? playRoom.playerA.flag : playRoom.playerB.flag;
        myPieceColor = myFlag === 1 ? 'white' : 'black';
        otherPieceColor = myFlag === 1 ? 'black' : 'white';
    }
    for (var i = 0; i < 8; i++) {
        board.append("<div id='row-" + i + "' class='row'></div>");
        for (var j = 0; j < 8; j++) {
            var pieceColor = '';
            if (playRoom.board[i][j] === myFlag) {
                pieceColor = myPieceColor;
            } else if (playRoom.board[i][j] === -myFlag) {
                pieceColor = otherPieceColor;
            }
            var nextClass = (playRoom.next[i][j] === 2*myFlag ? 'next' : '');
            $("#row-"+i).append(
                "<div id='box-" + i + j + "' class='box " + pieceColor + ' ' + nextClass + "'>" +
                    "<div class='flip-circle'>" +
                        "<div class='black-circle'></div>" +
                        "<div class='white-circle'></div>" +
                    "</div>" +
                "</div>"
            );
        }
    }
}

function move(box) {
    var id = box.attr('id');
    var x = parseInt(id[4]);
    var y = parseInt(id[5]);
    stompClient.send("/game/move", {}, JSON.stringify({'player': myFlag, 'x': x, 'y': y}));
}

function doMove(playRoom) {
    for (var i = 0; i < 8; i++) {
        for (var j = 0; j < 8; j++) {
            var box = $('#box-'+i+j);
            box.removeClass('next');
            if (playRoom.next[i][j] === 2*myFlag) {
                box.addClass('next');
            }
            if (playRoom.board[i][j] === myFlag && box.attr('class').indexOf(myPieceColor) === -1) {
                box.removeClass(otherPieceColor);
                box.addClass(myPieceColor);
            } else if (playRoom.board[i][j] === -myFlag && box.attr('class').indexOf(otherPieceColor) === -1) {
                box.removeClass(myPieceColor);
                box.addClass(otherPieceColor);
            }
        }
    }
    if (playRoom.finished === true) {
        disconnect();
    }
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#start" ).click(function() { connect(); });
    $( ".box.next" ).click(function() { move($(this)); });
});

