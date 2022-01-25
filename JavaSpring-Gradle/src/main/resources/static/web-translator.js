var cdata;
$.ajax({
   url: "/viewScript",
   dataType: "json",
   method: "get"
}).done(function(data){
    cdata = data;
});

function addRow () {
    var rows = document.querySelectorAll('.execFile');
    if (rows.length < 10) {
        document.querySelector('#add_button').insertAdjacentHTML(
            "beforebegin",
            `<label for="lname" class="execLabel">Executable file (optional)</label>
            <input type="file" class="execFile" name="execFile" accept="py, txt">
            <br class="break">
            <br class="break">
            `      
        )
    } else {
        alert("Too many files");
    }
}

function removeRow () {
    var exec_labels = document.querySelectorAll('.execLabel');
    var exec_file = document.querySelectorAll('.execFile');
    var break_lines = document.querySelectorAll('.break');

    if (exec_file.length > 1) {
        exec_labels[exec_labels.length - 1].remove();
        exec_file[exec_file.length - 1].remove();
        break_lines[break_lines.length - 1].remove();
        break_lines[break_lines.length - 2].remove();
    }
}

