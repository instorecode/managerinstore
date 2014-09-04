!function($) {

    // jshint ;_;


    /* TYPEAHEAD PUBLIC CLASS DEFINITION
     * ================================= */

    var Typeahead = function(element, options) {
        this.$element = $(element)
        this.options = $.extend({}, $.fn.typeahead.defaults, options)
        this.matcher = this.options.matcher || this.matcher
        this.sorter = this.options.sorter || this.sorter
        this.highlighter = this.options.highlighter || this.highlighter
        this.updater = this.options.updater || this.updater
        this.source = this.options.source
        this.$menu = $(this.options.menu)
        this.shown = false
        this.listen()
    }

    Typeahead.prototype = {
        constructor: Typeahead

                , select: function() {
            var val = this.$menu.find('.active').attr('data-value')
            this.$element
                    .val(this.updater(val))
                    .change()
            return this.hide()
        }

        , updater: function(item) {
            return item
        }

        , show: function() {
            var pos = $.extend({}, this.$element.position(), {
                height: this.$element[0].offsetHeight
            })

            this.$menu
                    .insertAfter(this.$element)
                    .css({
                top: pos.top + pos.height
                        , left: pos.left
            })
                    .show()

            this.shown = true
            return this
        }

        , hide: function() {
            this.$menu.hide()
            this.shown = false
            return this
        }

        , lookup: function(event) {
            var items

            this.query = this.$element.val()

            if (!this.query || this.query.length < this.options.minLength) {
                return this.shown ? this.hide() : this
            }

            items = $.isFunction(this.source) ? this.source(this.query, $.proxy(this.process, this)) : this.source

            return items ? this.process(items) : this
        }

        , process: function(items) {
            var that = this

            items = $.grep(items, function(item) {
                return that.matcher(item)
            })

            items = this.sorter(items)

            if (!items.length) {
                return this.shown ? this.hide() : this
            }

            return this.render(items.slice(0, this.options.items)).show()
        }

        , matcher: function(item) {
            return ~item.toLowerCase().indexOf(this.query.toLowerCase())
        }

        , sorter: function(items) {
            var beginswith = []
                    , caseSensitive = []
                    , caseInsensitive = []
                    , item

            while (item = items.shift()) {
                if (!item.toLowerCase().indexOf(this.query.toLowerCase()))
                    beginswith.push(item)
                else if (~item.indexOf(this.query))
                    caseSensitive.push(item)
                else
                    caseInsensitive.push(item)
            }

            return beginswith.concat(caseSensitive, caseInsensitive)
        }

        , highlighter: function(item) {
            var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&')
            return item.replace(new RegExp('(' + query + ')', 'ig'), function($1, match) {
                return '<strong>' + match + '</strong>'
            })
        }

        , render: function(items) {
            var that = this

            items = $(items).map(function(i, item) {
                i = $(that.options.item).attr('data-value', item)
                i.find('a').html(that.highlighter(item))
                return i[0]
            })

            items.first().addClass('active')
            this.$menu.html(items)
            return this
        }

        , next: function(event) {
            var active = this.$menu.find('.active').removeClass('active')
                    , next = active.next()

            if (!next.length) {
                next = $(this.$menu.find('li')[0])
            }

            next.addClass('active')
        }

        , prev: function(event) {
            var active = this.$menu.find('.active').removeClass('active')
                    , prev = active.prev()

            if (!prev.length) {
                prev = this.$menu.find('li').last()
            }

            prev.addClass('active')
        }

        , listen: function() {
            this.$element
                    .on('focus', $.proxy(this.focus, this))
                    .on('blur', $.proxy(this.blur, this))
                    .on('keypress', $.proxy(this.keypress, this))
                    .on('keyup', $.proxy(this.keyup, this))

            if (this.eventSupported('keydown')) {
                this.$element.on('keydown', $.proxy(this.keydown, this))
            }

            this.$menu
                    .on('click', $.proxy(this.click, this))
                    .on('mouseenter', 'li', $.proxy(this.mouseenter, this))
                    .on('mouseleave', 'li', $.proxy(this.mouseleave, this))
        }

        , eventSupported: function(eventName) {
            var isSupported = eventName in this.$element
            if (!isSupported) {
                this.$element.setAttribute(eventName, 'return;')
                isSupported = typeof this.$element[eventName] === 'function'
            }
            return isSupported
        }

        , move: function(e) {
            if (!this.shown)
                return

            switch (e.keyCode) {
                case 9: // tab
                case 13: // enter
                case 27: // escape
                    e.preventDefault()
                    break

                case 38: // up arrow
                    e.preventDefault()
                    this.prev()
                    break

                case 40: // down arrow
                    e.preventDefault()
                    this.next()
                    break
            }

            e.stopPropagation()
        }

        , keydown: function(e) {
            this.suppressKeyPressRepeat = ~$.inArray(e.keyCode, [40, 38, 9, 13, 27])
            this.move(e)
        }

        , keypress: function(e) {
            if (this.suppressKeyPressRepeat)
                return
            this.move(e)
        }

        , keyup: function(e) {
            switch (e.keyCode) {
                case 40: // down arrow
                case 38: // up arrow
                case 16: // shift
                case 17: // ctrl
                case 18: // alt
                    break

                case 9: // tab
                case 13: // enter
                    if (!this.shown)
                        return
                    this.select()
                    break

                case 27: // escape
                    if (!this.shown)
                        return
                    this.hide()
                    break

                default:
                    this.lookup()
            }

            e.stopPropagation()
            e.preventDefault()
        }

        , focus: function(e) {
            this.focused = true
        }

        , blur: function(e) {
            this.focused = false
            if (!this.mousedover && this.shown)
                this.hide()
        }

        , click: function(e) {
            e.stopPropagation()
            e.preventDefault()
            this.select()
            this.$element.focus()
        }

        , mouseenter: function(e) {
            this.mousedover = true
            this.$menu.find('.active').removeClass('active')
            $(e.currentTarget).addClass('active')
        }

        , mouseleave: function(e) {
            this.mousedover = false
            if (!this.focused && this.shown)
                this.hide()
        }

    }


    /* TYPEAHEAD PLUGIN DEFINITION
     * =========================== */

    var old = $.fn.typeahead

    $.fn.typeahead = function(option) {
        return this.each(function() {
            var $this = $(this)
                    , data = $this.data('typeahead')
                    , options = typeof option == 'object' && option
            if (!data)
                $this.data('typeahead', (data = new Typeahead(this, options)))
            if (typeof option == 'string')
                data[option]()
        })
    }

    $.fn.typeahead.defaults = {
        source: []
                , items: 8
                , menu: '<ul class="typeahead dropdown-menu"></ul>'
                , item: '<li><a href="#"></a></li>'
                , minLength: 1
    }

    $.fn.typeahead.Constructor = Typeahead


    /* TYPEAHEAD NO CONFLICT
     * =================== */

    $.fn.typeahead.noConflict = function() {
        $.fn.typeahead = old
        return this
    }


    /* TYPEAHEAD DATA-API
     * ================== */

    $(document).on('focus.typeahead.data-api', '[data-provide="typeahead"]', function(e) {
        var $this = $(this)
        if ($this.data('typeahead'))
            return
        $this.typeahead($this.data())
    })

}(window.jQuery);

