function tr_row_click(self) {
    self = jQuery(self).parent();

    if (self.attr("class").indexOf("selected") != -1) {
        self.removeClass('selected', true);
        self.trigger("unselected", null);
    } else {
        self.addClass('selected', true);
        self.trigger("selected", self.data("jsonItem"));
    }
}
function countRowsSelected() {
    var index = 0;
    jQuery('.row_data.selected').each(function() {
        index++;
    });
    return index;
}
;

function rowsSelected() {
    var arr = [];
    var index = 0;
    jQuery('.row_data.selected').each(function() {
        arr[index] = jQuery(this).data("jsonItem");
        index++;
    });
    return arr;
}
;

jQuery(document).ready(function() {

    jQuery('[data-mask]').each(function() {
        jQuery(this).mask(jQuery(this).data('mask'));
    });
    jQuery('[data-maskmoney]').each(function() {
        jQuery(this).mask("#.##0,00", {reverse: true, maxlength: false});
    });
    jQuery('[data-percent]').each(function() {
        $(this).mask('#0.00', {reverse: true});
    });

    jQuery.fn.countRowsSelected = function() {
        var index = 0;
        jQuery('.row_data.selected').each(function() {
            index++;
        });
        return index;
    }

    jQuery.fn.rowsSelected = function() {
        var arr = [];
        var index = 0;
        jQuery('.row_data.selected').each(function() {
            arr[index] = jQuery(this).data("jsonItem");
            index++;
        });
        return arr;
    }

    jQuery.extend(jQuery.validator.messages, {
        required: "Este campo &eacute; obrigat&oacute;rio.",
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

    xtable_load();
    function xtable_load() {
        msg_fadeIn();
        jQuery('#table').each(function() {
            var table = jQuery(this);
            var url = table.attr('url');
            url = url + "?datajson=true";
            url = url + "&page=" + table.attr('page');
            url = url + "&rows=" + table.attr('rows');



            jQuery("td.filter").each(function() {
                var input = jQuery(this).children("input");

                if (null != input.val() && undefined != input.val() && "" != input.val()) {
                    url = url + "&" + input.attr("name") + "=" + input.val();
                }

                var input = jQuery(this).children("select");
                if (null != input.val() && undefined != input.val() && "" != input.val()) {
                    url = url + "&" + input.attr("name") + "=" + input.val();
                }
            });

            var json = null;
            if (jQuery.storage(url) != null) {
                json = JSON.parse(jQuery.storage(url));
            } else {
                jQuery.ajax({
                    async: false,
                    type: 'GET',
                    url: url,
                    dataType: 'json',
                    success: function(json_interno) {
                        json = json_interno;
                    }
                });

                jQuery.storageAdd(url, JSON.stringify(json));
            }


            setInterval(function() {
                jQuery.storageClear()
            }, 180000);

            table.children("tbody").html("");
            table.attr("size", json["size"]);
            table.attr("page", json["page"]);
            jQuery('.pag_info').text("Pagina " + table.attr('page') + " de " + json["size"] + "");
            var tr = "<tr>";

            table.children("thead").children("tr").children("th").each(function() {
                var td = jQuery(this);
                tr += "<td class=\"filter\">";
                if (td.attr("options") == "false")
                {
                    if ("true" == td.attr("isfk")) {
                        tr += "<select class=\"select2_filter\" name=\"" + td.attr("fk") + "\">";
                        tr += "<option value=\"\">" + td.attr("fklabelselect") + "</option>";
                        jQuery.ajax({
                            async: false,
                            url: td.attr("fkurl"),
                            success: function(json_response) {
//                                    var selected = "";
//                                    if (null != json[td.attr("fk")] && undefined != json[td.attr("fk")] && "" != json[td.attr("fk")]) {
//                                        selected = "selected=\"selected\"";
//                                    }
                                for (indice in json_response) {
                                    var item_fk = json_response[indice];
                                    var fk = item_fk[td.attr("fk")];
                                    var fk_label = item_fk[td.attr("fklabel")];
                                    if (json[td.attr("fk")] == fk) {
                                        tr += "<option value=\"" + fk + "\" selected=\"selected\">" + fk_label + "</option>";
                                    } else {
                                        tr += "<option value=\"" + fk + "\">" + fk_label + "</option>";
                                    }

                                }
                            }
                        });
                        tr += "</select>";
                    } else {
                        value = "";
                        if (null != json[td.attr("field")] && undefined != json[td.attr("field")] && "" != json[td.attr("field")]) {
                            value = "value=\"" + json[td.attr("field")] + "\"";
                        }
                        tr += "<input type=\"text\" class=\"form-control \" name=\"" + td.attr("field") + "\" " + value + ">";
                    }
                }
                else
                {
                    tr += "<button class=\"btn btn-default btn-flat btn-xs btn_filtrar1\"> Filtrar </button> <button class=\"btn btn-default btn-flat btn-xs btn_filtrar2\"> Limpar filtro </button>";
                }
                tr += "</td>";
            });
            tr += "</tr>";

            for (i in json["rows"])
            {
                var item = json["rows"][i];
                tr += "<tr id=\"row_data_" + i + "\" class=\"row_data\" data-json-item='" + JSON.stringify(item) + "'>";

                table.children("thead").children("tr").children("th").each(function() {
                    var td = jQuery(this);
                    tr += "<td class=\"" + (i % 2 == 0 ? "zz1" : "zz2") + "\"   onclick=\"javascript:tr_row_click(this)\">";
                    if (td.attr("options") == "false")
                    {
                        tr += item[td.attr("field")];
                    } else
                    {
                        var btn_view_onclick = table.attr("btn-view-onclick");
                        if (null != btn_view_onclick && undefined != btn_view_onclick && "" != btn_view_onclick) {
                            btn_view_onclick = 'onclick="' + btn_view_onclick.split("[[__PK__]]").join(item[table.attr("pk")]) + '"';
                        } else {
                            btn_view_onclick = "  ";
                        }

                        var btn_edit_onclick = table.attr("btn-edit-onclick");
                        if (null != btn_edit_onclick && undefined != btn_edit_onclick && "" != btn_edit_onclick) {
                            btn_edit_onclick = 'onclick="' + btn_edit_onclick.split("[[__PK__]]").join(item[table.attr("pk")]) + '"';
                        } else {
                            btn_edit_onclick = "  ";
                        }

                        tr += "<div class=\"btn-group\">";
                        tr += "<button class=\"btn btn-default btn-flat btn-xs btn_view\" pk=\"" + item[table.attr("pk")] + "\"  " + btn_view_onclick + "> <i class=\"fa fa-eye\"></i> </button>";
                        tr += "<button class=\"btn btn-default btn-flat btn-xs btn_edit\" pk=\"" + item[table.attr("pk")] + "\"  " + btn_edit_onclick + "> <i class=\"fa fa-pencil\"></i> </button>";
                        tr += "<button class=\"btn btn-default btn-flat btn-xs btn_delete\" pk=\"" + item[table.attr("pk")] + "\"> <i class=\"fa fa-trash-o\"></i> </button>";

                        // add btn
                        if (null != jQuery(".addon") && undefined != jQuery(".addon")
                                && null != jQuery(".addon").html() && undefined != jQuery(".addon").html()) {
                            tr += jQuery(".addon").html().split("[[__PK__]]").join(item[table.attr("pk")]);
                        }


                        tr += "</div>";
                    }
                    tr += "</td>";
                });

                // tr view
                tr += "</tr>";
                tr += "<tr class=\"row_opt row_view\">";
                tr += "<td colspan=\"" + table.children("thead").children("tr").children("th").size() + "\" style=\"padding: 20px; padding-top: 0px;\">";
                tr += jQuery('.view').html().split("[[__PK__]]").join(item[table.attr("pk")]);
                tr += "</td>";
                tr += "</tr>";

                // tr edit
                tr += "</tr>";
                tr += "<tr class=\"row_opt row_edit\">";
                tr += "<td colspan=\"" + table.children("thead").children("tr").children("th").size() + "\" style=\"padding: 20px; padding-top: 0px;\">";
                tr += jQuery('.edit').html().split("[[__PK__]]").join(item[table.attr("pk")]);
                tr += "</td>";
                tr += "</tr>";

                // tr delete
                tr += "</tr>";
                tr += "<tr class=\"row_opt row_delete\">";
                tr += "<td colspan=\"" + table.children("thead").children("tr").children("th").size() + "\" style=\"padding: 20px; padding-top: 0px;\">";
                tr += jQuery('.delete').html().split("[[__PK__]]").join(item[table.attr("pk")]);
                tr += "</td>";
                tr += "</tr>";
            }
            table.append("<tbody>" + tr + "</tbody>");

            jQuery('.block-xtable .loader').hide();
            jQuery(".select2_filter").select2({width: '100%'});

            msg_fadeOut();
        });
    }

    jQuery(".prev").on("click", function() {
        var page = parseInt(jQuery(this).parent().next().children().attr("page"));
        jQuery(this).parent().next().children().attr("page", (page <= 1 ? 1 : page - 1));
        xtable_load();
    });

    jQuery("._prev").on("click", function() {
        jQuery(this).parent().next().children().attr("page", 1);
        xtable_load();
    });

    jQuery(".next").on("click", function() {
        var page = parseInt(jQuery(this).parent().next().children().attr("page"));
        var size = parseInt(jQuery(this).parent().next().children().attr("size"));
        jQuery(this).parent().next().children().attr("page", (page >= size ? size : page + 1));
        if (page < size)
        {
            xtable_load();
        }
    });

    jQuery("._next").on("click", function() {
        var size = parseInt(jQuery(this).parent().next().children().attr("size"));
        jQuery(this).parent().next().children().attr("page", size);
        xtable_load();
    });

    jQuery(".qtd li a").on("click", function() {
        jQuery(this).parent().parent().parent().next().next().children().attr("rows", jQuery(this).attr("href"));
        jQuery(this).parent().parent().parent().next().next().children().attr("page", 1);
        xtable_load();
        return false;
    });

    jQuery(document).on("click", ".btn_view", function() {
        jQuery('.block-xtable .loader').show();
        if (!jQuery(this).parent().parent().parent().next().is(":visible"))
        {
            var url = window.location.href + "?view=true&pk=" + jQuery(this).attr("pk");
            jQuery.get(url, function(json) {
                console.log(json);
                jQuery('.view_itens li').each(function() {
                    var f = jQuery(this).children("small").attr("field");
                    jQuery(this).children("small").text(json[f]);
                });
            });
        }

        if (jQuery(this).parent().parent().parent().next().is(":visible"))
        {
            jQuery(".row_data").removeClass("blur2");
            jQuery(this).parent().parent().parent().next().hide();
            jQuery(this).parent().parent().parent().children('td').each(function() {
                if (jQuery(this).attr("class").indexOf("zz1") != -1)
                {
                    jQuery(this).css({
                        "background-color": "#FFF"
                    });
                }
                else
                {
                    jQuery(this).css({
                        "background-color": "#f9f9f9"
                    });
                }

            });
        }
        else
        {
            jQuery("div.form").hide();
            jQuery(".row_opt").hide();
            jQuery(".row_data").attr("disabled", true).addClass("blur2");
            jQuery(".row_data").children('td').each(function() {
                jQuery(this).css({
                    "background-color": "#FFF"
                });
            });

            jQuery(this).parent().parent().parent().removeClass("blur2");
            jQuery(this).parent().parent().parent().next().show();
            jQuery(this).parent().parent().parent().children('td').each(function() {
                jQuery(this).css({
                    "background-color": "#fffbd3"
                });
            });
        }
        jQuery('.block-xtable .loader').hide();
        return false;
    });

    jQuery(document).on("click", ".btn_edit", function() {
        if (jQuery(this).parent().parent().parent().next().next().is(":visible"))
        {

            jQuery('.icheck2').iCheck('uncheck');
            jQuery('.icheck2').iCheck('destroy');
            jQuery(".row_data").removeClass("blur2");
            jQuery(this).parent().parent().parent().next().next().hide();
            jQuery(this).parent().parent().parent().children('td').each(function() {
                if (jQuery(this).attr("class").indexOf("zz1") != -1)
                {
                    jQuery(this).css({
                        "background-color": "#FFF"
                    });
                }
                else
                {
                    jQuery(this).css({
                        "background-color": "#f9f9f9"
                    });
                }

            });
        }
        else
        {
            jQuery("div.form").hide();
            $('.icheck2').iCheck({
                checkboxClass: 'icheckbox_square-blue checkbox',
                radioClass: 'iradio_square-blue'
            });
            jQuery(".row_opt").hide();
            jQuery(".row_data").attr("disabled", true).addClass("blur2");
            jQuery(".row_data").children('td').each(function() {
                jQuery(this).css({
                    "background-color": "#FFF"
                });
            });


            jQuery('.block-xtable .loader').show();
            form = jQuery(this).parent().parent().parent().next().next().children("td").children("form");

            var fn_callback = null;

            if (null != form.attr("callback") && undefined != form.attr("callback") && "" != form.attr("callback")) {
                fn_callback = window[form.attr("callback")];
            }

            var url = window.location.href + "?view=true&pk=" + jQuery(this).attr("pk");
            jQuery.get(url, function(json) {

                var form_elements = form.find(":input");
                form_elements.each(function() {
                    var f = jQuery(this).attr("field");
                    if (null != f && undefined != f && "" != f)
                    {
                        if ("INPUT" == jQuery(this).get(0).tagName)
                        {
                            jQuery(this).val(json[f]);
                        }

                        if ("SELECT" == jQuery(this).get(0).tagName)
                        {
                            jQuery(this).select2({width: '100%'});
                            jQuery(this).val(json[f]).change();
                        }

                        jQuery(".inner_datetime").datetimepicker({autoclose: true});

                    }
                });
                if (null != form.attr("callback") && undefined != form.attr("callback") && "" != form.attr("callback")) {
                    fn_callback.call(this, json);
                }
                jQuery('.block-xtable .loader').hide();
            });

            jQuery(this).parent().parent().parent().removeClass("blur2");
            jQuery(this).parent().parent().parent().next().next().show();
            jQuery(this).parent().parent().parent().children('td').each(function() {
                jQuery(this).css({
                    "background-color": "#fffbd3"
                });
            });
        }
        return false;
    });


    jQuery(document).on("click", ".btn_delete", function() {
        if (jQuery(this).parent().parent().parent().next().next().next().is(":visible"))
        {
            jQuery(".row_data").removeClass("blur2");
            jQuery(this).parent().parent().parent().next().next().next().hide();
            jQuery(this).parent().parent().parent().children('td').each(function() {
                if (jQuery(this).attr("class").indexOf("zz1") != -1)
                {
                    jQuery(this).css({
                        "background-color": "#FFF"
                    });
                }
                else
                {
                    jQuery(this).css({
                        "background-color": "#f9f9f9"
                    });
                }

            });
        }
        else
        {
            jQuery("div.form").hide();
            jQuery(".row_opt").hide();
            jQuery(".row_data").attr("disabled", true).addClass("blur2");
            jQuery(".row_data").children('td').each(function() {
                jQuery(this).css({
                    "background-color": "#FFF"
                });
            });

            jQuery(this).parent().parent().parent().removeClass("blur2");
            jQuery(this).parent().parent().parent().next().next().next().show();
            jQuery(this).parent().parent().parent().children('td').each(function() {
                jQuery(this).css({
                    "background-color": "#fffbd3"
                });
            });
        }
        return false;
    });

    jQuery(document).on("click", ".btn_filtrar1", function() {
        xtable_load();
        return false;
    });

    jQuery(document).on("click", ".btn_refresh", function() {
        xtable_load();
        return false;
    });

    jQuery(document).on("click", ".btn_filtrar2", function() {
        jQuery("td.filter").each(function() {
            jQuery(this).children("input").val("");
            jQuery(this).children("select").val("");
        });
        xtable_load();
        return false;
    });

    jQuery(document).on("submit", "form[data-formtable=\"true\"]", function() {
        var form = jQuery(this);

        form.validate();
        if (form.valid()) {
            var data_success_url = form.data('successUrl');
            var data_error_url = form.data('errorUrl');
            var notify = form.data('notify');
            jQuery.ajax({
                async: true,
                type: "POST",
                url: form.attr("action"),
                data: form.serialize(),
                dataType: 'json',
                beforeSend: function() {
                    bootbox.hideAll();
                    bootbox.dialog({
                        message: "Aguarde...",
                        title: "Sistema processando dados",
                        buttons: {}
                    });
                },
                success: function(data) {
                    if (data.success) {
//                        if (notify == 0) {
//
//                        } else {
//                            jQuery.avisar();
//                        }
                        if (data_success_url != null && data_success_url != undefined && data_success_url != '') {
                            xtable_load();
                            setTimeout(function() {
                                bootbox.hideAll();
                            }, 2000);
                            bootbox.hideAll();
                        } else {
                            xtable_load();
                            dialogAjax(data.response);
                            bootbox.hideAll();
                        }
                        if (form.attr("reload") == true || form.attr("reload") == "true") {
                            window.location.reload();
                        }

                        jQuery(".form").hide();
                    } else {
                        dialogAjax(data.response);
                        setTimeout(function() {
                            bootbox.hideAll();
                        }, 2000);
                    }
                },
                error: function(data) {
                    if (data_error_url != null && data_error_url != undefined && data_error_url != '') {
                        //window.location.href = data_error_url;
                    } else {
                        dialogAjax(data);
                    }

                }
            });
        }
        return false;
    });

    jQuery(".btn_cadastro").on("click", function() {
        if (jQuery("div.form").is(":visible"))
        {
            jQuery("div.form").hide();
        }
        else
        {
            jQuery("div.form").show();

            jQuery(".row_data").removeClass("blur2");
            jQuery(".row_view").hide();
            jQuery(".row_edit").hide();
            jQuery(".row_delete").hide();
        }
    });


//    jQuery(document).on('click', '.row_data', function() {
//        if (jQuery(this).attr("class").indexOf("selected") != -1) {
//            jQuery(this).removeClass('selected', true);
//            jQuery(this).trigger("unselected", ["evento"]);
//        } else {
//            jQuery(this).addClass('selected', true);
//            jQuery(this).trigger("selected", ["evento"]);
//        }
//    });
});


function dialogAjax(msg) {
    bootbox.hideAll();
    bootbox.dialog({
        message: msg,
        title: "Sistema processando dados",
        buttons: {}
    });

    setTimeout(function() {
        bootbox.hideAll();
    }, 2000);
}

function msg_fadeIn() {
    jQuery('.mask_message').css({
        'height': jQuery('.block-xtable').css('height'),
        'width': jQuery('.block-xtable').css('width')
    });
    jQuery('.mask_message .text').css({
        'margin-top': ((jQuery('.mask_message').height() - jQuery('.mask_message .text').height()) / 2) + 'px',
        'margin-left': ((jQuery('.mask_message').width() - 250) / 2) + 'px',
    });

    jQuery('.mask_message').fadeIn();
}
function msg_fadeOut() {
    jQuery('.mask_message').fadeOut();
}