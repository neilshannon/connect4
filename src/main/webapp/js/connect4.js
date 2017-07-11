
function drawBoard(grid){
    for(var i=0; i<42; i++){
        var row = rowFromIndex(i);
        var cellContainer = grid[i];
        var cellValue = cellContainer.cell;
        var selector = "#r"+row+" td";
        var columns = $(selector);
        var cell = $(columns.get(columnFromIndex(i)));

        if(cellValue === "[R]"){
            cell.removeClass("blankCell");
            cell.addClass("redCell");
        } else if(cellValue === "[B]"){
            cell.removeClass("blankCell");
            cell.addClass("blackCell");
        } else if(cellValue === "[-]"){
            if(!cell.hasClass("blankCell")){
                cell.removeClass("redCell");
                cell.removeClass("blackCell");
                cell.addClass("blankCell");
            }
        }
    }
}

function rowFromIndex(index){
    var row = 0;
    if(index > 6){
        row = Math.floor(index / 7);
    }
    return row;
}

function serializeGrid(){
    var gridArray = [];
    $("#board tr td").each(function(index, td){ var obj = {"cell": buildCell(td)}; gridArray.push(obj);});
    return JSON.stringify(gridArray);
}

function buildCell(td){
    var wrappedTd = $(td);
    if(wrappedTd.hasClass("redCell")){
        return "[R]";
    } else if(wrappedTd.hasClass("blackCell")){
        return "[B]";
    } else {
        return "[-]";
    }
}

function columnFromIndex(index){
    return index % 7;
}

function initializeGame(){
    $('td').click(function(e){
        var classes = $(e.target).attr("class").split(' ');
        var regex = /col([\d]+)/;
        $(classes).each(function(index, className){
            var matchFound = className.match(regex);
            if(matchFound){
                makeMove(matchFound[1], serializeGrid());
            }
        });
    });
    startGame();
}

function hideWinners(){
    $('#newGame').hide();
    $('#winningBanner').hide();
}

function startGame() {
    $.ajax('/api/startGame').done(function (grid) {
        hideWinners();
        drawBoard(grid, "");
        serializeGrid();
    });
}

function makeMove(column, grid){
    $.ajax({
        type: "POST",
        url: "/api/makeMove?column=" + column,
        processData: false,
        contentType: 'application/json',
        data: grid,
        success: function(grid, textStatus, request) {
            drawBoard(grid);
            var winningPlayer = request.getResponseHeader('Winning-Player');
            if(winningPlayer !== ""){
                if(winningPlayer === "RED"){
                    win("Red");
                } else if (winningPlayer === "BLACK"){
                    win("Black");
                } else if (winningPlayer === "DRAW"){
                    draw();
                }
            }
      }
    });
}

function win(player){
    showBanner(player + " WINS!!!")
}

function draw(){
    showBanner("IT'S A DRAW!!!")
}

function showBanner(text){
    var banner = $('#winningBanner');
    banner.text(text);
    banner.show();
    showNewGame();
}

function showNewGame(){
    $('#newGame').show();
}