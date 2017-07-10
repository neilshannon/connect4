
function drawBoard(grid){
    for(var i=0; i<42; i++){
        var row = 0;
        var cellContainer = grid[i];
        var cellValue = cellContainer.cell;
        var columns = $('#r'+i+" td");

        if(cellValue === "[R]"){
            columns.get(columnFromIndex(i)).removeClass("blankCell");
            columns.get(columnFromIndex(i)).addClass("redCell");
        } else if(cellValue === "[B]"){
            columns.get(columnFromIndex(i)).removeClass("blankCell");
            columns.get(columnFromIndex(i)).addClass("blackCell");
        }

        if(i%7 === 0 && i >= 7){
            row++;
        }
    }
}

function columnFromIndex(index){
    return index % 7;
}

function startGame() {
    $.ajax('/api/startGame').done(function (grid) {
        drawBoard(grid);
    });
}