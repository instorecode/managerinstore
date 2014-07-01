jQuery(document).ready(function() {

    $.browserName = null;

    if (/*@cc_on!@*/false) {
        $.browserName = 'ie';
    }
    
    if (window.chrome) {
        $.browserName = 'chrome';
    }

    if (window.opera) {
        $.browserName = 'opera';
    }

    if ('MozBoxSizing' in document.body.style) {
        $.browserName = 'mozilla';
    }
    
    if($.browserName == 'ie') {
        jQuery('.browserRec').show();
    }

    $('[data-clock="true"]').each(function() {
        var self = jQuery(this);
        var format = self.data('clockFormat');
        self.clockface({format: format});
    });

    $('[data-selectradio="true"]').each(function() {
        var self = jQuery(this);
        self.multiselect({
            dropRight: self.data('dropRight') || false,
            nonSelectedText: 'Selecione uma opção',
            nSelectedText: 'iten(s) selecionado(s)',
            enableFiltering: true
        });
    });

    $('[data-multiple="multiple"]').each(function() {
        var self = jQuery(this);
        self.multiselect({
            dropRight: self.data('dropRight') || false,
            numberDisplayed: 0,
            nonSelectedText: 'Selecione uma opção',
            nSelectedText: 'iten(s) selecionado(s)',
            enableFiltering: true
        });
    });

    jQuery('.cepload').on('blur', function() {
        var self = jQuery(this);
        var valueOrig = self.val();
        var value = self.val();
        var url = self.data('url');

        value = value.replace(".", "");
        value = value.replace("-", "");

        jQuery.ajax({
            type: 'GET',
            url: url,
            data: {cep: value},
            success: function(data) {
                self.val(valueOrig);
                jQuery('[data-uf="' + data.uf + '"]').attr('selected', true);
                jQuery('.cid').val(data.cidade);
                jQuery('.bai').val(data.bairro);
                jQuery('.log').val(data.logradouro);
            }
        });
    });
    (function($) {
        $.extend(true, $.validator, {
            prototype: {
                defaultShowErrors: function() {
                    var self = this;
                    $.each(this.successList, function(index, value) {
                        $(value).removeClass(self.settings.errorClass).addClass(self.settings.validClass).tooltip('destroy');
                    });
                    $.each(this.errorList, function(index, value) {
                        $(value.element).addClass('tooltip-error');
                        $(value.element).removeClass(self.settings.validClass).addClass(self.settings.errorClass).tooltip('destroy').tooltip(self.apply_tooltip_options(value.element, value.message)).tooltip('show');
                        return false;
                    });
                },
                apply_tooltip_options: function(element, message) {
                    var options = {
                        animation: $(element).data('animation') || true,
                        html: true,
                        placement: $(element).data('placement') || 'top',
                        selector: $(element).data('animation') || true,
                        title: $(element).attr('title') || message,
                        trigger: $.trim('manual ' + ($(element).data('trigger') || '')),
                        delay: $(element).data('delay') || 0,
                        container: $(element).data('container') || false
                    };
                    if (this.settings.tooltip_options && this.settings.tooltip_options[element.name]) {
                        $.extend(options, this.settings.tooltip_options[element.name]);
                    }
                    return options;
                },
            }
        });
    }(jQuery));

    jQuery('[data-tooltip="true"]').each(function() {
        var self = jQuery(this);
        self.tooltip();
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
    $(".datepicker").mask('99/99/9999')

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
                    error: function(data) {
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
