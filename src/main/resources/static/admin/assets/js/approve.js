$(document).ready(function() {
    // обработчик события на ячейку с статусом "Pending"
    $('td.approval-status').on('click', function() {
        // получаем ID заказа
        var orderId = $(this).data('order-id');
        // создаем выпадающий список
        var select = $('<select>');
        // добавляем варианты выбора
        $('<option>').val('WAITING_FOR_APPROVAL').text('Pending').appendTo(select);
        $('<option>').val('APPROVED').text('Approved').appendTo(select);
        $('<option>').val('REJECTED').text('Rejected').appendTo(select);
        // заменяем содержимое ячейки на выпадающий список
        $(this).html(select);
        // устанавливаем значение выпадающего списка равным текущему статусу
        select.val($(this).data('status'));
        // обработчик события на выбор значения в списке
        select.on('change', function() {
            // отправляем AJAX-запрос на сервер для обновления статуса заказа
            $.ajax({
                url: '/updateApprovalStatus',
                type: 'POST',
                data: {orderId: orderId, status: $(this).val()},
                success: function() {
                    // если обновление прошло успешно, заменяем содержимое ячейки на соответствующий бейдж
                    switch ($(this).val()) {
                        case 'WAITING_FOR_APPROVAL':
                            $(this).html('<div class="badge badge-outline-warning">Pending</div>');
                            break;
                        case 'APPROVED':
                            $(this).html('<div class="badge badge-outline-success">Approved</div>');
                            break;
                        case 'REJECTED':
                            $(this).html('<div class="badge badge-outline-danger">Rejected</div>');
                            break;
                    }
                }
            });
        });
    });
});