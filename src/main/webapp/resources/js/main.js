jQuery(document).ready(function() {
    jQuery.validator.setDefaults({
        invalidHandler: function(form, validator) {
            var errors = validator.numberOfInvalids();
            if (errors) {
                var message =' <ul></ul>';
                $("#messageBox").html(message);
                $("#messageBox").show();
                
            } else {
                $("#messageBox").hide();
            }
        },
        showErrors: function(errorMap, errorList) {
            $("#messageBox ul").html('');    
            jQuery.each(errorList , function(){
                $("#messageBox ul").append('<li><i class="fa fa-exclamation-triangle"></i>&nbsp;&nbsp;'+this.message+'</li>');
            });
        }
    });
    /* Brazilian initialisation for the jQuery UI date picker plugin. */
    /* Written by Leonildo Costa Silva (leocsilva@gmail.com). */
    $.datepicker.regional['pt-BR'] = {
        closeText: 'Fechar',
        prevText: '&#x3c;Anterior',
        nextText: 'Pr&oacute;ximo&#x3e;',
        currentText: 'Hoje',
        monthNames: ['Janeiro', 'Fevereiro', 'Mar&ccedil;o', 'Abril', 'Maio', 'Junho',
            'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
        monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun',
            'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
        dayNames: ['Domingo', 'Segunda-feira', 'Ter&ccedil;a-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sabado'],
        dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],
        dayNamesMin: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],
        weekHeader: 'Sm',
        dateFormat: 'dd/mm/yy',
        firstDay: 0,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: ''};
    $.datepicker.setDefaults($.datepicker.regional['pt-BR']);

    $(".datepicker").datepicker({
        changeMonth: true,
        changeYear: true
    });

    jQuery('.menuleft').resizable({handles: 'e'});
    formProccess();

    var docw = jQuery(window).width();
    var doch = jQuery(window).height();

    resize();
    jQuery(window).resize(function() {
        resize();
    });

    function resize() {
        docw = jQuery(window).width();
        doch = jQuery(window).height();

        jQuery('.menuleft').css({
            'height': doch + 'px'
        });

        jQuery('.content .over').css({
            'height': (doch - 140) + 'px',
        });

        jQuery('.content').css({
            'width': (docw - (5 + jQuery('.menuleft').width())) + 'px',
            'height': (doch) + 'px',
            'margin-left': ((5 + jQuery('.menuleft').width())) + 'px'
        });
    }

    jQuery('.btn_sair').on('click', function() {
        jQuery.ajax({
            type: 'GET',
            url: jQuery(this).attr('href'),
            dataType: 'json',
            beforeSend: function() {
                bootbox.hideAll();
                bootbox.dialog({
                    message: "Aguarde...",
                    title: "Sistema processando informações",
                    buttons: {}
                });
            },
            success: function() {
                window.location.reload();
            }
        });
        return false;
    });

    jQuery('[dropdown-toggle="true"]').dropdown();
    jQuery('[data-mask]').each(function() {
        jQuery(this).mask(jQuery(this).data('mask'));
    });
});

function formProccess() {
    jQuery.extend(jQuery.validator.messages, {
        required: "Este campo &eacute; obrigatório.",
        remote: "Por favor, corrija este campo.",
        email: "Por favor, forne&ccedil;a um endere&ccedil;o eletr&ocirc;nico v&aacute;lido.",
        url: "Por favor, forne&ccedil;a uma URL v&aacute;lida.",
        date: "Por favor, forne&ccedil;a uma data v&aacute;lida.",
        dateISO: "Por favor, forne&ccedil;a uma data v&aacute;lida (ISO).",
        dateDE: "Bitte geben Sie ein gültiges Datum ein.",
        number: "Por favor, forne&ccedil;a um n&uacute;mero v&aacute;lida.",
        numberDE: "Bitte geben Sie eine Nummer ein.",
        digits: "Por favor, forne&ccedil;a somente d&iacute;gitos.",
        creditcard: "Por favor, forne&ccedil;a um cart&atilde;o de cr&eacute;dito v&aacute;lido.",
        equalTo: "Por favor, forne&ccedil;a o mesmo valor novamente.",
        accept: "Por favor, forne&ccedil;a um valor com uma extens&atilde;o v&aacute;lida.",
        maxlength: jQuery.validator.format("Por favor, forne&ccedil;a n&atilde;o mais que {0} caracteres."),
        minlength: jQuery.validator.format("Por favor, forne&ccedil;a ao menos {0} caracteres."),
        rangelength: jQuery.validator.format("Por favor, forne&ccedil;a um valor entre {0} e {1} caracteres de comprimento."),
        range: jQuery.validator.format("Por favor, forne&ccedil;a um valor entre {0} e {1}."),
        max: jQuery.validator.format("Por favor, forne&ccedil;a um valor menor ou igual a {0}."),
        min: jQuery.validator.format("Por favor, forne&ccedil;a um valor maior ou igual a {0}.")
    });

    jQuery('[data-form="true"]').each(function() {
        var form = jQuery(this);
        form.validate();

        form.on('submit', function() {
            if (form.valid()) {
                var data_success_url = form.data('successUrl');
                var data_error_url = form.data('errorUrl');

                jQuery.ajax({
                    async: true,
                    type: form.attr('method'),
                    url: form.attr('action'),
                    data: form.serialize(),
                    dataType: 'json',
                    beforeSend: function() {
                        bootbox.hideAll();
                        bootbox.dialog({
                            message: "Aguarde...",
                            title: "Sistema processando informações",
                            buttons: {}
                        });
                    },
                    success: function(data) {
                        if (data.success) {
                            if (data_success_url != null && data_success_url != undefined && data_success_url != '') {
                                window.location.href = data_success_url;
                            } else {
                                dialogAjax(data.response);
                            }
                        } else {
                            dialogAjax(data.response);
                        }
                    },
                    error: function(response) {
                        if (data_error_url != null && data_error_url != undefined && data_error_url != '') {
                            window.location.href = data_error_url;
                        } else {
                            dialogAjax(data.response);
                        }
                    }
                });
            }
            return false;
        });

    });
}

function dialogAjax(msg) {
    bootbox.hideAll();
    bootbox.dialog({
        message: msg,
        title: "Sistema processando informações",
        buttons: {}
    });

    setTimeout(function() {
        bootbox.hideAll();
    }, 2000);
}