jQuery(document).ready(function() {


    jQuery.expr[':'].like = function(selector, i, m) {
        var self = jQuery(selector);
        var text = self.text();
        text = text.replace(/[á|ã|â|à]/gi, "a");
        text = text.replace(/[é|ê|è]/gi, "e");
        text = text.replace(/[í|ì|î]/gi, "i");
        text = text.replace(/[õ|ò|ó|ô]/gi, "o");
        text = text.replace(/[ú|ù|û]/gi, "u");
        text = text.replace(/[ç]/gi, "c");
        text = text.replace(/[ñ]/gi, "n");
        text = text.replace(/[á|ã|â]/gi, "a");

        var like_query = m[m.length - 1];
        var rule_start_char = like_query.substring(0, 1);
        var rule_end_char = like_query.substring(like_query.length, like_query.length - 1);
        var ruleText = like_query.replace('%', '');
        var ruleText = ruleText.replace('%', '');

        ruleText = ruleText.replace(/[á|ã|â|à]/gi, "a");
        ruleText = ruleText.replace(/[é|ê|è]/gi, "e");
        ruleText = ruleText.replace(/[í|ì|î]/gi, "i");
        ruleText = ruleText.replace(/[õ|ò|ó|ô]/gi, "o");
        ruleText = ruleText.replace(/[ú|ù|û]/gi, "u");
        ruleText = ruleText.replace(/[ç]/gi, "c");
        ruleText = ruleText.replace(/[ñ]/gi, "n");
        ruleText = ruleText.replace(/[á|ã|â]/gi, "a");

        var ruleTextUpperCase = ruleText.toUpperCase().trim();
        var ruleTextLowerCase = ruleText.toLowerCase().trim();


        // start
        if (rule_end_char == '%' && rule_start_char != '%') {
            var text_part = text.trim().substring(0, ruleText.length).trim();
            if (text_part == ruleTextUpperCase || text_part == ruleTextLowerCase) {
                return true;
            }
        }

        // end
        if (rule_end_char != '%' && rule_start_char == '%') {
            var text_part = text.trim().substring(text.trim().length - ruleText.length, text.length).trim();
            if (text_part == ruleTextUpperCase || text_part == ruleTextLowerCase) {
                return true;
            }
        }

        // any
        if (rule_end_char == '%' && rule_start_char == '%') {
            text = text.toLowerCase().trim();
            text = text.replace(/\s/g, '');
            ruleTextLowerCase = ruleTextLowerCase.replace(/\s/g, '');
            console.log("regra lowercase: " + ruleTextLowerCase);
            console.log("regra texto: " + text);
            console.log("");
            console.log("");
            if (text.trim().indexOf(ruleTextLowerCase) != -1) {
                return true;
            } else {
                return false;
            }
        }

    };

    formProccess();
    jQuery('[data-mask]').each(function() {
        jQuery(this).mask(jQuery(this).data('mask'));
    });
    jQuery('[data-maskmoney]').each(function() {
        jQuery(this).mask("#.##0,00", {reverse: true, maxlength: false});
    });
    jQuery('[data-percent]').each(function() {
        $(this).mask('#0.00', {reverse: true});
    });

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

    if ($.browserName == 'ie') {
        jQuery('.browserRec').show();
    }

//    $('[data-clock="true"]').each(function() {
//        var self = jQuery(this);
//        var format = self.data('clockFormat');
//        self.clockface({format: format});
//    });

//    $('[data-selectradio="true"]').each(function() {
//        var self = jQuery(this);
//        self.multiselect({
//            dropRight: self.data('dropRight') || false,
//            nonSelectedText: 'Selecione uma opção',
//            nSelectedText: 'iten(s) selecionado(s)',
//            enableFiltering: true,
//            maxHeight: 400,
//            buttonWidth: '100%',
//        });
//    });

    $('[data-multiple="multiple"]').each(function() {
        var self = jQuery(this);
        self.multiselect({
            dropRight: self.data('dropRight') || false,
            numberDisplayed: 0,
            nonSelectedText: 'Selecione uma opção',
            nSelectedText: 'iten(s) selecionado(s)',
            enableFiltering: true,
            maxHeight: 400,
            buttonWidth: '100%',
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
            beforeSend: function() {
                bootbox.hideAll();
                bootbox.dialog({
                    message: "Aguarde enquanto o CEP é consultado...",
                    title: "Sistema processando informações",
                    buttons: {}
                });
            },
            success: function(data) {
                console.log(data);
                self.val(valueOrig);
                jQuery('[data-uf="' + data.uf + '"]').attr('selected', true);
                $('.select2').change();
                jQuery('.cid').val(data.cidade);
                jQuery('.bai').val(data.bairro);
                jQuery('.log').val(data.logradouro);
                jQuery('.tipo_log').val(data.tipo_logradouro);
                bootbox.hideAll();
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

//    jQuery('[data-tooltip="true"]').each(function() {
//        var self = jQuery(this);
//        self.tooltip();
//    });

    /* Brazilian initialisation for the jQuery UI date picker plugin. */
    /* Written by Leonildo Costa Silva (leocsilva@gmail.com). */
//    $.datepicker.regional['pt-BR'] = {
//        closeText: 'Fechar',
//        prevText: '&#x3c;Anterior',
//        nextText: 'Pr&oacute;ximo&#x3e;',
//        currentText: 'Hoje',
//        monthNames: ['Janeiro', 'Fevereiro', 'Mar&ccedil;o', 'Abril', 'Maio', 'Junho',
//            'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
//        monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun',
//            'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
//        dayNames: ['Domingo', 'Segunda-feira', 'Ter&ccedil;a-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sabado'],
//        dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],
//        dayNamesMin: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],
//        weekHeader: 'Sm',
//        dateFormat: 'dd/mm/yy',
//        firstDay: 0,
//        isRTL: false,
//        showMonthAfterYear: false,
//        yearSuffix: ''};
//    $.datepicker.setDefaults($.datepicker.regional['pt-BR']);
//
//    $(".datepicker").datepicker({
//        changeMonth: true,
//        changeYear: true
//    });
//    $(".datepicker").mask('99/99/9999');

//    jQuery('.menuleft').resizable({handles: 'e'});

//
//    var docw = jQuery(window).width();
//    var doch = jQuery(window).height();
//
//    resize();
//    jQuery(window).resize(function() {
//        resize();
//    });

    function resize() {
//        docw = jQuery(window).width();
//        doch = jQuery(window).height();
//
//        jQuery('.menuleft').css({
//            'height': doch + 'px'
//        });
//
//        jQuery('.content .over').css({
//            'height': (doch - 140) + 'px',
//        });
//
//        jQuery('.content').css({
//            'width': (docw - (5 + jQuery('.menuleft').width())) + 'px',
//            'height': (doch) + 'px',
//            'margin-left': ((5 + jQuery('.menuleft').width())) + 'px'
//        });
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

//    jQuery('[dropdown-toggle="true"]').dropdown();
//    jQuery('[data-mask]').each(function() {
//        jQuery(this).mask(jQuery(this).data('mask'));
//    });
//    jQuery('[data-maskmoney]').each(function() {
//        jQuery(this).mask("#.##0,00", {reverse: true, maxlength: false});
//    });
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
                var notify = form.data('notify');

                jQuery.ajax({
                    async: true,
                    type: form.attr('method'),
                    url: form.attr('action'),
//                    data: form.serialize(),
                    data: new FormData( form ),
                    processData: false,
                    contentType: false,
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
                            if (notify == 0) {

                            } else {
                                jQuery.avisar();
                            }

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
            e.preventDefault();
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


