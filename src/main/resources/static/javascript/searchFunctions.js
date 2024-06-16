function search() {
    // Declare variables
    console.log("new input")
    var input, filter, ul, li, a, i, txtValue;
    input = document.getElementById('myInput');
    filter = input.value.toUpperCase();
    ul = document.getElementById("myUL");
    li = ul.getElementsByTagName('li');
    console.log(li.length)
    // Loop through all list items, and hide those who don't match the search query
    for (i = 0; i < li.length; i++) {
        span = li[i].getElementsByTagName("span")[3];
        console.log(span)
        txtValue = span.textContent || span.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            console.log("show" + txtValue)

            li[i].style.display = "block";
        } else {
            li[i].style.display = "none";
            console.log("hide" + txtValue)

        }
    }
}