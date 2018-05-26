$(function () {
$('#myModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget);
    var id = button.data('id');
    var date = button.data('date');
    var modal = $(this);
    //modal.find('.modal-title').text('New message to ' + id);
    modal.find('.modal-body input').val(id);
    modal.find('.modal-footer input').text('/release?id='+id);
    modal.find('.modal-date').text(date);
});
});