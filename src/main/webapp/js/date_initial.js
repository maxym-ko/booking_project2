const today = new Date();
document.getElementById('check_in_date').min = today.toISOString().split("T")[0];
document.getElementById('check_out_date').min = today.toISOString().split("T")[0];

today.setFullYear(today.getFullYear() + 1);
document.getElementById('check_in_date').max = today.toISOString().split("T")[0];
document.getElementById('check_out_date').max = today.toISOString().split("T")[0];

document.getElementById('check_in_date').onchange = function () {
    document.getElementById('check_out_date').min = this.value;
};